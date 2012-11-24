#include "EasyCSV.h"

EasyCSV::EasyCSV()
{

}

string EasyCSV::getValue(string nameFile, string key){
    FILE *file;
    file = fopen(nameFile.c_str(), "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    char* retour = new char[255];
    
    do{
        fgets(buffer, 255, file);
        strcpy(retour, buffer);
        gauche = strtok_r(buffer, ";", &buff);
        if(!strcmp(gauche, key.c_str())){
            retour[strlen(retour)-1] = '\0';
            fclose(file);
            delete [] buffer;
            return retour;
        }
    }while(!feof(file));
    fclose(file);
    delete [] buffer;
    delete [] retour;
    return NULL;
}

void EasyCSV::putValue(string nameFile, string key, string value){
    if(!EasyCSV::containsKey(nameFile, key)){
        FILE *file;
        file = fopen(nameFile.c_str(), "a");
        fputs("\n", file);
        fputs(value.c_str(), file);
        fclose(file);
    }
}

void EasyCSV::delValue(string nameFile, string key){

}

bool EasyCSV::containsKey(string nameFile, string key){
    FILE *file;
    file = fopen(nameFile.c_str(), "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, ";", &buff);
        if(!strcmp(gauche, key.c_str())){
            fclose(file);
            delete [] buffer;
            return true;
        }
    }while(!feof(file));
    fclose(file);
    delete [] buffer;
    return false;
}

bool EasyCSV::containsName(string nameFile, string key){
    FILE *file;
    file = fopen(nameFile.c_str(), "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, ";", &buff);
        gauche = strtok_r(buff, ";", &buff);
        if(!strcmp(gauche, key.c_str())){
            fclose(file);
            delete [] buffer;
            return true;
        }
    }while(!feof(file));
    fclose(file);
    delete [] buffer;
    return false;
}

char* EasyCSV::getLast(string nameFile){
    FILE *file;
    file = fopen(nameFile.c_str(), "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
    }while(!feof(file));
    fclose(file);
    return buffer;
}
