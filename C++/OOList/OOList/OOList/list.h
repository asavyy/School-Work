#pragma once

#include "rec.h"
class list
{
	friend class list;
private:
	rec* first;
	rec* last;
	rec* FindRecByID(char* id);
public:
	list();
	list& operator=(const list& l);
	~list();
	int AddItem(rec r);
	int DeleteItem(char* delid);
	int PrintList(int order);
};