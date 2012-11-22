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
    char *adresseIp;
    int port;
    struct sockaddr_in adresseSocket;
public:
    NetworkClient();
    NetworkClient(const char* host, int port);
    NetworkClient(const NetworkClient &n);
    ~NetworkClient();
    int createSocket();
    void initInfos(const char* adresseIP, int port);
    void disconnect();
    void connection();
    void sendMessage(const char* message);
    const char* receiveMessage();
    bool verifMarqueur(char* message, int nbByte);
    
    void setAdresseIp(const char* adresseIp);
    const char* getAdresseIp() const;
};
#endif