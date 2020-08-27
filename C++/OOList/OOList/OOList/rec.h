#pragma once

class rec {
	char* id;
	char firstName[15];
	char lastName[15];
	rec* previous;
	rec* next;

public:
	rec();
	rec(char* i, char* fn, char* ln);
	rec& operator=(const rec& r);
	rec(const rec& r);
	void SetData(char* id_in, char* fn, char* ln);
	void Print();
	~rec();
	char* getId();
	char* getFirstName();
	char* getLastName();
	void setNext(rec* next);
	void setPrevious(rec* previous);
	rec* getNext();
	rec* getPrevious();
};