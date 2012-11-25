#include "FHMP.h"

FHMP::FHMP()
{
}

FHMP::~FHMP()
{
}


string FHMP::treatPacketServer(string packet)
{
    string type, contenu;
    char* bType;
    char* bContenu;
    char* cstr = new char[packet.size()+1];
    strcpy(cstr, packet.c_str());
    bType = strtok_r(cstr, ";", &bContenu);
    type = bType;
    contenu = bContenu;
    delete [] cstr;
    string retour = this->actionTypeServer(type, contenu);
    return retour;
}


string FHMP::treatPacketClient(string packet)
{
    string type, contenu;
    char* bType;
    char* bContenu;
    char* cstr = new char[packet.size()+1];
    strcpy(cstr, packet.c_str());
    bType = strtok_r(cstr, ";", &bContenu);
    type = bType;
    contenu = bContenu;
    delete [] cstr;
    string retour = this->actionTypeClient(type, contenu);
    return retour;
}

string FHMP::actionTypeServer(string type, string contenu)
{
    string retour;
    if(type == EOC){
        return EOC;
    }else if(type == LOGIN){
        retour = actionGestionLogin(contenu);
    }else if(type == BMAT){
        retour = actionGestionBmat(contenu);
    }else{
        retour = CLOSE;
    }
    return retour;
}

string FHMP::actionTypeClient(string type, string contenu){
    cout << "type: " << type << endl;
    string retour;
    if(type == LOGIN_OUI){
        retour = type;
        
    }else if(type == LOGIN_NON){
        retour = type;
        
    }else if(type == BMAT_OUI){
        retour = type;
        retour += ";";
        retour += contenu;
        
    }else if(type == BMAT_NON){
        retour = type;
        retour += ";";
        retour += contenu;
        
    }else{
        retour = type;
    }
    return retour;
}

string FHMP::createPacket(string type, string message){
    string buffer;
    buffer = type;
    buffer += ";";
    buffer += message;
    return buffer;
}

string FHMP::createPacket(string type, int message)
{
    std::ostringstream out;
    out << type;
    out << ";";
    out << message;
    return out.str();
}


//Actions du protocole
string FHMP::actionGestionLogin(string contenu){
    char* bLogin;
    char* bPassword;
    string login, password;
    char* cstr = new char[contenu.size()+1];
    strcpy(cstr, contenu.c_str());
    bLogin = strtok_r(cstr, "#", &bPassword);
    login = bLogin;
    password = bPassword;
    delete [] cstr;
    if(!EasyCSV::containsKey("gestionnaires.csv", login)){
        return LOGIN_NON;
    }else{
        string ligne = EasyCSV::getValue("gestionnaires.csv", login);
        char *bLoginLigne;
        char *bPasswordLigne;
        string loginLigne, passwordLigne;
        char* cstr2 = new char[ligne.size()+1];
        strcpy(cstr2, ligne.c_str());
        bLoginLigne = strtok_r(cstr2, ";", &bPasswordLigne);
        loginLigne = bLoginLigne;
        passwordLigne = bPasswordLigne;
        delete [] cstr2;
        if(password == passwordLigne){
            return LOGIN_OUI;
        }else{
            return LOGIN_NON;
        }
    }
}

string FHMP::actionGestionBmat(string contenu){
    char *bAction;
    char *bMateriel;
    char *bDate;
    string action, materiel, date;
    char* cstr = new char[contenu.size()+1];
    strcpy(cstr, contenu.c_str());
    bAction = strtok_r(cstr, "#", &bMateriel);
    action = bAction;
    bDate = strtok_r(bMateriel, "#", &bMateriel);
    materiel = bMateriel;
    date = bDate;
    delete [] cstr;
    if(EasyCSV::containsName("materiel.csv", materiel)){
        string dispo = EasyCSV::materielIsDispo("materiel.csv", materiel);
        cout << "dispo: " << dispo << endl;
        if(dispo == "NULL"){
            return FHMP::createPacket(BMAT_NON, "Matériel non disponible pour le moment");
        }else{
            int id = addAction(action, dispo, date);
            return FHMP::createPacket(BMAT_OUI, id);
        }
    }else{
        return FHMP::createPacket(BMAT_NON, "Matériel non existant");
    }
}

int FHMP::addAction(string action, string materiel, string date){
    string lastTuple = EasyCSV::getLast("actions.csv");
    char *bIdString, *bRest;
    string idString, rest;
    char* cstr = new char[lastTuple.size()+1];
    strcpy(cstr, lastTuple.c_str());
    bIdString = strtok_r(cstr, ";", &bRest);
    idString = bIdString;
    rest = bRest;
    delete [] cstr;
    int id = atoi(idString.c_str());
    id++;
    std::ostringstream out;
    out << id;
    idString = out.str();
    out << ";";
    out << action;
    out << ";";
    out << materiel;
    out << ";";
    out << date;
    cout << "chaine : " << out.str() << endl;
    EasyCSV::putValue("actions.csv", idString, out.str());
    return id;
}



