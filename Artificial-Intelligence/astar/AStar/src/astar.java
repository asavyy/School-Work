import java.util.*;
import java.io.*;
/**
 *
 * @author brice
 */
public class astar {

    public static void main(String[] args) throws FileNotFoundException {
        String mapFile;
        String barberFile;
        pathFinding pathFind = new pathFinding();
        int timeStep = 0;
        Node startNode = new Node();
        Node startOrigNode = new Node();
        Node goalNode = new Node();
        
        Scanner input = new Scanner(System.in);
        
//                          INPUT FOR MAP FILE AND FILE PROCESSING
//-------------------------------------------------------------------------------------------------------------
        System.out.print("Please input the map file: ");
        mapFile = input.nextLine();
            Scanner mapRead = new Scanner(new File(mapFile));
            char mapObj = mapRead.next().charAt(0);
            int x = mapRead.nextInt();
            int y = mapRead.nextInt();
            int xArrayDim = x;
            int yArrayDim = y;
            char[][] mapArray = new char[x][y];
            
            while(mapRead.hasNext()){
                mapObj = mapRead.next().charAt(0);
                
                switch(mapObj){
                    case 'E':
                        System.out.println("The map file has been loaded successfully.");
                        break;
                    default:
                        x = mapRead.nextInt();
                        y = mapRead.nextInt();
                        mapArray[x][y] = mapObj;
                        if (mapObj == 'S') {
                            if (timeStep == 0) {
                                startOrigNode.setX(x);
                                startOrigNode.setY(y);
                                startNode.setX(x);                                                              
                                startNode.setY(y);
                                startNode.setParent(null);
                            }
                            } else if (mapObj == 'G') {
                                goalNode.setX(x);
                                goalNode.setY(y);
                                goalNode.setParent(null);
                }
            }
        }
        mapRead.close(); // closing the file because we are now done with it.
        
        
//                          INPUT FOR BARBER FILE AND FILE PROCESSING
//-------------------------------------------------------------------------------------------------------------
        System.out.print("Please input the barber file: ");
        barberFile = input.nextLine();            
            
            boolean pathFinding = true;
            
            while(pathFinding){
            Scanner barberRead = new Scanner(new File(barberFile));
            while (barberRead.hasNext()){
                int tempTimeStep; // temp timeStep for iterating our barber files 
                tempTimeStep = barberRead.nextInt(); //Since the barber file is all integers, we can just read in ints.
                switch(tempTimeStep){
                case -1:
                    //System.out.println("The barber file has been loaded successfully."); was going to keep this but it iterated. will find some other way
                    break;
                default:
                    if (tempTimeStep != timeStep){
                        tempTimeStep = barberRead.nextInt();
                        tempTimeStep = barberRead.nextInt();
                    } else {
                        int barberXCord = barberRead.nextInt();
                        int barberYCord = barberRead.nextInt();
                        mapArray[barberXCord][barberYCord] = 'B';
                    }
                    break;
                }
            }
            barberRead.close();
            

              

        //printMap(mapArray); testing map print
        
        mapArray[startNode.getX()][startNode.getY()] = 'S';
        
        if ((startNode.getX() == goalNode.getX() && startNode.getY() == goalNode.getY())) {
                System.out.println("The maze has been completed.");
                pathFinding = false; // we set our while loop to false now because we are done
                break;
        }    
                
        pathFind.aStar(startNode, goalNode, mapArray);
        
        if (timeStep == 0){
            mapArray[startOrigNode.getX()][startOrigNode.getY()] = 'S';
        } else {
            mapArray[startOrigNode.getX()][startOrigNode.getY()] = 'S';
        }
        
        System.out.println();
        System.out.println("TimeStep: " + timeStep);
        
        if (mapArray[startNode.getX()][startNode.getY()] == 'B' || mapArray[startNode.getX()][startNode.getY()] == 'W') {
            System.out.println("There is no path to complete this maze.");
        } else {
                mapArray[startNode.getX()][startNode.getY()] = '*';
                pathFind.printMap(mapArray);
        }
        timeStep++;
   
                                            //PATH FINDING 
//----------------------------------------------------------------------------------------------------------------------------------------
            if ((startNode.getX() + 1) < mapArray.length){
                if (mapArray[startNode.getX() + 1][startNode.getY()] ==  'S' || mapArray[startNode.getX() + 1][startNode.getY()] == 'G') {
                    startNode.setX(startNode.getX() +1);
                    continue;
                }
            }
            if ((startNode.getY() +1) < mapArray[0].length) {
                if (mapArray[startNode.getX()][startNode.getY() +1] == 'S' || mapArray[startNode.getX()][startNode.getY() +1] == 'G') {
                    startNode.setY(startNode.getY()+1);
                    continue;
                }
            }
            if ((startNode.getX() -1) > 0) {
                if (mapArray[startNode.getX()-1][startNode.getY()] == 'S' || mapArray[startNode.getX()-1][startNode.getY()] == 'G') {
                    startNode.setX(startNode.getX() -1);
                    continue;
                }
            }
            if ((startNode.getY() -1) > 0) {
                if (mapArray[startNode.getX()][startNode.getY() -1] == 'S' || mapArray[startNode.getX()][startNode.getY() -1] == 'G') {
                    startNode.setY(startNode.getY()-1);
                    
                }
            }
        }
    }
}