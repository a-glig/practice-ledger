package com.aleksandar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private JButton sessions;
    private JButton pieces;
    private JButton addPiece;
    private JButton deletePiece;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private PieceRepository pieceRepo;
    private JTable pieceTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<Piece> displayedPieces;

    private static final String[] PIECE_COLUMNS = {
            "Title",
            "Composer",
            "Difficulty"
    };

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
        deletePiece = new JButton("Delete Piece");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        model = new DefaultTableModel(PIECE_COLUMNS,0);
    }

    private void createPieceTable() {
        loadPiecesIntoTable();
        displayedPieces = pieceRepo.findAll();
        pieceTable = new JTable(model);
        scrollPane = new JScrollPane(pieceTable);
        add(scrollPane);
    }

    private void loadPiecesIntoTable() {
        for (Piece piece: pieceRepo.findAll()) {
            model.addRow(new Object[]{
                    piece.getTitle(),
                    piece.getComposer(),
                    piece.getDifficulty()
            });
        }
    }

    public void refreshPieceTable() {
        model.setRowCount(0);
        loadPiecesIntoTable();
        displayedPieces = pieceRepo.findAll();
        pieceTable.setModel(model);
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
        panel.add(createPieceButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createPieceButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(addPiece);
        panel.add(deletePiece);
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
        deletePiece.addActionListener(e -> {
                int selectedRow = pieceTable.getSelectedRow();
                Piece piece = displayedPieces.get(selectedRow);
                pieceRepo.delete(piece);
                refreshPieceTable();
        });
    }
}
