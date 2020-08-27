
/**
 * This class represents the board. A chess board may contain up to 32 chess
 * pieces, 16 of each color. Each piece has a unique position on the board, between
 * a1 and h8. The letters a - h represent columns, while the numbers 1 through 8
 * represent rows. The lower left corner of the board is a1.
 * 
 * Implement save and load methods as described below.
 * 
 * @author Stephen J. Sarma-Weierman
 * @author Brice Baerga
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Board {
    public static final int BOARD_SIZE = 8;
    private Piece [][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
    private int numberOfPieces = 0;
    
    public void addPiece(Piece p, String ps) {
        //magic
        int col = ((int)ps.charAt(0)) - 97;
        int row = ((int)ps.charAt(1)) - 49;
        if (board[row][col] == null)
            numberOfPieces++;
        board[row][col] = p;
    }
    
    public Piece removePiece(String ps) {
        int col = ((int)ps.charAt(0)) - 97;
        int row = ((int)ps.charAt(1)) - 49;
        Piece p = board[row][col];
        if (p != null)
            numberOfPieces--;
        return p;
    }
    
    public void printBoard() {
        for (int i = BOARD_SIZE-1; i >= 0; i--) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == null) {
                    System.out.print("-- ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
    //Write the contents of the board to the specified file.
    //Hint: create a new File, new ObjectOutputStream,
    //and write first the number of pieces on the board, then each piece as 
    //object and position as two byte values.
    public boolean save(String filename) {
        var file = new File(filename);
 try {
             // creating a ObjectOutputStream from file name
             var fout = new ObjectOutputStream(new FileOutputStream((file)));
             // writing number of pieces to file
            fout.writeByte(numberOfPieces);
             // looping through each row and column
             for (int i = 0; i < BOARD_SIZE; i++) {
                 for (int j = 0; j < BOARD_SIZE; j++) {
                     // checking if current piece is not null
                     if (board[i][j] != null) {
                         // writing Piece object to file
                         fout.writeObject(board[i][j]);
                         // converting j and i into position in bytes
                         byte col = (byte) (97 + j);
                         byte row = (byte) (49 + i);
                         // writing position as two bytes to output file
                         fout.write(new byte[] { col, row });
                     }
                 }
             }
             // closing file, saving changes
             fout.close();
             return true; // success
        } catch (IOException e) {
        }
        return false; // failed
    }
    
    //Read the contents of the board from the specified file.
    //Hint: create a new File, new ObjectInputStream,
    //and read first the number of pieces on the board, then each piece as 
    //object and position as two byte values.
    public boolean load(String filename) {       
        var file = new File(filename);
 // assuming the board is empty/cleared before calling this method
        try {
            // opening file using ObjectInputStream
             var fin = new ObjectInputStream(new FileInputStream((file)));
             // reading and setting numberOfPieces
             numberOfPieces = fin.readByte();
             // looping for numberOfPieces number of times
             for (int i = 0; i < numberOfPieces; i++) {
                 // reading a Piece object
                 Piece p = (Piece) fin.readObject();
                 // reading next two bytes as col and row
                 byte col = fin.readByte();
                 byte row = fin.readByte();
                 // adding piece to appropriate position using addPiece method
                 addPiece(p, new String(new byte[] { col, row }));
             }
             //closing file
             fin.close();
             return true; //success
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return false; //failed
    }
}