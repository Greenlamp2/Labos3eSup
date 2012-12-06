#include "PoolThread.h"


PoolThread::PoolThread()
{
    this->indiceCourant = -1;
    pthread_mutex_init(&this->mutexIndiceCourant, NULL);
    pthread_cond_init(&this->condIndiceCourant, NULL);
    this->startThreads();
}

PoolThread::PoolThread(Protocoles *protocole)
{
    this->paused = false;
    this->stoped = false;
    this->protocole = protocole;
    this->indiceCourant = -1;
    pthread_mutex_init(&this->mutexIndiceCourant, NULL);
    pthread_cond_init(&this->condIndiceCourant, NULL);
    pthread_mutex_init(&this->mutexPause, NULL);
    pthread_cond_init(&this->condPause, NULL);
    this->startThreads();
}

PoolThread::~PoolThread()
{
    pthread_mutex_destroy(&this->mutexIndiceCourant);
    pthread_cond_destroy(&this->condIndiceCourant);
}

void PoolThread::startThreads()
{
    int i=0;
    for(i = 0; i < NB_MAX_CLIENT; i++){
        pthread_create(&threadHandle[i], NULL, fctThread, (void*)this);
        pthread_detach(threadHandle[i]);
        sockets[i].socketService = -1;
        sockets[i].socketUrgence = -1;
    }
}

void PoolThread::inject(int socket)
{
    int i = 0;
    while(i < NB_MAX_CLIENT && sockets[i].socketService != -1){
        i++;
    }
    if(!this->paused){
        if(i != NB_MAX_CLIENT){
            pthread_mutex_lock(&this->mutexIndiceCourant);
            sockets[i].socketService = socket;
            sockets[i].socketUrgence = -1;
            this->indiceCourant = i;
            pthread_mutex_unlock(&this->mutexIndiceCourant);
            pthread_cond_signal(&this->condIndiceCourant);
        }
    }
}

void PoolThread::inject(int socket, int socketUrgence)
{
    int i = 0;
    while(i < NB_MAX_CLIENT && sockets[i].socketService != -1){
        i++;
    }
    if(!this->paused){
        if(i != NB_MAX_CLIENT){
            pthread_mutex_lock(&this->mutexIndiceCourant);
            sockets[i].socketService = socket;
            sockets[i].socketUrgence = socketUrgence;
            this->indiceCourant = i;
            pthread_mutex_unlock(&this->mutexIndiceCourant);
            pthread_cond_signal(&this->condIndiceCourant);
        }
    }else{
        NetworkServer ns(socket);
        ns.sendMessage(PAUSED);
        ns.disconnect();
    }
}

void* PoolThread::fctThread(void* param){
    PoolThread* poolThread = (PoolThread*) param;
    int indice = 0;
    while(1){
        //cout << "thread en attente" << endl;
        pthread_mutex_lock(&poolThread->mutexIndiceCourant);
        while(poolThread->indiceCourant == -1){
            pthread_cond_wait(&poolThread->condIndiceCourant, &poolThread->mutexIndiceCourant);
        }
        indice = poolThread->indiceCourant;
        poolThread->indiceCourant = -1;
        
        int socket = poolThread->sockets[indice].socketService;
        int socketUrgence = poolThread->sockets[indice].socketUrgence;
        pthread_mutex_unlock(&poolThread->mutexIndiceCourant);
        
        NetworkServer networkServer(socket);
        NetworkServer networkServerUrgence(socketUrgence);
        //FHMP fhmp;
        while(networkServer.isConnected()){
            string messageFromClient;
            pthread_mutex_lock(&poolThread->mutexPause);
            while(poolThread->paused == true){
                pthread_cond_wait(&poolThread->condPause, &poolThread->mutexPause);
            }
            pthread_mutex_unlock(&poolThread->mutexPause);
            bool ok = networkServer.receiveString(&messageFromClient);
            if(!ok){
                poolThread->protocole->removeUser(networkServer.getSocketClient());
            }else{
                cout << "["<< networkServer.getSocketClient() <<"]Message reçu: " << messageFromClient << endl;
                string messageToClient = poolThread->protocole->treatPacketServer(networkServer.getSocketClient(), messageFromClient);
                cout << "["<< networkServer.getSocketClient() <<"]Message à envoyer au client: " << messageToClient << endl;
                networkServer.sendMessage(messageToClient);
            }
        }
        networkServer.disconnect();
        networkServerUrgence.disconnect();
        
        pthread_mutex_lock(&poolThread->mutexIndiceCourant);
        poolThread->sockets[indice].socketService = -1;
        poolThread->sockets[indice].socketUrgence = -1;
        pthread_mutex_unlock(&poolThread->mutexIndiceCourant);
    }
}

map<int, string> PoolThread::getUsers(){
    return this->protocole->getUsers();
}

void PoolThread::setPause(bool val)
{
    pthread_mutex_lock(&this->mutexPause);
    this->paused = val;
    pthread_mutex_unlock(&this->mutexPause);
    pthread_cond_signal(&this->condPause);
}

void PoolThread::setStop(bool val)
{
    //this->setStop(val);
    this->stoped = val;
    for(int i=0; i<NB_MAX_CLIENT; i++){
        if(sockets[i].socketService != -1){
            NetworkServer ns(sockets[i].socketService);
            ns.disconnect();
        }
        if(sockets[i].socketUrgence != -1){
            NetworkServer nss(sockets[i].socketUrgence);
            nss.disconnect();
        }
    }
}

int PoolThread::getNbSockets()
{
    return NB_MAX_CLIENT;
}
int PoolThread::getSocket(int num)
{
    return this->sockets[num].socketService;
}

int PoolThread::getSocketUrgence(int num)
{
    return this->sockets[num].socketUrgence;
}


bool PoolThread::checkEtat()
{
    if(paused){
        cout << "Le serveur est en pause." << endl;
        return false;
    }else if(stoped){
        cout << "Le server s'est arrêter" << endl;
        return true;
    }
}
