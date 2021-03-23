/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class PinchHitter extends Player{
    
        public PinchHitter(){
            throwingBehavior = new ThrowNoThrow();
            catchingBehavior = new CatchNoCatch();
            battingBehavior = new BattingPower();
        }    
        
        public void setName(String name){
            n = name;
        }

         public void Display() {
             System.out.print("The players name is " + n + "\n");
        }
}
