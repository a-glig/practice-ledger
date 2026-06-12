package com.aleksandar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PiecePanel extends JPanel {

    private PieceRepository pieceRepo;
    private MainFrame frame;

    private JButton addPiece;
    private JButton deletePiece;

    private JTable pieceTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<Piece> displayedPieces;

    private static final String[] PIECE_COLUMNS = {
            "Title",
            "Composer",
            "Difficulty",
            "Category"
    };

    PiecePanel(MainFrame frame, PieceRepository pieceRepo) {
        this.frame = frame;
        this.pieceRepo = pieceRepo;

        initializeComponents();
        createPieceTable();
        initializeLayout();
        initializeActionListeners();
    }

    private void initializeComponents() {
        addPiece = new JButton("Add Piece");
        deletePiece = new JButton("Delete Piece");

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
                    piece.getDifficulty(),
                    piece.getCategory()
            });
        }
    }

    public void addPieceToTable(Piece piece) {
        pieceRepo.save(piece);
        refreshPieceTable();
    }

    public void refreshPieceTable() {
        model.setRowCount(0);
        loadPiecesIntoTable();
        displayedPieces = pieceRepo.findAll();
        pieceTable.setModel(model);
    }

    private void initializeLayout () {
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(createPieceButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createPieceButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(addPiece);
        panel.add(deletePiece);
        return panel;
    }

    private void initializeActionListeners() {
        addPiece.addActionListener(e ->
                new AddPieceDialog(frame,this)
        );
        deletePiece.addActionListener(e -> {
            int selectedRow = pieceTable.getSelectedRow();
            Piece piece = displayedPieces.get(selectedRow);
            pieceRepo.delete(piece);
            refreshPieceTable();
        });
    }

}
