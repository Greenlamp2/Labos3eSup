#define cls() cout << "\033[H\033[2J" << endl;
#include<stdlib.h>
#include<string.h>
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
#include "NetworkClient.h"
    
int main(){
    NetworkClient client;
    client.disconnect();
    return 0;
}