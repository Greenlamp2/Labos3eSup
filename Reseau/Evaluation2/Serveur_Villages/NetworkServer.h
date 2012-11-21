#ifndef DEF_NETWORKSERVER
#define DEF_NETWORKSERVER

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
    
class NetworkServer{
private:
    int socketServer;
    int socketClient;
    char *adresseIp;
    int port;
    struct sockaddr_in adresseSocket;
    bool connected;
public:
    NetworkServer();
    NetworkServer(const NetworkServer &n);
    NetworkServer(int socket);
    ~NetworkServer();
    int createSocket();
    void injectAdress();
    void initInfos(const char* adresseIp, int port);
    void listenSocket();
    void disconnect();
    int acceptSocket();
    void sendMessage(const char* message);
    const char* receiveMessage();
    
    //setters
    void setSocketServer(int socketServer);
    void setSocketClient(int socketClient);
    void setAdresseIp(const char* adresseIp);
    void setPort(int port);
    void setAdresseSocket(const struct sockaddr_in *adresseSocket);
    void setConnected(bool connected);
    //getters
    int getSocketServer() const;
    int getSocketClient() const;
    const char* getAdresseIp() const;
    int getPort() const;
    const struct sockaddr_in getAdresseSocket() const;
    bool isConnected() const;
};
#endif