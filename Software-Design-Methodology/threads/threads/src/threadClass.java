import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author bdb52
 */
public class threadClass extends Thread implements Runnable{
    int numOfArgs;
    threads obj = new threads();
    String searchWordArray[];
    int i;
    
    @Override
    public void run(){
            long startTime = System.nanoTime();
            for(i = 1; i < searchWordArray.length; i++){
            try(BufferedReader fileRead = new BufferedReader(new FileReader(new File("bible.txt")))){
                //System.out.println("File opened."); This is a test to make sure that we open the file multiple times.
            String line = null;
            //String[] words = null;
            int noOfLines = 0;
            //We iterate through the file line by line, if one of our arguments are found within the line, increment
            //noOfLines, which is a counter to keep track of how many lines a word is found on
            while((line=fileRead.readLine())!=null){
                String lines[] = line.toString().split(" ");
                for( int j = 0; j < lines.length; j++){
                    //If the element on line x matches 
                    if(lines[j].equalsIgnoreCase(searchWordArray[i])){
                        noOfLines++;
                    }
                }
            }
            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000000;
            System.out.println(Thread.currentThread().getName() + " Found " + noOfLines + " lines that contain " + searchWordArray[i] + " in " + duration + " milliseconds.");
            fileRead.close();        
            //System.out.println("File closed."); Was a test to make sure we opened and closed the file multiple times
        } catch (FileNotFoundException e) {
            Logger.getLogger(threadClass.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(threadClass.class.getName()).log(Level.SEVERE, null, e);
        }
        }
    }
    
    // THREAD METHODS TO HELP:
    
    public void copyArray(int length, String array[]){
        numOfArgs = length;
        searchWordArray = array;
        
    }
}
