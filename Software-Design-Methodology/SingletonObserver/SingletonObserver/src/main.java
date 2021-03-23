import java.util.*;
/**
 *
 * @author brice
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileManager thing = FileManager.getInstance();
        AverageDisplay average = new AverageDisplay(thing);
        GradesDisplay displayList = new GradesDisplay(thing);
        Scanner input = new Scanner(System.in);
        int test = 1000;
        int option = 0;
        
        System.out.println("First Test Case: Deleting an empty list...");
        System.out.println("Performing first delete..");
        thing.deleteAllGrades();
        System.out.println("--------------------------------"); 

        System.out.println("Performing second delete...");
        thing.deleteAllGrades();
        System.out.println("--------------------------------"); 
        
        System.out.println("Second Test Case: Displaying empty grades...");
        thing.GetAllGrades();
        System.out.println("--------------------------------"); 

        System.out.println("Third Test Case: Calling GetFirstGrade from main when list is empty...");
        thing.getFirstGrade();
        System.out.println("--------------------------------"); 
  
        System.out.println("Entering the last test case: ");
        do{
            System.out.println("1 - Add Grade");
            System.out.println("2 - Delete All Grades");
            System.out.println("3 - Quit");
            System.out.print("What would you like to do?: ");
            option = input.nextInt();
            System.out.println();
            switch (option){
                case 1:
                    thing.addGrade(test);
                    break;
                case 2:
                    thing.deleteAllGrades();
                    break;
                case 3:
                    System.out.println("The program is now terminating...");
                    break;
                default:
                    System.out.println("Please input a valid menu option.");
                    break;
            }
            System.out.println("--------------------------------"); 
            
        }while(option != 3);
        
    }
    
}
