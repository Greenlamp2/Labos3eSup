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

int EasyDate::getSeconds(string date){
    char *bDay, *bMonth, *bYear, *buffer;
    string day, month, year;
    char* cstr = new char[date.size()+1];
    strcpy(cstr, date.c_str());
    bDay = strtok_r(cstr, "/", &buffer);
    bMonth = strtok_r(buffer, "/", &buffer);
    bYear = strtok_r(buffer, "/", &buffer);
    day = bDay;
    month = bMonth;
    year = bYear;
    delete [] cstr;
    struct tm myTime;
    time_t out;
    myTime.tm_sec = 0;
    myTime.tm_min = 0;
    myTime.tm_hour = 0;
    myTime.tm_wday = 0;
    myTime.tm_yday = 0;
    myTime.tm_isdst = 0;
    myTime.tm_gmtoff = 0;
    myTime.tm_mday = atoi(day.c_str());
    myTime.tm_mon = atoi(month.c_str());
    myTime.tm_year = atoi(year.c_str()) - 1900;
    out = mktime(&myTime);
    return out;
}

