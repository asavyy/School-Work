
import java.io.Serializable;

/**
 *
 * @author Stephen J. Sarma-Weierman
 */
public class Piece implements Serializable {
    public static enum Rank {QUEEN, KING, BISHOP, KNIGHT, ROOK, PAWN};
    public static enum Color {BLACK, WHITE};
    
    private Rank type;
    private Color color;
    
    public Piece(String type, String color) {
        switch(type) {
            case "K":
                this.type = Rank.KING;
                break;
            case "Q":
                this.type = Rank.QUEEN;
                break;
            case "N":
                this.type = Rank.KNIGHT;
                break;
            case "R":
                this.type = Rank.ROOK;
                break;
            case "B":
                this.type = Rank.BISHOP;
                break;
            default:
                this.type = Rank.PAWN;
        }
        
        if (color.equals("W")) {
            this.color = Color.WHITE;
        } else {
            this.color = Color.BLACK;
        }
    }
    
    @Override
    public String toString() {
        String name = (color==Color.WHITE)?"w":"b";
        switch (type) {
            case BISHOP:
                name += "B";
                break;
            case KING:
                name += "K";
                break;
            case KNIGHT:
                name += "N";
                break;
            case PAWN:
                name += "P";
                break;
            case QUEEN:
                name += "Q";
                break;
            default:
                name += "R";
                break;
        }
        return name;
    }
}