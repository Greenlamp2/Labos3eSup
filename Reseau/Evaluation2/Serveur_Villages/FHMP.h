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
#include "Protocoles.h"
#include<sstream>
#include<list>
#include<map>
    
class FHMP: public Protocoles{
private:
    string login;
    list<string> listeUtilisateurs;
    map<int, string> liste;
public:
    FHMP();
    ~FHMP();
    string treatPacketServer(int numSocket, string packet);
    string treatPacketClient(string packet);
    string actionTypeServer(string type, string contenu, int numSocket);
    string actionTypeClient(string type, string contenu);
    string createPacket(string type, string message);
    string createPacket(string type, int message);
    
    string actionGestionLogin(string contenu, int numSocket);
    string actionGestionBmat(string contenu);
    string actionGestionCmat(string contenu);
    string actionGestionAskMat(string contenu);
    int addAction(string action, string materiel, string date);
    string getLogin();
    
    list<string> getUsers();
    void addUser(int numSocket, string login);
    void removeUser(int numSocket);
    void afficherUsers();
};

#endif