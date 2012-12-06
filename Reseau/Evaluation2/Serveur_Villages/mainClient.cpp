#define cls() cout << "\033[H\033[2J" << endl;
#include<stdlib.h>
#include<string.h>
#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
#endif

#include "properties.h"
#include "NetworkClient.h"
#include "FHMP.h"
#include "FHMPAC.h"

string gestionLogin();
int menu();
void pauseScreen();
string gestionChoix(int choix);
int menuAction();
string gestionChoixAction(int choix);
void gestionMessageFromServer(string messageFromServer);
void* fctThreadUrgence(void* param);
pthread_t threadUrgence;
void gestionMessage(string message);
void checkPause();
bool paused = false;
bool stoped = false;
pthread_mutex_t mutexPause;
pthread_cond_t condpause;
NetworkClient *clientConnected;
NetworkClient *clientUrgence;
    
int main(){
    pthread_mutex_init(&mutexPause, NULL);
    pthread_cond_init(&condpause, NULL);
    string host = EasyProp::getValue("properties.prop", "HOST");
    int port = atoi((EasyProp::getValue("properties.prop", "PORT_VILLAGE")).c_str());
 
    NetworkClient client(host, port);
    pthread_create(&threadUrgence, NULL, fctThreadUrgence, (void*)NULL);
    pthread_detach(threadUrgence);
    string chaine = gestionLogin();
    
    clientConnected = &client;
    FHMP fhmp;
    string messageToSend = LOGIN;
    messageToSend += ";";
    messageToSend += chaine;
    cout << "messageToSend: " << messageToSend << endl;
    client.sendMessage(messageToSend);
    string reponseFromServer = fhmp.treatPacketClient(client.receiveMessage());
    if(reponseFromServer == PAUSED){
        cout << "Le serveur est en pause." << endl;
        client.disconnect();
        exit(1);
    }
    if(reponseFromServer == EOC){
        cout << "Le serveur n'est pas disponible. soit pause, soit nombre max atteint." << endl;
    }
    cout << "reponseFromServer: " << reponseFromServer << endl;
    if(reponseFromServer == LOGIN_OUI){
        int choix = -1;
        do{
            string packetToSend;
            do{
                choix = menu();
                packetToSend = gestionChoix(choix);
                cout << "packetToSend: " << packetToSend << endl;
            }while(packetToSend == "RETOUR");
            if(packetToSend == EOC){
                
            }else{
                checkPause();
                client.sendMessage(packetToSend);
                string messageFromServer = fhmp.treatPacketClient(client.receiveMessage());
                gestionMessageFromServer(messageFromServer);
            }
        }while(choix > 0 && choix <= 3 && paused);
    }else{
        cout << "Login ou password incorrect." << endl;
    }
    clientConnected->disconnect();
    clientUrgence->disconnect();
    return 0;
}

string gestionLogin(){
    string login, password;
    cout << "Veuillez entrez votre login: ";
    //login = "admin";
    cin >> login;
    cout << "Veuillez entrez votre password: ";
    //password = "admin";
    cin >> password;
    string retour;
    retour = login;
    retour += "#";
    retour += password;
    return retour;
}

int menu(){
    checkPause();
    int choix = 0;
    cout << "========================" << endl;
    cout << "[0] Quitter" << endl;
    cout << "[1] Demander une action" << endl;
    cout << "[2] Supprimer une action" << endl;
    cout << "[3] Commande de matériel" << endl;
    cout << "========================" << endl;
    cout << "Votre choix: ";
    cin >> choix;
    return choix;
}

int menuAction(){
    checkPause();
    int choix = 0;
    cout << "========================" << endl;
    cout << "[0] Retour" << endl;
    cout << "[1] Demander une livraison" << endl;
    cout << "[2] Demander une réparation" << endl;
    cout << "[3] Demander un déclassement" << endl;
    cout << "========================" << endl;
    cout << "Votre choix: ";
    cin >> choix;
    return choix;
}

