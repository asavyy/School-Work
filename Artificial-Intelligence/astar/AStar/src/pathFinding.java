import java.util.ArrayList;
/**
 *
 * @author brice
 */
public class pathFinding {
    public void aStar(Node startNode, Node goalNode, char[][] mapArray) {
        
        //Here we create two arraylists, an open and a closed one
        ArrayList<Node> closetList = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();
        boolean shortestOpenPath, shortestClosedPath;
        Node backToStart; // this node will be used to trace back through the maze once we have completed it.

        //We start just like in the pseudo code, we take our starting node and add it to the open list.
        startNode.setF(0);
        openList.add(startNode);
       
        //while loop to loop through openList list
        //This while loop loops through open list and searches for the lowest cost (F) within in it. That gets updated to the currentNode
        while (!openList.isEmpty()) {
            Node currentNode;
            currentNode = openList.get(0);
            int index = 0;
            for (int i=1; i < openList.size(); i++) {
                if (currentNode.getF() > openList.get(i).getF()) {
                    currentNode = openList.get(i);
                    index = i;
                }
            }
            openList.remove(index);

//                            *** We set f, g, and H in every instance of a positional node ***            
                                //CREATING OUR UP POSITIONAL NODE
//------------------------------------------------------------------------------------------------------------------
            Node nodeUp = new Node();
             nodeUp.setX(currentNode.getX() - 1);
            nodeUp.setY(currentNode.getY());
            nodeUp.setParent(currentNode);
           
            if (nodeUp.getX() == goalNode.getX() && nodeUp.getY() == goalNode.getY()) {
                goalNode.setParent(nodeUp.getParent());
                break;
            }
 
            nodeUp.setF(nodeUp.getG()+nodeUp.getH());
            nodeUp.setG(Math.abs(startNode.getX()-nodeUp.getX())+Math.abs(startNode.getY()-nodeUp.getY()));
            nodeUp.setH(Math.abs(nodeUp.getX()-goalNode.getX())+Math.abs(nodeUp.getY()-goalNode.getY()));
           
            shortestOpenPath = true;
            shortestClosedPath = true;
            //Here we check to see whether or not a spot is already in our open list.
            //If the spot is already within our open list, we see if the cost is less than or not. 
            //If it's smaller, we skip it. 
            for (int i=0; i < openList.size(); i++) {
                if (nodeUp.getX() == openList.get(i).getX() && nodeUp.getY() == openList.get(i).getY()) {
                    if (nodeUp.getF() < openList.get(i).getF()) {
                        shortestOpenPath = true;
                    } else {
                        shortestOpenPath = false;
                    }
                }
            }
                        for (int i=0; i < closetList.size(); i++) {
                if (nodeUp.getX() == closetList.get(i).getX() && nodeUp.getY() == closetList.get(i).getY()) {
                    if (nodeUp.getF() < closetList.get(i).getF()) {
                        shortestClosedPath = true;
                    } else {
                        shortestClosedPath = false;
                    }
                }
            }
           
            if (closetList.size() == 0) {
                shortestClosedPath = true;
            }
           
            if (shortestOpenPath && shortestClosedPath) {
                if (nodeUp.getX() < 0 || nodeUp.getX() >= mapArray.length || nodeUp.getY() < 0 || nodeUp.getY() >= mapArray[0].length) {
                } else {
                    if (mapArray[nodeUp.getX()][nodeUp.getY()] == 'B' || mapArray[nodeUp.getX()][nodeUp.getY()] == 'W') {  
                        closetList.add(nodeUp);
                    } else {
                        openList.add(nodeUp);
                    }
                }
            }
            if (openList.size() == 0) {
                shortestOpenPath = true;
            }
                    //CREATING OUR DOWN POSITIONAL NODE
//------------------------------------------------------------------------------------------------------------------            
            Node nodeDown = new Node();
            nodeDown.setX(currentNode.getX() + 1);
            nodeDown.setY(currentNode.getY());
            nodeDown.setParent(currentNode);
           
            if (nodeDown.getX() == goalNode.getX() && nodeDown.getY() == goalNode.getY()) {
                goalNode.setParent(nodeDown.getParent());
                break;
            }
            
            nodeDown.setF(nodeDown.getG()+nodeDown.getH());
            nodeDown.setG(Math.abs(startNode.getX()-nodeDown.getX())+Math.abs(startNode.getY()-nodeDown.getY()));
            nodeDown.setH(Math.abs(nodeDown.getX()-goalNode.getX())+Math.abs(nodeDown.getY()-goalNode.getY()));

            shortestOpenPath = true;
            shortestClosedPath = true;
            //Here we check to see whether or not a spot is already in our open list.
            //If the spot is already within our open list, we see if the cost is less than or not. 
            //If it's smaller, we skip it. 
            for (int i=0; i < openList.size(); i++) {
                if (nodeDown.getX() == openList.get(i).getX() && nodeDown.getY() == openList.get(i).getY()) {
                    if (nodeDown.getF() < openList.get(i).getF()) {
                        shortestOpenPath = true;
                    } else {
                        shortestOpenPath = false;
                    }
                }
            }

            if (openList.size() == 0) {
                shortestOpenPath = true;
            }
           
            for (int i=0; i < closetList.size(); i++) {
                if (nodeDown.getX() == closetList.get(i).getX() && nodeDown.getY() == closetList.get(i).getY()) {
                    if (nodeDown.getF() < closetList.get(i).getF()) {
                        shortestClosedPath = true;
                    } else {
                        shortestClosedPath = true;
                    }
                }
            }
           
            if (closetList.size() == 0) {
                shortestClosedPath = true;
            }
           
            if (shortestOpenPath && shortestClosedPath) {
                if (nodeDown.getX() < 0 || nodeDown.getX() >= mapArray.length || nodeDown.getY() < 0 || nodeDown.getY() >= mapArray[0].length) {
                } else {
                    if (mapArray[nodeDown.getX()][nodeDown.getY()] == 'B' || mapArray[nodeDown.getX()][nodeDown.getY()] == 'W') {  
                        closetList.add(nodeDown);
                    } else {
                        openList.add(nodeDown);
                    }
                }
            }
            
                        //CREATING OUR LEFT POSITIONAL NODE
//------------------------------------------------------------------------------------------------------------------                       
            Node left = new Node();
            left.setY(currentNode.getY() - 1);
            left.setX(currentNode.getX());
            left.setParent(currentNode);
           
            if (left.getX() == goalNode.getX() && left.getY() == goalNode.getY()) {
                goalNode.setParent(left.getParent());
                break;
            }
            
            left.setF(left.getG()+left.getH());
            left.setG(Math.abs(startNode.getX()-left.getX())+Math.abs(startNode.getY()-left.getY()));
            left.setH(Math.abs(left.getX()-goalNode.getX())+Math.abs(left.getY()-goalNode.getY()));
            
           
            shortestOpenPath = true;
            shortestClosedPath = true;
            //Here we check to see whether or not a spot is already in our open list.
            //If the spot is already within our open list, we see if the cost is less than or not. 
            //If it's smaller, we skip it. 
            for (int i=0; i < openList.size(); i++) {
                if (left.getX() == openList.get(i).getX() && left.getY() == openList.get(i).getY()) {
                    if (left.getF() < openList.get(i).getF()) {
                        shortestOpenPath = true;
                    } else {
                        shortestOpenPath = false;
                    }
                }
            }

            if (openList.size() == 0) {
                shortestOpenPath = true;
            }
           
            for (int i=0; i < closetList.size(); i++) {
                if (left.getX() == closetList.get(i).getX() && left.getY() == closetList.get(i).getY()) {
                    if (left.getF() < closetList.get(i).getF()) {
                        shortestClosedPath = true;
                    } else {
                        shortestClosedPath = false;
                    }
                }
            }
           
            if (closetList.size() == 0) {
                shortestClosedPath = true;
            }
           
            if (shortestOpenPath && shortestClosedPath) {
                if (left.getX() < 0 || left.getX() >= mapArray.length || left.getY() < 0 || left.getY() >= mapArray[0].length) {
                } else {
                    if (mapArray[left.getX()][left.getY()] == 'B' || mapArray[left.getX()][left.getY()] == 'W') {
                        closetList.add(left);
                    } else {
                        openList.add(left);
                    }
                }
            }
       
                        //CREATING OUR RIGHT POSITIONAL NODE
//------------------------------------------------------------------------------------------------------------------                       
            Node right = new Node();
           
            right.setY(currentNode.getY() + 1);
            right.setX(currentNode.getX());
            right.setParent(currentNode);
           
            if (right.getX() == goalNode.getX() && right.getY() == goalNode.getY()) {
                goalNode.setParent(right.getParent());
                break;
               
            }
            
            right.setF(right.getG()+right.getH());            
            right.setG(Math.abs(startNode.getX()-right.getX())+Math.abs(startNode.getY()-right.getY()));
            right.setH(Math.abs(right.getX()-goalNode.getX())+Math.abs(right.getY()-goalNode.getY()));

            shortestOpenPath = true;
            shortestClosedPath = true;
            //Here we check to see whether or not a spot is already in our open list.
            //If the spot is already within our open list, we see if the cost is less than or not. 
            //If it's smaller, we skip it. 
            for (int i=0; i < openList.size(); i++) {
                if (right.getX() == openList.get(i).getX() && right.getY() == openList.get(i).getY()) {
                    if (right.getF() < openList.get(i).getF()) {
                        shortestOpenPath = true;
                    } else {
                        shortestOpenPath = false;
                    }
                }
            }
 
            if (openList.size() == 0) {
                shortestOpenPath = true;
            }
                       
            for (int i=0; i < closetList.size(); i++) {
                if (right.getX() == closetList.get(i).getX() && right.getY() == closetList.get(i).getY()) {
                    if (right.getF() < closetList.get(i).getF()) {
                        shortestClosedPath = true;
                    } else {
                        shortestClosedPath = false;
                    }
                }
            }
           
            if (closetList.size() == 0) {
                shortestClosedPath = true;
            }
           
            if (shortestOpenPath && shortestClosedPath) {
                if (right.getX() < 0 || right.getX() >= mapArray.length || right.getY() < 0 || right.getY() >= mapArray[0].length) {
                } else {
                    if (mapArray[right.getX()][right.getY()] == 'B' || mapArray[right.getX()][right.getY()] == 'W') {
                        closetList.add(right);
                    } else {
                        openList.add(right);
                    }
                }
            }
                        closetList.add(currentNode);
        }
           
            
                    //HERE, WE LOOP BACK TO THE START
//------------------------------------------------------------------------------------------------------------------
        backToStart = goalNode.getParent();
       
        while (backToStart.getX() != startNode.getX() || backToStart.getY() != startNode.getY()) {
            mapArray[backToStart.getX()][backToStart.getY()] = 'S';
            //mapArray[backToStart.getX()-1][backToStart.getY()-1] = ' ';
            backToStart = backToStart.getParent();
        }
    }
    
    public void printMap(char[][] printMap) {
        //print out the mapArray display
            for (int x = 0; x < printMap.length; x++) {
                for (int y = 0; y < printMap[0].length; y++) {
                if ((int)printMap[x][y] == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(printMap[x][y]);
                }
            }
            System.out.println();
        }
       
    }
}
