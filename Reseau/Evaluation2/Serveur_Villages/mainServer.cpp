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
    
int main(){
    PoolThread poolThread;
    NetworkServer server;
    while(1){
        poolThread.inject(server.getSocketClient());
        server.acceptSocket();
    // On balance la socket client au pool de thread
    // on refait accept
    }
    server.disconnect();
    return 0;
}
