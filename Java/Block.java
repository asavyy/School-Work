/**
 *
 * @author Brice
 * 
 */
import java.util.*;

public class Block {

   public static void main(String[] args) {
       //declaring a scanner for user input
       Scanner in = new Scanner(System.in);
       String choice = "Y";//variable used to store user input for determining if they want another calc or not
       while (choice.equals("Y")) {
           double l, w, h;//declaring variables for the dimensions of the block
           double volume,surfaceArea;//variables used to store volume and surfaceArea(helps with the display method)
           l=getInput("Please enter the length of the block: ");//method call that gets the length via the user
           w=getInput("Please enter the width of the block: ");//method call that gets the width via the user
           h=getInput("Please enter the height of the block: ");//method call that gets the height via the user
           //calling method to calculate volume of block
           volume=volBlock(l, w, h);
           //calling method to calculate surface area of block
           surfaceArea=surfaceAreaBlock(l, w, h);
           //calling the display() method
           display(volume, surfaceArea);
           //asking user whether want to have another calculation
           System.out.println("Would you like to do another calculation?(Y/N): ");
           choice = in.next().toUpperCase();//reading user input and converting to upper case
       }

       //showing a message when program ends (basically flavor text) :)
       System.out.println("Program now ending...");
   }
   //method to get input from user
   public static double getInput(String input) {
       double inputParam;//declaring variable
       Scanner fin = new Scanner(System.in);
       System.out.print(input);
       inputParam=fin.nextDouble();//reading input
       return inputParam;//return input      
   }
   //method to calculate the volume of the block
   public static double volBlock(double length, double width, double height) {
       double volume;
       //calculating the volume
       volume = length * width * height;
       return volume;//return volume
   }
   //method to calculate surface area of block
   public static double surfaceAreaBlock(double l, double w, double h) {
       double surfaceArea;
       //calculating the surface area
       surfaceArea = 2 * (l * h + l * w + h * w);
       //return surface area of the block
       return surfaceArea;
   }
   //method to display the results of the calculations
   public static void display(double volume,double surfaceArea) {
       System.out.println("Volume = " + volume);//prints volume
       System.out.println("Surface Area = " + surfaceArea);//prints surface area
   }
}