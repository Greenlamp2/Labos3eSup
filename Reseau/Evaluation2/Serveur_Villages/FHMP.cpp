#include "FHMP.h"

FHMP::FHMP()
{
}

FHMP::~FHMP()
{
}


const char* FHMP::treatPacketServer(const char* packet)
{
    char* type;
    char* contenu;
    char* tempPacket = new char[255];
    strcpy(tempPacket, packet);
    type = strtok_r(tempPacket, ";", &contenu);
    const char* retour = this->actionTypeServer(type, contenu);
    delete [] tempPacket;
    return retour;
}


const char* FHMP::treatPacketClient(const char* packet)
{
    char *type;
    char *contenu;
    char *tempPacket = new char[255];
    strcpy(tempPacket, packet);
    type = strtok_r(tempPacket, ";", &contenu);
    const char* retour = this->actionTypeClient(type, contenu);
    delete [] tempPacket;
    return retour;
}

void FHMP::parsePacket(char* packet, char* type, char* contenu)
{
    type = strtok_r(packet, ";", &contenu);
}

const char* FHMP::actionTypeServer(char* type, char* contenu)
{
    const char* retour;
    if(!strcmp(type, EOC)){
        return EOC;
    }else if(!strcmp(type, LOGIN)){
        retour = actionGestionLogin(contenu);
    }else if(!strcmp(type, BMAT)){
        retour = actionGestionBmat(contenu);
    }else{
        retour = CLOSE;
    }
    return retour;
}

const char* FHMP::actionTypeClient(char* type, char* contenu){
    cout << "type: " << type << endl;
    char *retour = new char[255];
    if(!strcmp(type, LOGIN_OUI)){
        strcpy(retour, type);
        
    }else if(!strcmp(type, LOGIN_NON)){
        strcpy(retour, type);
        
    }else if(!strcmp(type, BMAT_OUI)){
        sprintf(retour, "%s;%s", type, contenu);
        
    }else if(!strcmp(type, BMAT_NON)){
        sprintf(retour, "%s;%s", type, contenu);
        
    }else{
        strcpy(retour, type);
    }
    return retour;
}

char* FHMP::createPacket(const char* type, const char* message){
    char* buffer = new char[255];
    sprintf(buffer, "%s;%s", type, message);
    return buffer;
}

char* FHMP::createPacket(const char* type, int message)
{
    char* buffer = new char[255];
    sprintf(buffer, "%s;%d", type, message);
    return buffer;
}


//Actions du protocole
const char* FHMP::actionGestionLogin(char* contenu){
    char *login;
    char *password;
    const char* retour;
    login = strtok_r(contenu, "#", &password);
    if(!EasyCSV::containsKey("gestionnaires.csv", login)){
        return LOGIN_NON;
    }else{
        char* ligne = EasyCSV::getValue("gestionnaires.csv", login);
        char *loginLigne;
        char *passwordLigne;
        loginLigne = strtok_r(ligne, ";", &passwordLigne);
        if(!strcmp(password, passwordLigne)){
            retour = LOGIN_OUI;
        }else{
            retour = LOGIN_NON;
        }
        delete[] ligne;
        return retour;
    }
}

const char* FHMP::actionGestionBmat(char* contenu){
    char *action;
    char *materiel;
    action = strtok_r(contenu, "#", &materiel);
    if(EasyCSV::containsName("materiel.csv", materiel)){
        int id = addAction(action, materiel);
        char* packet = FHMP::createPacket(BMAT_OUI, "test");
        //delete [] c;
        return packet;
    }else{
        return FHMP::createPacket(BMAT_NON, "Matériel non existant");
    }
}

int FHMP::addAction(char* action, char* materiel){
    //char* lastTuple = EasyCSV::getLast("actions.csv");
    //char *idChar, *rest;
    //idChar = strtok_r(lastTuple, ";", &rest);
    //int id = atoi(idChar);
    //id++;
    //char* chaine = new char[255];
    //char* dateJour = EasyDate::now();
    //sprintf(chaine, "%d;%s;%s;%s", id, action, materiel, dateJour);
    //cout << "chaine: " << chaine << endl;
    //EasyCSV::putValue("materiel.csv", idChar, chaine);
    //delete [] dateJour;
    //delete [] lastTuple;
    //delete [] chaine;
    return 5;
}


