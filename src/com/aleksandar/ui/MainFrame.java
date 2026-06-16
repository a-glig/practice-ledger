package com.aleksandar.ui;

import com.aleksandar.repository.PieceRepository;
import com.aleksandar.repository.SessionRepository;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

    private PieceRepository pieceRepo;
    private SessionRepository sessionRepo;

    private JButton sessions;
    private JButton pieces;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainFrame(PieceRepository pieceRepo, SessionRepository sessionRepo) {
        super("Practice Schedule App");
        this.pieceRepo = pieceRepo;
        this.sessionRepo = sessionRepo;

        initializeComponents();
        initializeLayout();
        initializeActionListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        sessions = new JButton("Sessions");
        pieces = new JButton("Pieces");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(createNavigationPanel(), BorderLayout.NORTH);
        add(createCardPanel(), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel();
        panel.add(sessions);
        panel.add(pieces);
        return panel;
    }

    private JPanel createCardPanel() {
        cardPanel.add(new SessionPanel(this, sessionRepo, pieceRepo), "sessions");
        cardPanel.add(new PiecePanel(this, pieceRepo), "pieces");
        return cardPanel;
    }

    private void initializeActionListeners() {
        sessions.addActionListener(e ->
                cardLayout.show(cardPanel, "sessions")
        );
        pieces.addActionListener(e ->
                cardLayout.show(cardPanel, "pieces")
        );
    }
}
