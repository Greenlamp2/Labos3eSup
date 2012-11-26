#include "PoolThread.h"


PoolThread::PoolThread()
{
    this->indiceCourant = -1;
    pthread_mutex_init(&this->mutexIndiceCourant, NULL);
    pthread_cond_init(&this->condIndiceCourant, NULL);
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
        sockets[i] = -1;
    }
}

void PoolThread::inject(int socket)
{
    int i = 0;
    while(i < NB_MAX_CLIENT && sockets[i] != -1){
        i++;
    }
    if(i != NB_MAX_CLIENT){
        pthread_mutex_lock(&this->mutexIndiceCourant);
        sockets[i] = socket;
        this->indiceCourant = i;
        pthread_mutex_unlock(&this->mutexIndiceCourant);
        pthread_cond_signal(&this->condIndiceCourant);
    }
}

void* PoolThread::fctThread(void* param){
    PoolThread* poolThread = (PoolThread*) param;
    int indice = 0;
    while(1){
        cout << "thread en attente" << endl;
        pthread_mutex_lock(&poolThread->mutexIndiceCourant);
        while(poolThread->indiceCourant == -1){
            pthread_cond_wait(&poolThread->condIndiceCourant, &poolThread->mutexIndiceCourant);
        }
        indice = poolThread->indiceCourant;
        poolThread->indiceCourant = -1;
        
        int socket = poolThread->sockets[indice];
        pthread_mutex_unlock(&poolThread->mutexIndiceCourant);
        
        NetworkServer networkServer(socket);
        FHMP fhmp;
        while(networkServer.isConnected()){
            string messageFromClient;
            bool ok = networkServer.receiveString(&messageFromClient);
            if(!ok){
                poolThread->removeUser(fhmp.getLogin());
                networkServer.disconnect();
            }else{
                cout << "["<< networkServer.getSocketClient() <<"]Message reçu: " << messageFromClient << endl;
                string messageToClient = fhmp.treatPacketServer(messageFromClient);
                if(messageToClient == LOGIN_OUI){
                    poolThread->addUser(fhmp.getLogin());
                }
                cout << "["<< networkServer.getSocketClient() <<"]Message à envoyer au client: " << messageToClient << endl;
                networkServer.sendMessage(messageToClient);
            }
        }
        cout << "Client parti" << endl;
        
        pthread_mutex_lock(&poolThread->mutexIndiceCourant);
        poolThread->sockets[indice] = -1;
        pthread_mutex_unlock(&poolThread->mutexIndiceCourant);
    }
}

void PoolThread::addUser(string login)
{
    this->listeUtilisateurs.push_back(login);
    this->afficherUsers();
}

void PoolThread::removeUser(string login)
{
    this->listeUtilisateurs.remove(login);
}

void PoolThread::afficherUsers()
{
    list<string>::iterator it;
    for(it = this->listeUtilisateurs.begin(); it != this->listeUtilisateurs.end(); it++){
        cout << "user: " << *it << endl;
    }
}

list<string> PoolThread::getUsers(){
    return this->listeUtilisateurs;
}
