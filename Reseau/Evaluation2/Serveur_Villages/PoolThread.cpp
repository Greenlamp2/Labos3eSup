#include "PoolThread.h"

PoolThread::PoolThread()
{
    this->startThreads();
}

PoolThread::PoolThread(const PoolThread& p)
{

}

PoolThread::~PoolThread()
{

}

void PoolThread::startThreads()
{
    int i=0;
    for(i = 0; i<NB_MAX_CLIENT; i++){
        
    }
}

void PoolThread::inject(int socket)
{
    NetworkServer networkServer(socket);
    FHMP fhmp;
    while(networkServer.isConnected()){
        const char* messageFromServer = networkServer.receiveMessage();
        const char* messageToClient = fhmp.treatPacket(messageFromServer);
        networkServer.sendMessage(messageToClient);
    }
}