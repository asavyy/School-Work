/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class Catcher extends Player{
    
        public Catcher(){
            throwingBehavior = new ThrowBackToPitcher();
            catchingBehavior = new CatchFromPitcher();
            battingBehavior = new BattingContact();
        }    
        
        public void setName(String name){
            n = name;
        }

         public void Display() {
             System.out.print("The players name is " + n + "\n");
             
        }
    
}
    

