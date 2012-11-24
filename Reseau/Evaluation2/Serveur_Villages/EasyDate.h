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
#include<sstream>
#include <time.h>
    
class EasyDate{
private:
public:
    static string now();
};

#endif