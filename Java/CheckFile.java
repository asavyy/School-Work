/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;
/**
 *
 * @author bdb52
 */
public class CheckFile {
   
   
   
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("usage: java CheckFile filename");
            System.exit(1);
           
            File file = new File (args[0]);
            if (!file.exists()){
                System.err.println("File not found");
                System.exit(2);
            }
            try{
                DataInputStream fin = new DataInputStream(new FileInputStream(file));
                int b;
                boolean checkWin = false;
                int counter = 0;
                do {
                    b = fin.read();
                    if (b == 0){
                        System.out.println("The file is a binary file.");
                        checkWin = true;
                        
                    } else if (fin.read() == '\r'){
                        if (fin.read() == '\n'){
                            System.out.println("The file is a windows text file.");
                            checkWin = true;
                        }
                }            
                counter++;
                
                } while (file.length() > counter);
                
                if(checkWin == false){
                    System.out.println("The file is a unix text file.");
                }

               
            } catch (IOException e) {
                System.out.println("oof.");
               
            }
                   
        }
    }
}
