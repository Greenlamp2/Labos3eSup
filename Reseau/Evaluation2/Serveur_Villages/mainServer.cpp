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
#include<vector>
#include "FHMP.h"
#include "FHMPA.h"
#include "FHMPAC.h"


void* fctThreadAdmin(void* param);
pthread_t threadAdmin;
    
int main(){
    string host = EasyProp::getValue("properties.prop", "HOST");
    int port = atoi((EasyProp::getValue("properties.prop", "PORT_VILLAGE")).c_str());
    int portUrgence = atoi((EasyProp::getValue("properties.prop", "PORT_URGENCE")).c_str());
    FHMP fhmp;
    FHMPAC fhmpac;
    PoolThread poolThread(&fhmp);
    
    pthread_create(&threadAdmin, NULL, fctThreadAdmin, (void*)&poolThread);
    pthread_detach(threadAdmin);
    
    NetworkServer server(host, port);
    NetworkServer urgence(host, portUrgence);
    while(1){
        poolThread.inject(server.getSocketClient(), urgence.getSocketClient());
        server.acceptSocket();
    }
    return 0;
}

void* fctThreadAdmin(void* param){
    PoolThread *poolThread = (PoolThread*) param;
    string host = EasyProp::getValue("properties.prop", "HOST");
    int portAdmin = atoi((EasyProp::getValue("properties.prop", "PORT_ADMIN")).c_str());
    bool done = false;
    NetworkServer admin(host, portAdmin);
    bool stopped = false;
    while(!stopped){
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
            if(messageFromClient == STOP){
                stopped = true;
            }
        }
        if(!stopped){
            admin.acceptSocket();
        }
        done = false;
    }
    admin.disconnect();
}