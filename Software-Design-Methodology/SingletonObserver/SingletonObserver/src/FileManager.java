import java.io.*;
import java.util.*;
/**
 *
 * @author brice
 */
public class FileManager extends Observable{
    private static FileManager instance;
    ArrayList<Integer> gradesList = new ArrayList<>();
    int grade;
    private File grades;
    
    private FileManager(){
        if (instance != null){
            throw new Error();
        }
        grades = new File("Grades.txt");
    }
    
    public static synchronized FileManager getInstance(){
        if (instance == null){
            instance = new FileManager();
        }
        
        return instance;
    }
    
    public void addGrade(int grade){
        try{
            BufferedWriter gradeAdd = new BufferedWriter(new FileWriter(grades, true));
            Scanner gradeInput = new Scanner(System.in);
            System.out.println("Please enter the grade:");
            grade = gradeInput.nextInt();
            gradeAdd.write(String.valueOf(grade));
            gradeAdd.newLine();
            gradesList.add(grade);
            gradeAdd.close();
        }catch (Exception o){
            System.out.println("Could not add the grade");
        }       
        gradesChanged();
    }
    
    public Integer getFirstGrade(){
        if (!gradesList.isEmpty()){
            int first = gradesList.get(grade);
            System.out.println("The first grade in the file is: " + first);
            return first;
        }else
        System.out.println("The grades list is currently empty.");

        return null;
    }
    
    ArrayList<Integer> GetAllGrades() {
        if(gradesList.isEmpty()){
            System.out.println("The grades list is currently empty.");
        }
        return gradesList;
    }
    
    public void deleteAllGrades(){
        try{
            if(gradesList.get(0) != null){
                PrintWriter deleteGrade = new PrintWriter(grades);
                deleteGrade.close();
                gradesList.clear();
            }else{
                System.out.println("The file is already empty.");
            }
        } catch (Exception o){
            System.out.println("The file is already empty.");
        }
        gradesChanged();
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    public void gradesChanged() {
	setChanged();
	notifyObservers();
    }
}
