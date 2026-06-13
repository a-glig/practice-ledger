package com.aleksandar;

public class Main {

    public static void main(String[] args) {
        PieceRepository pieceRepo = new PieceRepository();
        SessionRepository sessionRepo = new SessionRepository();
        new MainFrame(pieceRepo, sessionRepo);
    }
}
