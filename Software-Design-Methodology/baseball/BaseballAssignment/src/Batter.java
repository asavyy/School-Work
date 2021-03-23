/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class Batter extends Player {

        public Batter(){
            throwingBehavior = new ThrowNoThrow();
            catchingBehavior = new CatchBareHands();
            battingBehavior = new BattingContact();
        }    
        
        public void setName(String name){
            n = name;
        }

         public void Display() {
             System.out.print("The players name is " + n + "\n");
             
        }
    
}
