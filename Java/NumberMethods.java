
// CS0410
// Name and Section here
// NumberMethods.java
// Program to show some method sinvolving numerical analysis



import java.util.Scanner;

public class NumberMethods {
	public static void main(String[] args) {
		int numIn, x1 = 7, x2 = 3;
		// Complete the main method by adding in 8 more 
		// System.out.println commands where each should
		// contain a call to a method
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter a positive integer: ");
                numIn = input.nextInt();
		System.out.println("The number of digits in this number is: " + numberDigits(numIn));
                System.out.println("The first digit in the number is:  " + firstNum(numIn));
                System.out.println("The last digit in the number is: " + lastNum(numIn));
                System.out.println("Does this number begin with 7? " + beginWith(numIn,7));
                System.out.println("Does this number begin with 3? " + beginWith(numIn,3));
                System.out.println("Does this number contain 7? " + contains(numIn, 7));
                System.out.println("Does this number contain 3? " + contains(numIn, 3));
                System.out.println("Is this number divisible by 13? " + divTest(13,numIn));
                System.out.println("Is this number divisible by 77? " + divTest(77,numIn));
                System.out.println("The amount of time(s) the digit 7 is contained in the number: " + containsBonus(numIn, 7));
                System.out.println("The amount of time(s) the digit 3 is contained in the number: " + containsBonus(numIn, 3));
	}
	
	// The method below returns the number of digits 
	// in the parameter num
	public static int numberDigits(int num) {
            int count = 0;
            
            while(num != 0){
                num /= 10;
                ++count;
            }
		
            return count;
	}
	
	// The method below returns the value of
	// the last digit in the num parameter
	public static int lastNum(int num) {
            int lastDigit = num % 10;
            return lastDigit;
	}
	
	// The method below returns the value of
	// the first digit in the num parameter
	public static int firstNum(int num) {
            int firstDigit = num;
            while(firstDigit >= 10){
                firstDigit = firstDigit / 10;
            }
            return firstDigit;
	}
	
	// The method below returns true if num begins
	// with x otherwise it returns false
	public static boolean beginWith(int num, int x) {
		if (firstNum(num) == x){
                    return true;
                } else {
                    return false;
                }
	}
	
	// The method below returns true if the num parameter is
	// evenly divisible by the x parameter (x is a factor of num) 
	// otherwise it returns false
	public static boolean divTest(int x, int num) {
            if (num % x == 0){
                return true;
            } else {
                return false;
            }
	}
	
	// The method below returns true if the num parameter
	// contains x as one of its digits otherwise it 
	// returns false
	public static boolean contains(int num, int x) {
            while (num > 0){
                if (num % 10 == x)
                    return true;
                    
                num = num /10;
                }
                return false;
            }
        
        public static int containsBonus(int num, int x) {
            int count = 0;
            while (num > 0){
                if (num % 10 == x) {
                    count++;      
                
                }
               num = num/10;
            }
            return count;
        }
}

	
	
	

	


