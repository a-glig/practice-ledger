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
    private DefaultTableModel tableModel;
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
        initializeLayout();
        initializeActionListeners();
        loadPiecesIntoTable();
    }

    private void initializeComponents() {
        initializeTableModel();
        initializePieceTable();

        addPiece = new JButton("Add Piece");
        deletePiece = new JButton("Delete Piece");
    }

    private void initializeTableModel() {
        tableModel = new DefaultTableModel(PIECE_COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void initializePieceTable() {
        displayedPieces = pieceRepo.findAll();
        pieceTable = new JTable(tableModel);
        scrollPane = new JScrollPane(pieceTable);
        add(scrollPane);
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(initializeButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel initializeButtonPanel() {
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
            if (isSelected(selectedRow)) {
                Piece piece = displayedPieces.get(selectedRow);
                pieceRepo.delete(piece);
                refreshPieceTable();
            }
        });
    }

    private boolean isSelected(int row) {
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a piece.");
            return false;
        }
        return true;
    }

    private void loadPiecesIntoTable() {
        for (Piece piece: pieceRepo.findAll()) {
            tableModel.addRow(new Object[]{piece.getTitle(), piece.getComposer(),
                    piece.getDifficulty(), piece.getCategory()
            });
        }
    }

    public void addPieceToTable(Piece piece) {
        pieceRepo.save(piece);
        refreshPieceTable();
    }

    public void refreshPieceTable() {
        tableModel.setRowCount(0);
        loadPiecesIntoTable();
        displayedPieces = pieceRepo.findAll();
    }

}
