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
#include <string.h>
#include <stdlib.h>
    
class EasyDate{
private:
public:
    static string now();
    static int getSeconds(string date);
};

#endif