#ifndef FHMP_H
#define FHMP_h

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
#include "EasyCSV.h"
    
class FHMP{
private:
public:
    FHMP();
    const char* treatPacketServer(const char* packet);
    const char* treatPacketClient(const char* packet);
    void parsePacket(const char* packet, char* type, char* message);
    const char* actionTypeServer(const char* type, const char* message);
    const char* actionTypeClient(const char* type, const char* message);
    static char* createPacket(const char* type, const char* message);
    
    const char* actionGestionLogin(const char* message);
    const char* actionGestionBmat(const char* message);
};

#endif