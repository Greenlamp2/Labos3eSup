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
    
int main(){
    const char* host = EasyProp::getValue("properties.prop", "HOST");
    int port = atoi(EasyProp::getValue("properties.prop", "PORT_VILLAGE"));
    
    char* chaine = gestionLogin();
    
    NetworkClient client(host, port);
    FHMP fhmp;
    client.sendMessage(fhmp.createPacket(LOGIN, chaine));
    const char* reponseFromServer = fhmp.treatPacketClient(client.receiveMessage());
    if(!strcmp(reponseFromServer, LOGIN_OUI)){
        int choix = -1;
        do{
            choix = menu();
            cout << "choix: " << choix << endl;
        }while(choix > 0 && choix <= 3);
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
