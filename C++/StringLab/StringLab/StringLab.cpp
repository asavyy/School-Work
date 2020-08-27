#include <iostream>
#include "stringies.h"

using namespace std;

int main()
{
	char* stringies[3];		//creates an array of pointers 
	int letterCounter = 0;
	getInput(stringies);
	cout << "Displaying the strings... " << endl;
	displayStrings(stringies);
	cout << "Enter a string/character you would like to search for: " << endl;
	char searchin[100];		//array to store value searching for
	cin.getline(searchin, 100, '\n');		//gets input
	cout << "Searching the strings..." << endl;
	search(stringies, searchin);
	cout << "Sorting the strings..." << endl;
	sort(stringies);
	cout << "Displaying the strings after sort..." << endl;
	displayStrings(stringies);
	cout << "Displaying the length of the strings..." << endl;
	showLens(stringies);
	cout << "Counting how many alphabetical characters are in the strings..." << endl;
	letterCounter = AlphaChars(stringies);
	cout << "There are " << letterCounter << " letters in the combined strings" << endl;
	
	return 0;
}
