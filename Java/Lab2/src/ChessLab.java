/**
 * This program simulates a chess board. The sole purpose of this program
 * is to demonstrate the saving and loading of objects to 
 * @author sjw
 */
public class ChessLab {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board b = new Board();
        b.addPiece(new Piece("R", "B"), "a8");
        b.addPiece(new Piece("N", "B"), "b8");
        b.addPiece(new Piece("B", "B"), "c8");
        b.addPiece(new Piece("Q", "B"), "d8");
        b.addPiece(new Piece("K", "B"), "e8");
        b.addPiece(new Piece("B", "B"), "f8");
        b.addPiece(new Piece("N", "B"), "g8");
        b.addPiece(new Piece("R", "B"), "h8");
        for (char i = 'a'; i <= 'h'; i++) {
            b.addPiece(new Piece("P", "B"), i + "7");
            b.addPiece(new Piece("P", "W"), i + "2");
        }
        b.addPiece(new Piece("R", "W"), "a1");
        b.addPiece(new Piece("N", "W"), "b1");
        b.addPiece(new Piece("B", "W"), "c1");
        b.addPiece(new Piece("Q", "W"), "d1");
        b.addPiece(new Piece("K", "W"), "e1");
        b.addPiece(new Piece("B", "W"), "f1");
        b.addPiece(new Piece("N", "W"), "g1");
        b.addPiece(new Piece("R", "W"), "h1");
        
        System.out.println("Starting board state...");
        b.printBoard();
        System.out.println("Saving file...");
        b.save("savegame.dat");
        
        System.out.println("Erasing board in memory...");
        b = new Board();
        System.out.println("Loading board from file...");
        b.load("savegame.dat");
        
        System.out.println("Restored board...");
        b.printBoard();
    }
    
}