package com.aleksandar;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PieceRepository pieceRepo = new PieceRepository(10);
        new MainFrame(pieceRepo);
    }
}
