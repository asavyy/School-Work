// List Lab.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
#include <iostream>
#include "list.h"
using namespace std;

int main()
{
	int choice;
	do {
		cout << "What would you like to do?(1 to add an item, 2 to delete an item, 3 to print the list, 0 to quit: ";
		cin >> choice;
		if (choice == 0) {
			exit(0);
		}
		else if (choice == 1) {
			Rec record;
			char firstName[15], lastName[15], idTemp[15];
			cout << "Please enter the ID: " << endl;
			cin >> idTemp;
			record.id = new char[sizeof(idTemp) + 1];
			strcpy_s(record.id, sizeof(idTemp), idTemp);
			cout << "Please enter the first name: " << endl;
			cin >> record.firstname;
			cout << "Please enter the last name: " << endl;
			cin >> record.lastname;
			AddItem(record);
		}
		else if (choice == 2) {
			char ptrID[15];
			cout << "Enter an ID that you would like to delete: " << endl;
			cin >> ptrID;
			DeleteItem(ptrID);
		}
		else if (choice == 3) {
			int answer = 0;
			cout << "Enter the order in which you would like to print the list (0 for ascending, 1 for descending): " << endl;
			cin >> answer;
			PrintList(answer);
		}
		else {
			cout << "Please enter a valid menu option.";
		}

	} while (choice != 0);
}

