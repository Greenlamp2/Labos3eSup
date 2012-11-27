#ifndef PROTOCOLES_H
#define PROTOCOLES_H
#include<list>
class Protocoles{
public:
    virtual string treatPacketServer(int numSocket, string packet) = 0;
    virtual string getLogin() = 0;
    virtual list<string> getUsers() = 0;
    virtual void removeUser(int numSocket) = 0;
};
#endif