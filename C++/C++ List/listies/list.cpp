#include "list.h"
#include <iostream>
using namespace std;

Rec* first = NULL;
Rec* last = NULL;
Rec* current;

//This function determines whether or not a duplicate ID exists. For better code, and cleaner code, 
//I put it into its own function to use within loops later in the program. That way, it is a simple
//True or False check to determine whether or not there is a duplicate ID being entered.
bool dupeID(Rec r)
{
	current = first;
	while (current != NULL)
	{
		if (strcmp(r.id, current->id) == 0)
		{
			return true;
		}
		current = current->next;
	}
	return false;
}
// This is another boolean function that is used to check to see if an ID exists in the list
//already or not. 
bool checkExists(char a[15])
{
	current = first;
	while (current != NULL)
	{
		if (strcmp(a, current->id) == 0)
		{
			return false;
		}
		current = current->next;
	}
	return true;
}


int AddItem(Rec r)
{
	Rec* current = new Rec; // We begin by creating a new rec.
	Rec* firstCopy = first;
	bool dupe = dupeID(r);
	if (dupe) // As stated earlier, we are using the boolean function defined earlier in order to make this check easier. 
	{
		cout << "You've entered a duplicate ID. Please re-enter the record with a valid ID." << endl;
		return 0;
	} // if it's true we output an error message to the screen notifying the user that there is a problem with the ID they've entered.

	//If first is null, we can assume there's nothing contained within the list. Therefore, we add the record to the first position within the list
	// We can only assume this because if first is null, then we can assume that the list is empty.
	if (first == NULL) {
		current->id = r.id;
		strcpy_s(current->firstname,15, r.firstname);
		strcpy_s(current->lastname,15, r.lastname);
		current->next = NULL;
		current->previous = NULL;
		first = current;
		last = current;
		return 1;
	} else {
		//These next loops are if the list is NOT empty. The list is being sorted alphabetically by last name, therefore we have to search through the list and insert
		//the record in the appropriate position. The first if adds the record to the last position in the list. 
		if (((string)last->lastname).compare(r.lastname) < 0) {
			current->id = r.id;
			strcpy_s(current->firstname, 15, r.firstname);
			strcpy_s(current->lastname, 15, r.lastname);
			current->previous = last;
			current->next = NULL;
			last->next = current;
			last = current;
			return 1;
		}
		//Here, we add the record into the correct spot in the list.
		else if (((string)first->lastname).compare(r.firstname) > 0) {
			current->id = r.id;
			strcpy_s(current->firstname, 15, r.firstname);
			strcpy_s(current->lastname, 15, r.lastname);
			current->previous = NULL;
			current->next = first;
			first->previous = current;
			first = current;
			return 1;
		}
		else {
			bool check = true;
			while (check == true) {
				if (strcmp(((char*)first->next), r.lastname) > 0) {
					strcpy_s(current->lastname, 15, r.lastname);
					current->id = r.id;
					current->previous = firstCopy;
					current->next = firstCopy->next;
					firstCopy->next = current;
					check = false;
					return 1;
				}
				firstCopy = firstCopy->next;
			}
		}
	}
	return 1;

}
int DeleteItem(char* delid) {
	Rec* firstCpy1 = NULL;
	Rec* firstCpy2 = NULL;
	// here we check to see whether an ID actually exists or not. If it doesn't, the user will be prompted an error message that states that the ID
	//they input is not in the list. 
	if (first == NULL) {
		cout << "The list is empty" << endl;
		return 0;
	}
	// If the ID does exist, then we will go ahead and delete it.
	if (*first->id == (*delid)) {
		firstCpy1 = first;
		first = first->next;
		cout << "Delete Successful" << endl;
		return 1;
	}
	while (firstCpy2->next->next != NULL) {
		if (firstCpy2->next->id == delid) {
			firstCpy1 = firstCpy2->next;
			firstCpy2->next = firstCpy1->next;
			firstCpy1->next->previous = firstCpy2;
			cout << "Delete Successful" << endl;
			return 1;
		}
	}
	if (firstCpy2->next->id == delid) {
		firstCpy1 = firstCpy2->next;
		firstCpy2->next = NULL;
		cout << "Delete Successful" << endl;
		return 1;
	}
	cout << "Delete Failed" << endl;
	return 0;
}
void PrintList(int order) {
	Rec* pos;
	if (order == 0) {
		pos = first;
		if (pos == NULL) { // Checking to see if our list is empty or not. If it is, then we will prompt the user with an error message. 
			cout << "The list is currently empty." << endl;
		}
		while (pos != NULL) { // If not, we will begin printing the list in ascending order.
			//The record is printed on one line with spaces in between them, as the project requires.
			cout << pos->id << " ";
			cout << pos->firstname << " ";
			cout << pos->lastname << " ";
			cout << pos->previous << " ";
			cout << pos << " ";
			cout << pos->next << " " << endl;
			cout.flush();
			pos = pos->next;
		}
	}
	else if (order == 1) { // If the user selects a 0, then we print in descending order. 
		{
			pos = first;
			while (pos != NULL) //Here we set pointer to be the last record in the list and move backwards through the list.
			{
				last = pos;
				pos = pos->next;
			}
			pos = last;
			if (pos == NULL) { // If the list is empty, we prompt the user with an error message.
				cout << "The list is currently empty.";
			}
			while (pos != NULL) {
				cout << pos->id << " ";
				cout << pos->firstname << " ";
				cout << pos->lastname << " ";
				cout << pos->previous << " ";
				cout << pos << " ";
				cout << pos->next << " " << endl;
				if (pos->previous == NULL)
				{
					break;
				}
				pos = pos->previous;
			} 
		}
	}
}