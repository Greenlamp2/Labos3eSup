#include "NetworkServer.h"

NetworkServer::NetworkServer(){
    this->setSocketServer(this->createSocket());
    this->setSocketClient(-1);
    this->initInfos(IP, PORT_VILLAGE);
    this->injectAdress();
    this->listenSocket();
    this->setSocketClient(this->acceptSocket());
}

NetworkServer::NetworkServer(int socketClient)
{
    this->setSocketClient(socketClient);
    this->setConnected(true);
}


NetworkServer::NetworkServer(const NetworkServer& n)
{
    setSocketServer(n.getSocketServer());
    this->setSocketClient(n.getSocketClient());
    this->setAdresseIp(n.getAdresseIp());
    this->setPort(n.getPort());
    this->setAdresseSocket(&n.getAdresseSocket());
    this->setConnected(n.isConnected());
}


NetworkServer::~NetworkServer()
{
    delete [] adresseIp;
}


int NetworkServer::createSocket()
{
    int hSocket = -1;
    //AF_INET = pour dire a TCP/IP d'utiliser une adresse Ip sur 4 octets.
    //SOCK_STREAM = pour dire qu'on veut travailler en mode connecté
    //0 = pour prendre le protocole par défaut, c'est à dire TCP/IP (6 normalement)
    hSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(hSocket == -1){
        cout << "Erreur de création de la socket: " << errno << endl;
        exit(1);
    }else{
        cout << "Création de la socket: OK" << endl;
    }
    
    return hSocket;
}

void NetworkServer::initInfos(const char* adresseIp, int port)
{
    struct hostent *infoHost;
    struct in_addr addIp;

    this->adresseIp = new char[strlen(adresseIp) + 1];
    strcpy(this->adresseIp, adresseIp);
    this->port = port;
    cout << "Mise à l'écoute sur l'adresse " << this->adresseIp << " et le port " << this->port << endl;

    infoHost = gethostbyname(adresseIp);
    if(infoHost == 0){
        cout << "Erreur d'acquisition des infos sur le host: " << errno << endl;
        exit(1);
    }else{
        cout << "Acquisition des infos sur le host: OK" << endl;
    }

    //infoHost = les infos sur la machine comme par exemple les carte réseau installé etc.
    // h_addr est une macro qui recupere la premiere adresse du tableau des cartes réseau.
    memcpy(&addIp, infoHost->h_addr, infoHost->h_length);
    //on initialise la structure sockaddr_in qui contiendra l'adresse et le port.
    memset(&this->adresseSocket, 0, sizeof(struct sockaddr_in));
    this->adresseSocket.sin_family = AF_INET;
    //On fait htons pour mettre en big endian si ce n'est pas le cas.
    this->adresseSocket.sin_port = htons(port);
    memcpy(&this->adresseSocket.sin_addr, infoHost->h_addr, infoHost->h_length);
}


void NetworkServer::injectAdress()
{
    cout << "== [BIND] ==" << endl;
    int ret = bind(this->socketServer, (struct sockaddr*) &this->adresseSocket, sizeof(struct sockaddr_in));
    if(ret == -1){
        cout << "Erreur sur le bind de la socket: " << errno << endl;
        exit(1);
    }else{
        cout << "Bind de la socket: OK" << endl;
    }
}

void NetworkServer::listenSocket()
{
    cout << "== [LISTEN] ==" << endl;
    int ret = -1;
    //SOMAXCONN = nombre de connexion pendante maximum ici 1024.
    ret = listen(this->socketServer, SOMAXCONN);
    if(ret == -1){
        cout << "Erreur sur le listen de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }else{
        cout << "Listen de la socket: OK" << endl;
    }
}

void NetworkServer::disconnect()
{
    close(this->socketServer);
    if(this->socketClient != -1){
        close(this->socketClient);
    }
    this->connected = false;
}

int NetworkServer::acceptSocket()
{
    cout << "== [ACCEPT] ==" << endl;
    int tailleStruct = sizeof(struct sockaddr_in);
    int ret = accept(this->socketServer, (struct sockaddr*) &this->adresseSocket, (socklen_t*)&tailleStruct);
    if(ret == -1){
        cout << "Erreur sur l'accept de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }else{
        cout << "Accept de la socket: OK" << endl;
    }
    this->connected = true;
    return ret;
}

void NetworkServer::sendMessage(const char* message)
{
    int ret = send(this->socketClient, message, SIZEMESSAGE, 0);
    if(ret == -1){
        cout << "Erreur sur le send de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
}

const char* NetworkServer::receiveMessage()
{
    int tailleSegment;
    int nbByteRecu;
    int sizeInt = sizeof(int);
    int ret = getsockopt(this->socketClient, IPPROTO_TCP, TCP_MAXSEG, &tailleSegment, (socklen_t*)&sizeInt);
    if(ret == -1){
        cout << "Erreur sur le getSockOpt de la socket: " << errno << endl;
        this->disconnect();
        exit(1);
    }
}

//Setters
void NetworkServer::setSocketServer(int socketServer)
{
    this->socketServer = socketServer;
}
void NetworkServer::setSocketClient(int socketClient)
{
    this->socketClient = socketClient;
}
void NetworkServer::setPort(int port)
{
    this->port = port;
}
void NetworkServer::setConnected(bool connected)
{
    this->connected = connected;
}
void NetworkServer::setAdresseSocket(const sockaddr_in *adresseSocket)
{
    memcpy(&this->adresseSocket, adresseSocket, sizeof(struct sockaddr_in));
}
void NetworkServer::setAdresseIp(const char* adresseIp)
{
    this->adresseIp = new char[strlen(adresseIp) + 1];
    strcpy(this->adresseIp, adresseIp);
}

//Getters
int NetworkServer::getSocketServer() const
{
    return this->socketServer;
}
int NetworkServer::getSocketClient() const
{
    return this->socketClient;
}
int NetworkServer::getPort() const
{
    return this->port;
}
const sockaddr_in NetworkServer::getAdresseSocket() const
{
    return this->adresseSocket;
}
const char* NetworkServer::getAdresseIp() const
{
    return this->adresseIp;
}

bool NetworkServer::isConnected() const
{
    return this->connected;
}



