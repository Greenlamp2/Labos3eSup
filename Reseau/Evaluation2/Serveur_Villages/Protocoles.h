#ifndef PROTOCOLES_H
#define PROTOCOLES_H
#include<map>
class Protocoles{
public:
    virtual string treatPacketServer(int numSocket, string packet) = 0;
    virtual string getLogin() = 0;
    virtual map<int, string> getUsers() = 0;
    virtual void removeUser(int numSocket) = 0;
};
#endif