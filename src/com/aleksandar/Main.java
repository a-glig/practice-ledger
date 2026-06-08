package com.aleksandar;

public class Main {

    public static void main(String[] args) {
        PieceRepository pieceRepo = new PieceRepository(10);
        new MainFrame(pieceRepo);
    }
}
