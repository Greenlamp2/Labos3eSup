#ifndef EASYDATE_H
#define EASYDATE_H

#ifdef LIN
    using namespace std;
    #include<iostream>
    #include<fstream>
    #include<string>
#endif

#ifdef SUN
    #include<iostream.h>
    #include<fstream.h>
#endif
#include <time.h>
    
class EasyDate{
private:
    time_t timer;
    struct tm* tm;
    char* dateFormater;
public:
    EasyDate();
    ~EasyDate();
    char* getDateJour();
    static char* now();
};

#endif