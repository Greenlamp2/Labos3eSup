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
    }else if(type == CMAT){
        retour = actionGestionCmat(contenu);
    }else if(type == ASKMAT){
        retour = actionGestionAskMat(contenu);
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
        
    }else if(type == CMAT_NON){
        retour = type;
        retour += ";";
        retour += contenu;
        
    }else if(type == CMAT_OUI){
        retour = type;
        retour += ";";
        retour += contenu;
        
    }else if(type == ASKMAT_OUI){
        retour = type;
        retour += ";";
        retour += contenu;
        
    }else if(type == ASKMAT_NON){
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
    bMateriel = strtok_r(bMateriel, "#", &bDate);
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

string FHMP::actionGestionCmat(string contenu){
    if(EasyCSV::containsKey("actions.csv", contenu)){
        string ligne = EasyCSV::getValue("actions.csv", contenu);
        char *buff, *buffer;
        string date;
        char* cstr = new char[ligne.size()+1];
        strcpy(cstr, ligne.c_str());
        buff = strtok_r(cstr, ";", &buffer);//buff = id
        buff = strtok_r(buffer, ";", &buffer);//buff = nom
        buff = strtok_r(buffer, ";", &buffer);//buff = materiel
        buff = strtok_r(buffer, ";", &buffer);//buff = date
        date = buff;
        delete [] cstr;
        int secondsFichier = EasyDate::getSeconds(date);
        int secondsNow = EasyDate::getSeconds(EasyDate::now());
        if(secondsFichier < secondsNow){
            //date expirée
            return FHMP::createPacket(CMAT_NON, "date dépassée");
        }else{
            EasyCSV::delValue("actions.csv", contenu);
            return FHMP::createPacket(CMAT_OUI, CMAT_OUI);
        }
    }else{
        return FHMP::createPacket(CMAT_NON, "action introuvable");
    }
}

string FHMP::actionGestionAskMat(string contenu)
{
    string ligne = EasyCSV::getLast("materiel.csv");
    char *buff, *buffer;
    string idString;
    char* cstr = new char[ligne.size()+1];
    strcpy(cstr, ligne.c_str());
    buff = strtok_r(cstr, ";", &buffer);//buff = id
    idString = buff;
    delete [] cstr;
    int id = atoi(idString.c_str());
    id++;
    
    string nom, marque, prix, accessoires;
    char *bNom, *bMarque, *bPrix, *bAccessoires;
    cstr = new char[contenu.size()+1];
    strcpy(cstr, contenu.c_str());
    bNom = strtok_r(cstr, "#", &buffer);
    bMarque = strtok_r(buffer, "#", &buffer);
    bPrix = strtok_r(buffer, "#", &buffer);
    bAccessoires = strtok_r(buffer, "#", &buffer);
    nom = bNom;
    marque = bMarque;
    prix = bPrix;
    accessoires = bAccessoires;
    delete [] cstr;
    
    std::ostringstream out;
    out << id;
    out << ";";
    out << nom;
    out << ";";
    out << marque;
    out << ";";
    out << prix;
    out << ";commander;15;";
    out << accessoires;
    std::ostringstream idConverted;
    idConverted << id;
    EasyCSV::putValue("materiel.csv", idConverted.str(), out.str());
    std::ostringstream out2;
    out2 << "id: " << id << " delai: 15";
    return FHMP::createPacket(ASKMAT_OUI, out2.str());
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



