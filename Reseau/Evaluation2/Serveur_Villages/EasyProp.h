#ifndef EASYPROP_H
#define EASYPROP_H

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
    
#include "string.h"
    
class EasyProp{
private:
public:
    EasyProp();
    static string getValue(string nameFile, string key);
    static bool containsKey(string nameFile, string key);
};

#endif