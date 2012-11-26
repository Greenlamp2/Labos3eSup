#define cls() cout << "\033[H\033[2J" << endl;
#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
#endif

#include "properties.h"
#include "PoolThread.h"
#include "NetworkServer.h"
#include "EasyProp.h"
#include "EasyCSV.h"
#include "FHMPA.h"


void* fctThreadAdmin(void* param);
pthread_t threadAdmin;
    
int main(){
    string host = EasyProp::getValue("properties.prop", "HOST");
    int port = atoi((EasyProp::getValue("properties.prop", "PORT_VILLAGE")).c_str());
    
    PoolThread poolThread;
    pthread_create(&threadAdmin, NULL, fctThreadAdmin, (void*)&poolThread);
    pthread_detach(threadAdmin);
    NetworkServer server(host, port);
    while(1){
        poolThread.inject(server.getSocketClient());
        server.acceptSocket();
    }
    server.disconnect();
    return 0;
}

void* fctThreadAdmin(void* param){
    cout << "thread admin créer" << endl;
    PoolThread *poolThread = (PoolThread*) param;
    string host = EasyProp::getValue("properties.prop", "HOST");
    int portAdmin = atoi((EasyProp::getValue("properties.prop", "PORT_ADMIN")).c_str());
    cout << "thread Admin en attente" << endl;
    bool done = false;
    NetworkServer admin(host, portAdmin);
    while(1){
        FHMPA fhmpa(poolThread);
        while(!done){
            string messageFromClient;
            bool ok = admin.receiveString(&messageFromClient);
            if(!ok || messageFromClient == EOC){
                cout << "client parti" << endl;
                done = true;
            }else{
                cout << "["<< admin.getSocketClient() <<"]Message reçu: " << messageFromClient << endl;
                string messageToClient = fhmpa.treatPacketServer(messageFromClient);
                cout << "["<< admin.getSocketClient() <<"]Message à envoyer au client: " << messageToClient << endl;
                admin.sendMessage(messageToClient);
            }
        }
        admin.acceptSocket();
        done = false;
    }
}
