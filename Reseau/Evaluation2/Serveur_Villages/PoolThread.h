#ifndef POOLTHREAD_H
#define POOLTHREAD_H

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
#include "FHMP.h"
#include "NetworkServer.h"
    
class PoolThread{
private:
    pthread_t threadHandle[NB_MAX_CLIENT];
public:
    PoolThread();
    PoolThread(const PoolThread &p);
    ~PoolThread();
    void startThreads();
    void inject(int socket);
};

#endif