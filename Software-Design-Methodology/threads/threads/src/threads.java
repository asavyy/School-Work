/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class threads {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String arguments[] = new String[args.length];
        int threadCount = 0;
        
        for(int i = 0; i < args.length; i++){
            arguments[i] = args[i];
        }
        //threadClass multiThread = new threadClass();
        if(args[0].equalsIgnoreCase("m")){
            for (int i=1; i < args.length; i++)
            {
                //This creates a new thread for each argument specified. It starts at index 1
                // because index 0 would contain our mode for single or multi threading.
                multiThread temp = new multiThread();
                temp.setName("Thread " + i);
                temp.copyArray(args.length, arguments);
                temp.start();
                }
             
        } else if (args[0].equalsIgnoreCase("s")){
            //We use one single thread in order to search for x amount of words.
            threadClass thread1 = new threadClass();
            thread1.setName("Thread " + threadCount + " ");
            thread1.copyArray(args.length, arguments);
            thread1.start();
        }
        
    }
    
}
