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

char * gestionLogin();
int menu();
void pauseScreen();
const char* gestionChoix(int choix);
int menuAction();
const char* gestionChoixAction(int choix);
void gestionMessageFromServer(const char* messageFromServer);
    
int main(){
    const char* host = EasyProp::getValue("properties.prop", "HOST");
    int port = atoi(EasyProp::getValue("properties.prop", "PORT_VILLAGE"));
    
    char* chaine = gestionLogin();
    
    NetworkClient client(host, port);
    FHMP fhmp;
    const char* messageToSend = FHMP::createPacket(LOGIN, chaine);
    cout << "messageToSend: " << messageToSend << endl;
    client.sendMessage(messageToSend);
    const char* reponseFromServer = fhmp.treatPacketClient(client.receiveMessage());
    cout << "reponseFromServer: " << reponseFromServer << endl;
    if(!strcmp(reponseFromServer, LOGIN_OUI)){
        int choix = -1;
        do{
            choix = menu();
            const char* packetToSend = gestionChoix(choix);
            if(!strcmp(packetToSend, EOC)){
                
            }else{
                client.sendMessage(packetToSend);
                const char* messageFromServer = fhmp.treatPacketClient(client.receiveMessage());
                cout << "messageFromServer: " << messageFromServer << endl;
                gestionMessageFromServer(messageFromServer);
            }
        }while(choix > 0 && choix <= 3);
    }else{
        cout << "Login ou password incorrect." << endl;
    }
    client.disconnect();
    return 0;
}

char * gestionLogin(){
    char* login = new char[255];
    char* password = new char[255];
    cout << "Veuillez entrez votre login: ";
    strcpy(login, "admin");
    //cin >> login;
    cout << "Veuillez entrez votre password: ";
    strcpy(password, "admin");
    //cin >> password;
    char* retour = new char[255];
    sprintf(retour, "%s#%s", login, password);
    return retour;
}

int menu(){
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

void gestionMessageFromServer(const char* messageFromServer){
    char *type, *message;
    char* temp = new char[strlen(messageFromServer)];
    strcpy(temp, messageFromServer);
    type = strtok_r(temp, ";", &message);
    
    if(!strcmp(type, BMAT_OUI)){
        cout << "Booking Material OK" << endl;
        cout << "Id action: " << message << endl;
    }else if(!strcmp(type, BMAT_NON)){
       cout << "Booking Material pas OK" << endl;
       cout << "erreur: " << message << endl;
    }
    delete [] temp;
    delete [] messageFromServer;
}

const char* gestionChoix(int choix){
    int choixAction = 0;
    switch(choix){
        case 1:
            do{
                choixAction = menuAction();
            }while(choixAction < 0 || choixAction > 3);
            return gestionChoixAction(choixAction);
            break;
        case 2:
            break;
        case 3:
            break;
        default:
            return EOC;
            break;
    }
}

const char* gestionChoixAction(int choix){
    char* retour = new char[255];
    char* chaine = new char[255];
    char* nomMateriel = new char[255];
    
    switch(choix){
        case 1:
            cout << "Quel matériel souhaitez-vous la livraison: ";
            cin >> nomMateriel;
            sprintf(chaine, "LIVRAISON#%s", nomMateriel);
            strcpy(retour, FHMP::createPacket(BMAT, chaine));
            break;
        case 2:
            cout << "ok" << endl;
            break;
        case 3:
            cout << "ok" << endl;
            break;
    }
    delete [] chaine;
    delete [] nomMateriel;
    return retour;
}
