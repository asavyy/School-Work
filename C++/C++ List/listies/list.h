#pragma once

struct Rec
{
    char* id;
    char firstname[15];
    char lastname[15];
    Rec* previous;
    Rec* next;
};


int AddItem(Rec r);
int DeleteItem(char* delid);
void PrintList(int order);
