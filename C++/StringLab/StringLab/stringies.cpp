#include <iostream>
#include <cstring>
using namespace std;

//This function gathers input for strings. It accepts three lines of
// user-input text and stores these entered lines as three individual strings

void getInput(char* x[]) {
	cout << "Enter three individual strings: ";
	char* s1 = new char[100];
	cin.getline(s1, 100, '\n');
	x[0] = &s1[0];
	char* s2 = new char[100];
	cin.getline(s2, 100, '\n');
	x[1] = &s2[0];
	char* s3 = new char[100];
	cin.getline(s3, 100, '\n');
	x[2] = &s3[0];
}

//This function basically prints out each string in our string array
// It also prints a linefeed after each string for added readability.
void displayStrings(char* x[]) {
	int i = 0; // counter variable 
	while (i < 3) { //this loop basically takes in each string individually and prints it, then increments the counter
		cout << *(x + i) << endl;
		i++;
	}
}

// This function searches for a keyword (z in this case). It begins searching
// for a specified user-input text within our pointer array. If it is found, it will print 
// a message to the user indicating that the string was found along with the amount of 
// times that string was found within a specific string. Along with that, it will also print 
// the string itself. 
void search(char* x[], char* z) {
	int i = 0; //basic counter var
	int strI = 0; // counter for the amount of times a string is found
	while (i < sizeof(x) - 1) { // while i is less than the size of the character array
		if (strstr(x[i], z)) {  // compare the string at the location to the wanted string
			cout << z << " has been located in the string '" << x[i] << "'" << endl; // this message is printed if z is located
			strI++; // increment the number of times a string has been found
			cout << z << " has been located " << strI << " time(s)." << endl; // output the number of times a the string has been located

		}
		else {
			cout << z << " is not in " << x[i] << endl; // outputs that z is not in our strings
		}
		i++; //moves us to the next pointer
	}
}

// This function sorts the arrays. It re-orders the pointer elements such that
// the strings are ordered. it works with any size array that is passed in.
void sort(char* x[]) {
	int i2 = 0; //  basic counter var
	int result; // basically a temporary variable.

	for (int i = 0; i < sizeof(x) - 1; i++) {       // this loop will basically run until i is > size of our arr
		for (int j = 0; j < sizeof(x) - 2; j++) {   // also includes a nested for loop that runs until j is > the second to last size of arr
			result = strcmp(x[i2], x[i2 + 1]); //compares the 1st str & 2nd str
			
			if (result > 0) // if the result of the compare is 0, then we know that the first string is larger than the 2nd, 
				swap(x[i2], x[i2 + 1]); // so we swap these two strings. could also be done with < 0, depending on how we want it sorted.
			i2++; // increment our counter
		}
		i2 = 0; // reset our counter.
	}
}

//This function will print out the length of each string in the array.
void showLens(char* x[]) {
	int i = 0; // basic counter var
	int strCount = 1; // this keeps track of our string number (starting at 1)
	while (i < 3) { // in this loop, while the counter is less than our number of strings, print
		cout << "String " << strCount << " is: " << strlen(x[i]) << endl; // the number of the string and its corresponding length using strlen
		i++; // increment our counter
		strCount++; // increment out string number
	}
}

// This function will search through all of the string elements in the array and return the total number of alphabetic 
// {a-z, A-Z} characters found in each string. It returns an integer, which is the total number of
// alphabetical characters in all three strings. 
int AlphaChars(char* x[]) {
	int i = 0; //this time, we use i to keep track of our location in the array.
	int letterCounter = 0; //keeps track of the number of alpha chars
	int index = 0; // keeps track of our index
	while (i < sizeof(x) - 1) { // moves through our pointers
		while (*(x[i] + index) != NULL) { // goes to the end of the str
			if (isalpha(*(x[i] + index))) { // this checks if the value is a letter or not
				letterCounter++; // if it is, increment our letterCounter
			}
			index++; // moves our index to the next letter of the str
		}
		index = 0; // reset our index
		i++; // move to the next ptr
	}
	return letterCounter;
}