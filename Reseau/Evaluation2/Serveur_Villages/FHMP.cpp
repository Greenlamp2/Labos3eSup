#include "FHMP.h"

FHMP::FHMP()
{

}

const char* FHMP::treatPacketServer(const char* packet)
{
    char* type = new char[SIZEMESSAGE];
    char* message = new char[SIZEMESSAGE];
    int nbByte = strlen(packet);
    FHMP::parsePacket(packet, type, message);
    return this->actionTypeServer(type, message);
}
const char* FHMP::treatPacketClient(const char* packet)
{
    char* type = new char[SIZEMESSAGE];
    char* message = new char[SIZEMESSAGE];
    int nbByte = strlen(packet);
    FHMP::parsePacket(packet, type, message);
    const char* retour = this->actionTypeClient(type, message);
    cout << "retour: " << retour << endl;
    return retour;
}

void FHMP::parsePacket(const char* packet, char* type, char* message)
{
    char *buffer, *buff;
    char* temp = new char[strlen(packet)];
    strcpy(temp, packet);
    
    buff = strtok_r(temp, ";", &buffer);
    strcpy(type, buff);
    strcpy(message, buffer);
}

const char* FHMP::actionTypeServer(const char* type, const char* message)
{
    cout << "type: " << type << endl;
    if(!strcmp(type, EOC)){
        return EOC;
    }else if(!strcmp(type, LOGIN)){
        return actionGestionLogin(message);
    }else if(!strcmp(type, BMAT)){
        return actionGestionBmat(message);
    }else{
        return CLOSE;
    }
}

const char* FHMP::actionTypeClient(const char* type, const char* message){
    cout << "type: " << type << endl;
    if(!strcmp(type, LOGIN_OUI)){
        cout << "return LOGIN_OUI" << endl;
        return LOGIN_OUI;
    }else if(!strcmp(type, LOGIN_NON)){
        cout << "return LOGIN_NON" << endl;
        return LOGIN_NON;
    }else if(!strcmp(type, BMAT_OUI)){
        cout << "return BMAT_OUI" << endl;
        char* retour = new char[255];
        sprintf(retour, "%s;%s", type, message);
        return retour;
    }else{
        cout << "return CLOSE" << endl;
        return CLOSE;
    }
}

char* FHMP::createPacket(const char* type, const char* message){
    char* buffer = new char[255];
    sprintf(buffer, "%s;%s", type, message);
    return buffer;
}


//Actions du protocole
const char* FHMP::actionGestionLogin(const char* message){
    char *login, *password;
    char* temp = new char[strlen(message)];
    strcpy(temp, message);
    
    login = strtok_r(temp, "#", &password);
    if(!EasyCSV::containsKey("gestionnaires.csv", login)){
        return LOGIN_NON;
    }else{
        const char* ligne = EasyCSV::getValue("gestionnaires.csv", login);
        char *buff, *buffer;
        char* tempCsv = new char[strlen(ligne)];
        strcpy(tempCsv, ligne);
        buff = strtok_r(tempCsv, ";", &buffer);
        if(!strcmp(password, buffer)){
            return LOGIN_OUI;
        }else{
            return LOGIN_NON;
        }
    }
}

const char* FHMP::actionGestionBmat(const char* message){
    cout << "message: " << message << endl;
    return FHMP::createPacket(BMAT_OUI, "12");
}

