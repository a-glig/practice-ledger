package com.aleksandar;

import com.aleksandar.repository.PieceRepository;
import com.aleksandar.repository.SessionRepository;
import com.aleksandar.ui.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        PieceRepository pieceRepo = new PieceRepository();
        SessionRepository sessionRepo = new SessionRepository();
        SwingUtilities.invokeLater(() ->
                new MainFrame(pieceRepo, sessionRepo)
        );
    }
}
