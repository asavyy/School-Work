
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class GradesDisplay implements Observer, display{
    private ArrayList<Integer> gradesList2;
    
    
    public GradesDisplay(Observable observable) {
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
        System.out.println("The grades currently in the file are: ");
        for(int i = 0; i < gradesList2.size(); i++){
            System.out.print(gradesList2.get(i) + " ");
        }
        System.out.println();
        } else {// if theres no grades in the file
        System.out.println("There are currently no grades in the file.");
        }
    }

}
