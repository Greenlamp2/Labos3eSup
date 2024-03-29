#include "FHMPA.h"

FHMPA::FHMPA()
{

}

FHMPA::FHMPA(PoolThread* poolThread)
{
    this->poolThread = poolThread;
}


string FHMPA::treatPacketServer(string packet)
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

string FHMPA::actionTypeServer(string type, string contenu)
{
    string retour;
    if(type == EOC){
        return EOC;
    }else if(type == LOGINA){
        retour = actionGestionLogina(contenu);
    }else if(type == LCLIENT){
        retour = actionGestionLclients(contenu);
    }else if(type == PAUSE){
        retour = actionGestionPause(contenu);
    }else if(type == STOP){
        retour = actionGestionStop(contenu);
    }else if(type == RESUME){
        retour = actionGestionResume(contenu);
    }else{
        retour = CLOSE;
    }
    return retour;
}

string FHMPA::createPacket(string type, string message)
{
    string buffer;
    buffer = type;
    buffer += ";";
    buffer += message;
    return buffer;
}

string FHMPA::createPacket(string type, int message)
{
    std::ostringstream out;
    out << type;
    out << ";";
    out << message;
    return out.str();
}

string FHMPA::actionGestionLogina(string contenu)
{
    char* bLogin;
    char* bPassword;
    string login, password;
    char* cstr = new char[contenu.size()+1];
    strcpy(cstr, contenu.c_str());
    bLogin = strtok_r(cstr, "#", &bPassword);
    login = bLogin;
    password = bPassword;
    delete [] cstr;
    if(!EasyCSV::containsKey("admin.csv", login)){
        return LOGINA_NON;
    }else{
        string ligne = EasyCSV::getValue("admin.csv", login);
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
            return LOGINA_OUI;
        }else{
            return LOGINA_NON;
        }
    }
}

string FHMPA::actionGestionLclients(string contenu)
{
    map<int, string> listeUtilisateurs = poolThread->getUsers();
    if(listeUtilisateurs.size() == 0){
        return LCLIENT_EMPTY;
    }else{
        map<int, string>::iterator it;
        string retour;
        retour = LCLIENT_REPONSE;
        retour += ";";
        int taille = listeUtilisateurs.size();
        int i = 0;
        for(it = listeUtilisateurs.begin(); it != listeUtilisateurs.end(); it++){
            retour += (*it).second;
            if(i+1 < taille){
                retour += "#";
            }
            i++;
        }
        return retour;
    }
}

string FHMPA::actionGestionPause(string contenu)
{
    int nbMax = this->poolThread->getNbSockets();
    for(int i=0; i<nbMax; i++){
        if(this->poolThread->getSocketUrgence(i) != -1){  
            NetworkServer urgence(this->poolThread->getSocketUrgence(i));
            urgence.sendMessage("PAUSE");
        }
    }
    this->poolThread->setPause(true);
    return PAUSE_OUI;
}

string FHMPA::actionGestionStop(string contenu)
{
    int nbMax = this->poolThread->getNbSockets();
    for(int i=0; i<nbMax; i++){
        if(this->poolThread->getSocketUrgence(i) != -1){  
            NetworkServer urgence(this->poolThread->getSocketUrgence(i));
            urgence.sendMessage("STOP");
        }
    }
    this->poolThread->setStop(true);
    return STOP_OUI;
}

string FHMPA::actionGestionResume(string contenu)
{
    int nbMax = this->poolThread->getNbSockets();
    for(int i=0; i<nbMax; i++){
        if(this->poolThread->getSocketUrgence(i) != -1){  
            NetworkServer urgence(this->poolThread->getSocketUrgence(i));
            urgence.sendMessage("RESUME");
        }
    }
    this->poolThread->setPause(false);
    return RESUME_OUI;
}
