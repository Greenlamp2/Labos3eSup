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
#include "EasyProp.h"
    
class NetworkServer{
private:
    int socketServer;
    int socketClient;
    string adresseIp;
    int port;
    struct sockaddr_in adresseSocket;
    bool connected;
    int sizeMessage;
public:
    NetworkServer();
    NetworkServer(string host, int port);
    NetworkServer(const NetworkServer &n);
    NetworkServer(int socket);
    int createSocket();
    void injectAdress();
    void initInfos(string adresseIp, int port);
    void listenSocket();
    void disconnect();
    void acceptSocket();
    void sendMessage(string message);
    string receiveMessage();
    bool receiveString(string *line);
    bool verifMarqueur(char* message, int nbByte);
    void setSocketClient(int socket);
    
    //setters
    //getters
    string getAdresseIp() const;
    bool isConnected() const;
    int getSocketClient();
};
#endif