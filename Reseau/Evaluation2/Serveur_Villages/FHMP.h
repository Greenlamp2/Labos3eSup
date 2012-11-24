#ifndef FHMP_H
#define FHMP_h

#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
    #include<sstream>
    #include<string>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
    #include<sstream.h>
#endif
#include "properties.h"
#include "EasyCSV.h"
#include "EasyDate.h"
    
class FHMP{
private:
public:
    FHMP();
    ~FHMP();
    const char* treatPacketServer(const char* packet);
    const char* treatPacketClient(const char* packet);
    void parsePacket(char* packet, char* type, char* contenu);
    const char* actionTypeServer(char* type, char* contenu);
    const char* actionTypeClient(char* type, char* contenu);
    static char* createPacket(const char* type, const char* message);
    static char* createPacket(const char* type, int message);
    
    const char* actionGestionLogin(char* contenu);
    const char* actionGestionBmat(char* contenu);
    int addAction(char* action, char* materiel);
};

#endif