package com.aleksandar;

import java.io.*;
import java.util.ArrayList;

public class PieceRepository {

    private final File pieceFile = new File("allPieces.txt");

    public void save(Piece piece) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(pieceFile, true))){
            writer.write(piece.toFileString());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Piece> findAll() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(pieceFile))) {
            ArrayList<Piece> pieces = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                pieces.add(new Piece(parts[0], parts[1], Integer.parseInt(parts[2])));
            }
            return pieces;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
