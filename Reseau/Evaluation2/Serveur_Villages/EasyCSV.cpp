#include "EasyCSV.h"

EasyCSV::EasyCSV()
{

}

char* EasyCSV::getValue(const char* nameFile, const char* key){
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
            delete [] buffer;
            return retour;
        }
    }while(!feof(file));
    fclose(file);
    delete [] buffer;
    delete [] retour;
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
            delete [] buffer;
            return true;
        }
    }while(!feof(file));
    fclose(file);
    delete [] buffer;
    return false;
}

bool EasyCSV::containsName(const char* nameFile, const char* key){
    FILE *file;
    file = fopen(nameFile, "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
        gauche = strtok_r(buffer, ";", &buff);
        gauche = strtok_r(buff, ";", &buff);
        if(!strcmp(gauche, key)){
            fclose(file);
            delete [] buffer;
            return true;
        }
    }while(!feof(file));
    fclose(file);
    delete [] buffer;
    return false;
}

char* EasyCSV::getLast(const char* nameFile){
    FILE *file;
    file = fopen(nameFile, "r");
    char* buffer = new char[255];
    
    char* buff;
    char* gauche;
    
    do{
        fgets(buffer, 255, file);
    }while(!feof(file));
    fclose(file);
    return buffer;
}
