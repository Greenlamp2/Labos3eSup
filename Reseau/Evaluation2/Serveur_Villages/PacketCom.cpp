#include "PacketCom.h"

PacketCom::PacketCom(){
    this->type = NULL;
    this->object = NULL;
}

void PacketCom::setType(const char* type)
{
    this->type = new char[strlen(type) + 1];
    strcpy(this->type, type);
}

const char* PacketCom::getType()
{
    return this->type;
}

void PacketCom::setObject(void* object)
{

}

void* PacketCom::getObject()
{

}
