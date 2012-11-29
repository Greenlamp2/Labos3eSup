#include "FHMPAC.h"

FHMPAC::FHMPAC()
{

}

FHMPAC::FHMPAC(PoolThread* poolThread)
{
    this->poolThread = poolThread;
}


string FHMPAC::treatPacketServer(string packet)
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

string FHMPAC::actionTypeServer(string type, string contenu)
{
    string retour;
    if(type == EOC){
        return EOC;
    }else if(type == PAUSE){
        retour = actionGestionPause(contenu);
    }else if(type == STOP){
        retour = actionGestionStop(contenu);
    }else{
        retour = CLOSE;
    }
    return retour;
}

string FHMPAC::createPacket(string type, string message)
{
    string buffer;
    buffer = type;
    buffer += ";";
    buffer += message;
    return buffer;
}

string FHMPAC::createPacket(string type, int message)
{
    std::ostringstream out;
    out << type;
    out << ";";
    out << message;
    return out.str();
}

string FHMPAC::actionGestionPause(string contenu)
{
}

string FHMPAC::actionGestionStop(string contenu)
{
}
