package com.aleksandar;

import java.io.*;
import java.util.ArrayList;

public class PieceRepository {

    private Piece[] pieces;
    private int count;

    private File pieceFile = new File("allPieces.txt");

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

    // HERE WE IMPLEMENT NEW METHODS TO USE FILES INSTEAD OF ARRAYS
    public void writeToFile(Piece piece) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pieceFile, true));
            writer.write(piece.getTitle() + ",");
            writer.write(piece.getComposer() + ",");
            writer.write(String.valueOf(piece.getDifficulty()));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Piece> getData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("allPieces.txt"));
            ArrayList<Piece> pieces = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                pieces.add(new Piece(parts[0], parts[1], Integer.parseInt(parts[2])));
            }
            reader.close();
            return pieces;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
