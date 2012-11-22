#include "EasyCSV.h"

EasyCSV::EasyCSV()
{

}

const char* EasyCSV::getValue(const char* nameFile, const char* key){
    FILE *file;
    file = fopen(nameFile, "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    char* retour = new char[255];
    
    do{
        fgets(buffer, 255, file);
        strcpy(retour, buffer);
        gauche = strtok_r(buffer, ";", &buff);
        if(!strcmp(gauche, key)){
            retour[strlen(retour)-1] = '\0';
            fclose(file);
            return retour;
        }
    }while(!feof(file));
    fclose(file);
    return NULL;
}

void EasyCSV::putValue(const char* nameFile, const char* key, const char* value){
    if(!EasyCSV::containsKey(nameFile, key)){
        FILE *file;
        file = fopen(nameFile, "a");
        fputs("\n", file);
        fputs(value, file);
        fclose(file);
    }
}

void EasyCSV::delValue(const char* nameFile, const char* key){

}

bool EasyCSV::containsKey(const char* nameFile, const char* key){
    FILE *file;
    file = fopen(nameFile, "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, ";", &buff);
        if(!strcmp(gauche, key)){
            fclose(file);
            return true;
        }
    }while(!feof(file));
    fclose(file);
    return false;
}