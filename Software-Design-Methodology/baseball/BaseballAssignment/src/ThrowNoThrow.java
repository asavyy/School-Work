/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brice
 */
public class ThrowNoThrow implements ThrowingBehavior{
    public void throwing(){
        System.out.print("The player did not throw the ball.\n");
    }
}
