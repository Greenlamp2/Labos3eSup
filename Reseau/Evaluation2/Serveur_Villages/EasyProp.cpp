#include "EasyProp.h"

string EasyProp::getValue(string nameFile, string key){
    FILE *file;
    file = fopen(nameFile.c_str(), "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, "=", &buff);
        if(!strcmp(gauche, key.c_str())){
            buff[strlen(buff)-1] = '\0';
            fclose(file);
            return buff;
        }
    }while(!feof(file));
    fclose(file);
    return NULL;
}

bool EasyProp::containsKey(string nameFile, string key){
    FILE *file;
    file = fopen(nameFile.c_str(), "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, "=", &buff);
        if(!strcmp(gauche, key.c_str())){
            fclose(file);
            return true;
        }
    }while(!feof(file));
    fclose(file);
    return false;
}