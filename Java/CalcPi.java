/**
 *
 * @author Brice
 */
import java.util.*;
public class CalcPi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int numberOfTerms = 0, odd = 1; //variable to store the # of terms. odd it set to 1 because the 1st term in the pi series denominator is odd, (4/1)
        double pi = 0.0; //variable for pi
        
        //asking the user to input the number of terms they would like to calculate pi with.
        System.out.println("Please enter the number of terms: ");
        numberOfTerms = in.nextInt();
        
        
        //calculation section. here we calculate pi based on the number of terms the user has input.
        for(int i = 1;i <= numberOfTerms;i++){
        double currentTerm = 0.0;
        if(i%2==0){
            currentTerm =(double)-4/odd;
        } else {
            currentTerm =(double)4/odd;
        }
        odd = odd + 2;
        pi = pi + currentTerm;
    }
        System.out.println("The calculation of pi is: " + pi);
    }
    
}
