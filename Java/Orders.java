/**
 *
 * @author Brice
 */

import java.util.*;
public class Orders {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in); //declare scanner
        
        //this section is for declaring our variables. We store each product code and product value into its own variable
        //in additon, we also declare quantity to keep track of how many products there are
        //and also, each product has its own counter to help calculate the cost of the items at when the program is ended.
        String A1 = "A105";
        String A2 = "A207";
        String D6 = "D671";
        String X1 = "X111";
        String X9 = "X902";
        String terminate = "ZZZZ";
        String productCode = "";
        int quantity;
        int A1Counter = 0, A2Counter = 0, D6Counter = 0, X1Counter = 0, X9Counter = 0;
        double A1QuantityPrice = 13.67, A2QuantityPrice = 21.65, D6QuantityPrice = 20.55, X1QuantityPrice = 39.99, X9QuantityPrice = 4.56, totalCost;
        
        //our first initial check. The user can enter zzzz 0 here to terminate immediately. This also checks for correct output.
        System.out.println("Please enter the product code and quantity: ");
        productCode = in.next().toUpperCase();
        quantity = in.nextInt();
        // here is where it checks for correct output. If the product code entered doesnt match any existing product codes,
        // or the quantity is less than 0, we prompt the user to enter it again until it is correct.
        while(!productCode.equals(A1) && !productCode.equals(A2) && !productCode.equals(D6) && !productCode.equals(X1) && !productCode.equals(X9) && !productCode.equals(terminate) || quantity < 0){
            System.out.println("Error. Re-enter the product code and quantity: ");
            productCode = in.next().toUpperCase();
            quantity = in.nextInt();
           }
        
        //once the input is correct we shift down into our next while loop, which will determine what the user entered and then 
        //correctly carry out the proper code for each case of user input from zzzz 0 to even telling if our user has entered
        // a negative number or an incorrect product code.
        while (productCode.equals(A1) || productCode.equals(A2) || productCode.equals(D6) || productCode.equals(X1) || productCode.equals(X9) || productCode.equals(terminate) || quantity >= 0 ){
            if (productCode.equals(terminate) && quantity == 0){
                System.out.println("Program ending...");
                break;
            }else if (quantity < 0){
                System.out.println("The quantity value cannot be negative.");
            } else if (productCode.equals(A1)){
                A1Counter = A1Counter + quantity;
            } else if (productCode.equals(A2)){
                A2Counter = A2Counter + quantity;
            } else if (productCode.equals(D6)){
                D6Counter = D6Counter + quantity;
            } else if (productCode.equals(X1)){
                X1Counter = X1Counter + quantity;
            } else if (productCode.equals(X9)) {
                X9Counter = X9Counter + quantity;
            } else {
                System.out.println("You have not entered a correct product code.");  
            }
            //here, we continue our while loop by repeatedly asking for input until our end condition is entered,
            //zzzz 0
            System.out.println("Please enter the product code and quantity: ");
            productCode = in.next().toUpperCase();
            quantity = in.nextInt();
        }
        // here, the calculations are carried out for each individual product, and also the totalCost is calculated by adding
        //each of the products costs together.
        A1QuantityPrice = A1Counter*A1QuantityPrice;
        A2QuantityPrice = A2Counter*A2QuantityPrice;
        D6QuantityPrice = D6Counter * D6QuantityPrice;
        X1QuantityPrice = X1Counter * X1QuantityPrice;
        X9QuantityPrice = X9Counter * X9QuantityPrice;
        totalCost = A1QuantityPrice + A2QuantityPrice + D6QuantityPrice + X1QuantityPrice + X9QuantityPrice;
        
        //this is our output section where we format each individual product code, how many items they contain respectively, 
        //the cost of the individual products, and the total cost of all the individual products combined.
        System.out.print("A105: " + A1Counter + " items" + "     " + "Price: $");
        System.out.printf("%.2f", A1QuantityPrice);
        System.out.println();
        System.out.print("A207: " + A2Counter + " items" + "     " + "Price: $");
        System.out.printf("%.2f", A2QuantityPrice);
        System.out.println();
        System.out.print("D671: " + D6Counter + " items" + "     " + "Price: $");
        System.out.printf("%.2f", D6QuantityPrice);
        System.out.println();
        System.out.print("X111: " + X1Counter + " items" + "     " + "Price: $");
        System.out.printf("%.2f", X1QuantityPrice);
        System.out.println();
        System.out.print("X902: " + X9Counter + " items" + "     " + "Price: $");
        System.out.printf("%.2f", X9QuantityPrice);
        System.out.println();
        System.out.print("The total price: ");
        System.out.printf("%.2f", totalCost);
        System.out.println();               
    }
        
  }
       
