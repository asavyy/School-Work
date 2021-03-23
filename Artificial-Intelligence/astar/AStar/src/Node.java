/**
 *
 * @author brice
 */
public class Node {
    Node parent;
    int g, f, h, x, y;

    //setters for our Node
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    // getters for our Node
    public Node getParent() {
        return parent;
    }

    public int getG() {
        return g;
    }

    public int getF() {
        return f;
    }

    public int getH() {
        return h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
}
