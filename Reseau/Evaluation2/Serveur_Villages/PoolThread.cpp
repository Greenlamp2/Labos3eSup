#include "PoolThread.h"

PoolThread::PoolThread()
{

}

PoolThread::PoolThread(const PoolThread& p)
{

}

PoolThread::~PoolThread()
{

}

void PoolThread::startThreads()
{

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
