/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public abstract class Player {
    BattingBehavior battingBehavior;
    CatchingBehavior catchingBehavior;
    ThrowingBehavior throwingBehavior;
    String n;
    
    public Player(){
        
    }
    
    public void setBattingBehavior(BattingBehavior b){
        battingBehavior = b;
    }
    
    public void setCatchingBehavior(CatchingBehavior c){
        catchingBehavior = c;
    }
    
    public void setThrowingBehavior(ThrowingBehavior t){
        throwingBehavior = t;
    }
    
    public void setName(String name){ // I think we add another interface for this!
        n = name;
    }
    
    abstract void Display();
    
    public void performBat(){
        battingBehavior.bat();
    }
    
    public void performCatch(){
        catchingBehavior.catching();
    }
    
    public void performThrow(){
        throwingBehavior.throwing();
    }
    
}
