/**
 *
 * @author brice
 */
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Thread;

public class multiThread extends Thread
{
    int numOfArgs;
    threads obj = new threads();
    String searchWordArray[];
    int i;
    
   public void run()
   {
    long startTime = System.nanoTime();
      String thread = Thread.currentThread().getName();
            int threadNo = thread.charAt(thread.length()-1);
            threadNo = (threadNo-48);
            try(BufferedReader fileRead = new BufferedReader(new FileReader(new File("bible.txt")))){
                String line = null;
                int noOfLines = 0;
                    while((line = fileRead.readLine()) != null){
                        //Use split in order to search every individual word in some line.
                        String lines[] = line.toString().split(" "); 
                            for(int j = 0; j < lines.length; j++){
                                if(lines[j].equalsIgnoreCase(searchWordArray[threadNo])){ 
                                    noOfLines++;
                                }
                            }                          
                    }
                    
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime)/1000000;
                System.out.println(Thread.currentThread().getName() + " Found " + noOfLines + " lines that contain " + searchWordArray[threadNo] + " in " + duration + " milliseconds.");
                fileRead.close();
                }   
                catch (FileNotFoundException ex) {
                    Logger.getLogger(multiThread.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (IOException ex) {
                    Logger.getLogger(multiThread.class.getName()).log(Level.SEVERE, null, ex);
                }

        }
       
      
   
    public void copyArray(int length, String array[]){
        numOfArgs = length;
        searchWordArray = array;
        
   }
}