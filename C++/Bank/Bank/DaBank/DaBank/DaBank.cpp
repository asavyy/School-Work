#include <cstdlib>
#include <iostream>
#include <iomanip>

using namespace std;

int main(int argc, char** argv) {

    for (;;)
    {
        //declare all the attributes
        double input;
        int hundreds = 0;
        int fifties = 0;
        int twenties = 0;
        int tens = 0;
        int fives = 0;
        int ones = 0;
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;

        //Ask user to enter the amount
        cout << "Please enter an amount of money between $0-100: $";
        cin >> input;
        double pennies = input * 100;
        int intPennies;
        while (true)
        {
            if (pennies / 10000 >= 1)
            {
                hundreds++;
                pennies -= 10000;
            }
            else if (pennies / 5000 >= 1)
            {
                fifties++;
                pennies -= 5000;
            }
            else if (pennies / 2000 >= 1)
            {
                twenties++;
                pennies -= 2000;
            }
            else if (pennies / 1000 >= 1)
            {
                tens++;
                pennies -= 1000;
            }
            else if (pennies / 500 >= 1)
            {
                fives++;
                pennies -= 500;
            }
            else if (pennies / 100 >= 1)
            {
                ones++;
                pennies -= 100;
            }
            else if (pennies / 25 >= 1)
            {
                quarters++;
                pennies -= 25;
            }
            else if (pennies / 10 >= 1)
            {
                dimes++;
                pennies -= 10;
            }
            else if (pennies / 5 >= 1)
            {
                nickels++;
                pennies -= 5;
            }
            else
                break;
        }
        intPennies = pennies;
        //print number of pennies required
        cout << "The number of pennies: " << intPennies << endl;
        //print number of nickels required
        cout << "The number of nickels: " << nickels << endl;
        //print number of dimes required
        cout << "The number of dimes: " << dimes << endl;
        //print number of quarters required
        cout << "The number of quarters: " << quarters << endl;
        //print number of one dollar required
        cout << "The number of one dollar bills: " << ones << endl;
        //print number of five dollar required
        cout << "The number of five dollar bills: " << fives << endl;
        //print number of ten dollars required
        cout << "The number of ten dollar bills: " << tens << endl;
        //print number of twenties dollar required
        cout << "The number of twenty dollar bills: " << twenties << endl;
        //print number of fifties dollar required
        cout << "The number of fifty dollar bills: " << fifties << endl;
        //print number of one-hundred dollar bills required
        cout << "The number of one-hundred dollar bills: " << hundreds << endl;


        //asking the user if they would like to repeat the program
        cout << "Do you want to repeat the program? (y/n)" << endl;
        char answer;
        cin >> answer;
        if (answer == 'n')
            break; // exit loop
    }
    cout << "Program now ending..." << endl;
    return 0;
}