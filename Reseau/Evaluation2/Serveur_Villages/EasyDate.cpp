#include "EasyDate.h"

EasyDate::EasyDate()
{
    this->timer = time(NULL);
    this->tm = localtime(&timer);
    this->dateFormater = NULL;
}

EasyDate::~EasyDate()
{
    if(this->dateFormater != NULL){
        delete [] this->dateFormater;
    }
}


char* EasyDate::getDateJour()
{
    this->dateFormater = new char[255];
    sprintf(this->dateFormater, "%d/%d/%d", tm->tm_mday, tm->tm_mon, 1900 + tm->tm_year);
    return this->dateFormater;
}

char* EasyDate::now()
{
    time_t timer = time(NULL);
    struct tm* tm = localtime(&timer);
    char* dateFormater = new char[255];
    sprintf(dateFormater, "%d/%d/%d", tm->tm_mday, tm->tm_mon, 1900 + tm->tm_year);
    return dateFormater;
}

