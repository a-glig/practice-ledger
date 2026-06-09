package com.aleksandar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {

    private JButton sessions;
    private JButton pieces;
    private JButton addPiece;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private PieceRepository pieceRepo;
    private JTable pieceTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    MainFrame(PieceRepository pieceRepo) {
        super("Practice Schedule App");
        this.pieceRepo = pieceRepo;
        initializeComponents();
        createPieceTable();
        initializeLayout();
        initializeActionListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        sessions = new JButton("Sessions");
        pieces = new JButton("Pieces");
        addPiece = new JButton("Add Piece");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        model = new DefaultTableModel();
    }

    private void createPieceTable() {
        model.addColumn("Title");
        model.addColumn("Composer");
        model.addColumn("Difficulty");
        for (Piece piece: pieceRepo.findAll()) {
            model.addRow(new Object[] {piece.getTitle(), piece.getComposer(), piece.getDifficulty()});
        }
        pieceTable = new JTable(model);
        scrollPane = new JScrollPane(pieceTable);
        add(scrollPane);
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
        cardPanel.add(createSessionPanel(), "sessions");
        cardPanel.add(createPiecePanel(), "pieces");
        return cardPanel;
    }

    private JPanel createPiecePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(addPiece, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createSessionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("This Panel shows the Practice Sessions"));
        return panel;
    }

    private void initializeActionListeners() {
        sessions.addActionListener(e ->
                cardLayout.show(cardPanel, "sessions")
        );
        pieces.addActionListener(e ->
                cardLayout.show(cardPanel, "pieces")
        );
        addPiece.addActionListener(e ->
            new AddPieceDialog(this, pieceRepo)
        );
    }
}
