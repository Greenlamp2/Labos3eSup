#ifndef FHMP_H
#define FHMP_h

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
#include "properties.h"
    
class FHMP{
private:
public:
    FHMP();
    const char* treatPacket(const char* packet);
    void parsePacket(const char* packet, char* type, char* message);
};

#endif