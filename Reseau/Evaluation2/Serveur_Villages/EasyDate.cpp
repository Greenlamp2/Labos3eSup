#include "EasyDate.h"

string EasyDate::now()
{
    time_t timer = time(NULL);
    struct tm* tm = localtime(&timer);
    string date;
    std::ostringstream out;
    out << tm->tm_mday;
    out << "/";
    out << tm->tm_mon;
    out << "/";
    out << 1900+tm->tm_year;
    date = out.str();
    return date;
}

