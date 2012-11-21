#include "NetworkClient.h"

NetworkClient::NetworkClient(){
    this->socketClient = this->createSocket();
    this->initInfos(IP, PORT_VILLAGE);
    this->connection();
}

NetworkClient::NetworkClient(const NetworkClient& n)
{
    this->socketClient = n.socketClient;
    this->adresseIp = new char[sizeof(n.adresseIp) + 1];
    strcpy(this->adresseIp, n.adresseIp);
    this->port = n.port;
    memcpy(&this->adresseSocket, &n.adresseSocket, sizeof(struct sockaddr_in));
}


NetworkClient::~NetworkClient()
{
    delete [] adresseIp;
}


int NetworkClient::createSocket()
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

void NetworkClient::initInfos(const char* adresseIp, int port)
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

void NetworkClient::disconnect()
{
    close(this->socketClient);
}

void NetworkClient::connection()
{
    unsigned int tailleStruct = sizeof(struct sockaddr_in);
    int ret = connect(this->socketClient, (struct sockaddr*) &this->adresseSocket, tailleStruct);
    if(ret == -1){
        cout << "Erreur sur le connect de la socket: " << errno << endl;
    }else{
        cout << "Connect de la socket: OK" << endl;
    }
}

void NetworkClient::send(const char* )
{
    
}

const char* NetworkClient::receive()
{
    
}

