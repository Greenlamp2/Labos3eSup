#include "FHMP.h"

FHMP::FHMP()
{

}

const char* FHMP::treatPacket(const char* packet)
{
    char* type;
    char* message;
    FHMP::parsePacket(packet, type, message);
}

void FHMP::parsePacket(const char* packet, char* type, char* message)
{
    
}

