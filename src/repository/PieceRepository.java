package repository;

import ui.Console;
import model.Piece;

public class PieceRepository {

    private Piece[] pieces;
    private int count;

    public PieceRepository(int capacity) {
        pieces = new Piece[capacity];
        count = 0;
    }

    public void add(Piece piece) {
        if (count >= pieces.length) {
            System.out.println("The repository is full");
            return;
        }
        pieces[count] = piece;
        count++;
    }

    public void show() {
        if (count == 0) {
            System.out.println("No pieces stored in the repository.");
            return;
        }
        for (int i = 0; i < count; i++) {
            pieces[i].show();
        }
    }

    public Piece selectPiece() {
        if (count == 0) {
            System.out.println("No pieces stored in the repository.");
            System.out.println("Please add a piece first.");
            return null;
        }
        for (int i = 0; i < count; i++) {
            System.out.println((i+1) + " : " + pieces[i].getTitle());
        }
        byte choice = (byte) Console.readNumber("Piece: ", 1, count);
        return pieces[choice-1];
    }

}
