package com.aleksandar.repository;

import com.aleksandar.model.Piece;

import java.io.*;
import java.util.ArrayList;

public class PieceRepository {

    private final File pieceFile = new File("data/pieces.txt");

    public void save(Piece piece) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(pieceFile, true))){
            writer.write(piece.toFileString());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Piece piece) {
        ArrayList<Piece> pieces = findAll();
        pieces.remove(piece);
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(pieceFile))) {
            for (Piece currentPiece: pieces) {
                writer.write(currentPiece.toFileString());
                writer.newLine();
            }
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
                String[] parts = line.split("\\|",-1);
                pieces.add(new Piece(parts[0], parts[1], Integer.parseInt(parts[2]),parts[3]));
            }
            return pieces;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getTitles() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(pieceFile))) {
            ArrayList<String> titles = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                titles.add(parts[0]);
            }
            return titles;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
