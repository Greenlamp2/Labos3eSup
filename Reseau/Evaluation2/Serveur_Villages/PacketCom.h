#ifndef DEF_NETWORKCLIENT
#define DEF_NETWORKCLIENT

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
    
class PacketCom{
private:
    char* type;
    void* object;
public:
    PacketCom();
    void setType(const char* type);
    void setObject(void* object);
    const char* getType();
    void* getObject();
};
#endif