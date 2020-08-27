#include "list.h"
#include <string.h>
#include <iostream>
using namespace std;

// create an empty list
list::list()
{
	first = NULL;
	last = NULL;
}

// overloaded assignment operator
list& list::operator=(const list& l)
{
	if (this != &l) // avoid self assignment
	{
		// delete the existing records
		while (first != NULL)
		{
			rec* temp = first;
			first = first->getNext();
			delete(temp);
		}

		last = NULL;

		rec* current = l.first;
		// loop to insert the record into the list
		while (current != NULL)
		{
			rec r(current->getId(), current->getFirstName(), current->getLastName());
			AddItem(r);
			current = current->getNext();
		}
	}

	return *this;
}

// delete all the records of the list
list::~list()
{
	while (first != NULL)
	{
		rec* temp = first;
		first = first->getNext();
		delete(temp);
	}

	last = NULL;
}

// add a record to the list
int list::AddItem(rec r) {

	if (FindRecByID(r.getId()) != NULL) {
		return 0;
	}

	rec* newRec;
	newRec = new rec(r.getId(), r.getFirstName(), r.getLastName());

	if (newRec == NULL) {
		return 0;
	}

	if (first == NULL) {
		first = newRec;
		last = newRec;
		newRec->setNext(NULL);
		newRec->setPrevious(NULL);
		return 1;
	}

	rec* current;

	current = first;
	while (current) {
		int stringCompVal = strcmp(r.getLastName(), current->getLastName());
		if (stringCompVal < 0) {
			break;
		}
		current = current->getNext();
	}

	if (current == NULL) {
		newRec->setNext(NULL);
		newRec->setPrevious(last);
		last->setNext(newRec);
		last = newRec;
		return 1;
	}

	//adding to front before current.
	if (current->getPrevious() == NULL) {
		newRec->setPrevious(NULL);
		newRec->setNext(first);
		first->setPrevious(newRec);
		first = newRec;
		return 1;
	}

	//adding to middle before current
	newRec->setNext(current);
	newRec->setPrevious(current->getPrevious());
	current->getPrevious()->setNext(newRec);
	current->setPrevious(newRec);
	return 1;

}

// delete a record from the list
int list::DeleteItem(char* delid)
{
	rec* delRec;
	delRec = FindRecByID(delid);
	if (delRec == NULL) {
		return 0;
	}

	if (delRec == first && delRec == last) {
		first = NULL;
		last = NULL;
	}
	else if (delRec->getPrevious() == NULL) {
		delRec->getNext()->setPrevious(NULL);
		first = delRec->getNext();
	}
	else if (delRec->getNext() == NULL) {
		delRec->getPrevious()->setNext(NULL);
		last = delRec->getPrevious();
	}
	else {
		delRec->getPrevious()->setNext(delRec->getNext());
		delRec->getNext()->setPrevious(delRec->getPrevious());
	}

	delete delRec;
	return 1;
}

// display the list
int list::PrintList(int order)
{
	rec* current;

	if (order == 0) {
		current = first;
		cout << endl;
		while (current) {
			current->Print();
			current = current->getNext();
		}
	}
	else // descending
	{
		current = last;
		cout << endl;
		while (current) {
			current->Print();
			current = current->getPrevious();
		}
	}

	return 1;
}

// helper function to find a record
rec* list::FindRecByID(char* id)
{
	rec* current;
	current = first;
	while (current) {
		if (strcmp(current->getId(), id) == 0) {
			return current;
		}
		current = current->getNext();
	}

	return NULL;
}