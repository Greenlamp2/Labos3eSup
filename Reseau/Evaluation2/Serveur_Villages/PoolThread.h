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
#include <pthread.h>
    
class PoolThread{
private:
    pthread_t threadHandle[NB_MAX_CLIENT];
    int sockets[NB_MAX_CLIENT];
    int indiceCourant;
    pthread_mutex_t mutexIndiceCourant;
    pthread_cond_t condIndiceCourant;
public:
    PoolThread();
    ~PoolThread();
    void startThreads();
    void inject(int socket);
    static void* fctThread(void* param);
};

#endif