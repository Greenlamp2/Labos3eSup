#ifndef DEF_NETWORKCLIENT
#define DEF_NETWORKCLIENT

#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
    #include<string>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
#endif
    
#include "properties.h"
#include "EasyProp.h"
    
class NetworkClient{
private:
    int socketClient;
    string adresseIp;
    int port;
    struct sockaddr_in adresseSocket;
public:
    NetworkClient();
    NetworkClient(string host, int port);
    NetworkClient(const NetworkClient &n);
    int createSocket();
    void initInfos(string adresseIP, int port);
    void disconnect();
    void connection();
    void sendMessage(string message);
    string receiveMessage();
    bool verifMarqueur(char* message, int nbByte);
    
    string getAdresseIp() const;
};
#endif