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
    
int main(){
    const char* host = EasyProp::getValue("properties.prop", "HOST");
    int port = atoi(EasyProp::getValue("properties.prop", "PORT_VILLAGE"));
    
    PoolThread poolThread;
    NetworkServer server(host, port);
    while(1){
        poolThread.inject(server.getSocketClient());
        server.acceptSocket();
    }
    server.disconnect();
    return 0;
}
