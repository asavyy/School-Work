import java.io.*;
/**
 *
 * @author brice
 */
public class baseball {

    /**
     * @param args the command line arguments
     */
public static void main(String[] args)
 {
    Pitcher kent = new Pitcher();
    kent.setName("Kent T");
    kent.Display();
    kent.performThrow();
    kent.performCatch();
    kent.performBat();
    Batter pedro = new Batter();
    pedro.setName("Pedro A");
    pedro.Display();
    pedro.performThrow();
    pedro.performCatch();
    pedro.performBat();
    Fielder max = new Fielder();
    max.setName("Max M");
    max.Display();
    max.performThrow();
    max.performCatch();
    max.performBat();
    Catcher chris = new Catcher();
    chris.setName("Chris S");
    chris.Display();
    chris.performThrow();
    chris.performCatch();
    chris.performBat();
    Console.writeLine("Kent the pitcher will not bat any more because we have a pinch hitter.");
    kent.setBattingBehavior(new BattingNoBat());
    kent.Display();
    kent.performBat();

    PinchHitter bobcat = new PinchHitter();
    bobcat.setName("Bobcat");
    bobcat.Display();
    bobcat.performThrow(); 
    bobcat.performCatch();
    bobcat.performBat();
    }
}
