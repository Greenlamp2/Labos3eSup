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
    
class NetworkClient{
private:
    int socketClient;
    char *adresseIp;
    int port;
    struct sockaddr_in adresseSocket;
public:
    NetworkClient();
    NetworkClient(const NetworkClient &n);
    ~NetworkClient();
    int createSocket();
    void initInfos(const char* adresseIP, int port);
    void disconnect();
    void connection();
    void send(const char*);
    const char* receive();
};
#endif