void gestionMessageFromServer(string messageFromServer){
    char* bType;
    char* bMessage;
    string type, message;
    char* cstr = new char[messageFromServer.size()+1];
    strcpy(cstr, messageFromServer.c_str());
    bType = strtok_r(cstr, ";", &bMessage);
    type = bType;
    message = bMessage;
    delete [] cstr;
    
    if(type == BMAT_OUI){
        cout << "Booking Material OK" << endl;
        cout << "Id action: " << message << endl;
    }else if(type == BMAT_NON){
       cout << "Booking Material pas OK" << endl;
       cout << "erreur: " << message << endl;
    }else if(type == CMAT_OUI){
       cout << "Cancel Material OK" << endl;
    }else if(type == CMAT_NON){
       cout << "Cancel Material pas OK" << endl;
       cout << "erreur: " << message << endl;
    }else if(type == ASKMAT_NON){
       cout << "ASKING FOR MATERIAL pas OK" << endl;
       cout << "erreur: " << message << endl;
    }else if(type == ASKMAT_OUI){
       cout << "ASKING FOR MATERIAL OK" << endl;
       cout << message << endl;
    }
}

string gestionChoix(int choix){
    int choixAction = 0;
    string chaine;
    string idAction;
    string nom, marque, prix, accessoire;
    switch(choix){
        case 1:
            do{
                choixAction = menuAction();
            }while(choixAction < 0 || choixAction > 3);
            chaine = gestionChoixAction(choixAction);
            break;
        case 2:
            cout << "Quel est l'id de l'action que vous souhaitez supprimer: ";
            cin >> idAction;
            chaine = CMAT;
            chaine += ";";
            chaine += idAction;
            break;
        case 3:
            cout << "Quel est le nom du matériel: ";
            cin >> nom;
            cout << "Quel est la marque: ";
            cin >> marque;
            cout << "Quel est le prix: ";
            cin >> prix;
            cout << "Quels sont les accesoires (xx,xx,xx): ";
            cin >> accessoire;
            chaine = ASKMAT;
            chaine += ";";
            chaine += nom;
            chaine += "#";
            chaine += marque;
            chaine += "#";
            chaine += prix;
            chaine += "#";
            chaine += accessoire;
            break;
        default:
            return EOC;
            break;
    }
    return chaine;
}

string gestionChoixAction(int choix){
    string chaine, nomMateriel, retour, date;
    
    switch(choix){
        case 1:
            cout << "Quel matériel souhaitez-vous la livraison: ";
            cin >> nomMateriel;
            cout << "A quelle date(JJ/MM/YY): ";
            cin >> date;
            chaine = "LIVRAISON#";
            chaine += nomMateriel;
            chaine += "#";
            chaine += date;
            retour = BMAT;
            retour += ";";
            retour += chaine;
            break;
        case 2:
            cout << "Quel matériel souhaitez-vous la réparation: ";
            cin >> nomMateriel;
            cout << "A quelle date(JJ/MM/YYYY): ";
            cin >> date;
            chaine = "REPARATION#";
            chaine += nomMateriel;
            chaine += "#";
            chaine += date;
            retour = BMAT;
            retour += ";";
            retour += chaine;
            break;
        case 3:
            cout << "Quel matériel souhaitez-vous le déclassement: ";
            cin >> nomMateriel;
            cout << "A quelle date(JJ/MM/YY): ";
            cin >> date;
            chaine = "DECLASSEMENT#";
            chaine += nomMateriel;
            chaine += "#";
            chaine += date;
            retour = BMAT;
            retour += ";";
            retour += chaine;
            break;
        default:
            retour = "RETOUR";
            break;
    }
    return retour;
}

void* fctThreadUrgence(void* param){
    string host = EasyProp::getValue("properties.prop", "HOST");
    int portUrgence = atoi((EasyProp::getValue("properties.prop", "PORT_URGENCE")).c_str());
    NetworkClient urgence(host, portUrgence);
    clientUrgence = &urgence;
    bool done = false;
    FHMPAC fhmpac;
    while(!done){
        string messageFromServer;
        bool ok = urgence.receiveString(&messageFromServer);
        if(!ok || messageFromServer == EOC){
            done = true;
        }else{
            cout << endl << "["<< urgence.getSocketClient() <<"]Message reçu: " << messageFromServer << endl;
            gestionMessage(messageFromServer);
        }
    }
}
void gestionMessage(string message){
    if((message == "PAUSE")){
        paused = true;
        pthread_mutex_lock(&mutexPause);
    }else if(message == "STOP"){
        stoped = true;
	clientConnected->disconnect();
	clientUrgence->disconnect();
        exit(1);
    }else if(message == "RESUME"){
        paused = false;
        pthread_mutex_unlock(&mutexPause);
    }
}

void checkPause()
{
    if(paused){
        cout << "Le serveur est en pause." << endl;
        pthread_mutex_lock(&mutexPause);
        pthread_mutex_unlock(&mutexPause);
    }else if(stoped){
        cout << "Le server s'est arrêter" << endl;
        exit(1);
    }
}
