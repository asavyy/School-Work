#include "rec.h"
#include <string.h>
#include <iostream>
using namespace std;

// default constructor
rec::rec()
{
	id = NULL;
	previous = NULL;
	next = NULL;
}

// parameterized constructor
rec::rec(char* i, char* fn, char* ln)
{
	id = new char[strlen(i) + 1];
	strcpy_s(id,strlen(i) + 1, i);
	strcpy_s(firstName,strlen(fn) + 1, fn);
	strcpy_s(lastName,strlen(ln) + 1, ln);
	previous = NULL;
	next = NULL;

}

// overloaded assignment operator
rec& rec:: operator=(const rec& r)
{
	if (this != &r) // avoid self assignment
	{
		if (id != NULL)
			delete id;
		id = new char[strlen(r.id) + 1];
		strcpy_s(id, strlen(r.id) + 1, r.id);
		strcpy_s(firstName, strlen(r.firstName) + 1, r.firstName);
		strcpy_s(lastName,strlen(r.lastName) + 1, r.lastName);
		previous = NULL;
		next = NULL;
	}

	return *this;
}

// copy constructor
rec::rec(const rec& r)
{
	id = new char[strlen(r.id) + 1];
	strcpy_s(id,strlen(r.id) + 1, r.id);
	strcpy_s(firstName,strlen(r.firstName) + 1, r.firstName);
	strcpy_s(lastName,strlen(r.lastName) + 1, r.lastName);
	previous = NULL;
	next = NULL;
}

// set the id, firstName and lastName of record
void rec::SetData(char* id_in, char* fn, char* ln)
{
	id = new char[strlen(id_in) + 1];
	strcpy_s(id,strlen(id_in) + 1, id_in);
	strcpy_s(firstName,strlen(fn) + 1, fn);
	strcpy_s(lastName,strlen(ln) + 1, ln);
}

// display the record
void rec::Print()
{
	if (id != NULL)
	{
		cout << id << " ";
		cout << firstName << " ";
		cout << lastName << " ";
		if (previous == NULL)
			cout << "NULL";
		else
			cout << previous;
		cout << " " << this << " ";
		if (next == NULL)
			cout << "NULL";
		else
			cout << next;
		cout << endl;
	}
}

// delete the record
rec::~rec()
{
	if (id != NULL)
		delete id;
}

// return the id of the record
char* rec::getId()
{
	return id;
}

// return the firstname of record
char* rec::getFirstName()
{
	return firstName;
}

// return the lastname of record
char* rec::getLastName()
{
	return lastName;
}

// set the next pointer of record
void rec::setNext(rec* next)
{
	this->next = next;
}

// set the previous pointer of record
void rec::setPrevious(rec* previous)
{
	this->previous = previous;
}

// return the next pointer of record
rec* rec::getNext()
{
	return next;
}

// return the previous pointer of record
rec* rec::getPrevious()
{
	return previous;
}