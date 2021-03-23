
import java.util.Observable;
import java.util.Observer;
import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class AverageDisplay implements Observer, display{
    private ArrayList<Integer> gradesList2;
    
    
    public AverageDisplay(Observable observable) {
		observable.addObserver(this);
	}
    
    public void update(Observable o, Object arg) {
        if (o instanceof FileManager){
            FileManager fileManager = (FileManager)o;
            gradesList2 = ((FileManager) o).GetAllGrades();
            display();
        }
    }
    
    public void display(){                
        if(!gradesList2.isEmpty()){
        int sum = 0;
        for (int i : gradesList2){
            sum += i;
        }       
        float average = (float) sum / gradesList2.size();
        // return average;
        System.out.printf("The average of the grades is: %.2f", average);
        System.out.println();
        } else { // if theres no grades in the file
        System.out.println("There are currently no grades in the file.");
        }
    }



}
