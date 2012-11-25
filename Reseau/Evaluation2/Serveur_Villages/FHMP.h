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
#include<sstream>
    
class FHMP{
private:
public:
    FHMP();
    ~FHMP();
    string treatPacketServer(string packet);
    string treatPacketClient(string packet);
    string actionTypeServer(string type, string contenu);
    string actionTypeClient(string type, string contenu);
    string createPacket(string type, string message);
    string createPacket(string type, int message);
    
    string actionGestionLogin(string contenu);
    string actionGestionBmat(string contenu);
    string actionGestionCmat(string contenu);
    int addAction(string action, string materiel, string date);
};

#endif