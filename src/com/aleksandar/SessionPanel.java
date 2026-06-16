package com.aleksandar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SessionPanel extends JPanel {

    private MainFrame frame;
    private PieceRepository pieceRepo;
    private SessionRepository sessionRepo;

    private DefaultListModel<String> listModel;
    private JList<String> dateList;
    private JScrollPane scrollPaneList;

    private DefaultTableModel tableModel;
    private JTable sessionTable;
    private JScrollPane scrollPaneTable;
    private ArrayList<PracticeSession> displayedSessions;

    private static final String[] SESSION_COLUMNS = {
            "Piece",
            "Duration (in min)",
            "Comments"
    };
    private JSplitPane splitPane;

    private JButton addSession;
    private JButton deleteSession;

    SessionPanel(MainFrame frame, SessionRepository sessionRepo, PieceRepository pieceRepo) {
        this.frame = frame;
        this.pieceRepo = pieceRepo;
        this.sessionRepo = sessionRepo;

        initializeComponents();
        initializeLayout();
        initializeActionListeners();
        initializeSelectionListener();
        loadData();
    }

    private void initializeComponents() {
        listModel = new DefaultListModel<>();
        initializeTableModel();
        initializeDateList();
        initializeSessionTable();
        initializeSplitPane();

        addSession = new JButton("Add Session");
        deleteSession = new JButton("Delete Session");
    }

    private void initializeTableModel() {
        tableModel = new DefaultTableModel(SESSION_COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void initializeDateList() {
        dateList = new JList<>(listModel);
        scrollPaneList = new JScrollPane(dateList);
        add(scrollPaneList);
    }

    private void initializeSessionTable() {
        sessionTable = new JTable(tableModel);
        scrollPaneTable = new JScrollPane(sessionTable);
        add(scrollPaneTable);
    }

    private void initializeSplitPane() {
        splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, scrollPaneList, scrollPaneTable
        );
        splitPane.setDividerSize(10);
        splitPane.setEnabled(false);
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
        add(initializeButtonPanel(),BorderLayout.SOUTH);
    }

    private JPanel initializeButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(addSession);
        panel.add(deleteSession);
        return panel;
    }

    private void initializeActionListeners() {
        addSession.addActionListener(e -> {
            if (!pieceRepoEmpty())
                new AddSessionDialog(frame, pieceRepo, this);
        });
        deleteSession.addActionListener(e-> {
            int selectedRow = sessionTable.getSelectedRow();
            if (isSelected(selectedRow)) {
                PracticeSession session = displayedSessions.get(selectedRow);
                sessionRepo.delete(session);
                refreshAfterDeletion(session.getDate());
            }
        });
    }

    private boolean pieceRepoEmpty() {
        if (pieceRepo.findAll().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please add at least one piece before adding a session."
            );
            return true;
        }
        return false;
    }

    private void initializeSelectionListener() {
        dateList.addListSelectionListener(e -> {
            String selectedDate = dateList.getSelectedValue();
            if (selectedDate == null) {return;}
            refreshSessionTable(selectedDate);
        });
    }

    private boolean isSelected(int row) {
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a session.");
            return false;
        }
        return true;
    }

    private void loadData() {
        loadDatesIntoList();
        if (!listModel.isEmpty()) {
            dateList.setSelectedIndex(0);
            displayedSessions = sessionRepo.findByDate(dateList.getSelectedValue());
        }
    }

    private void loadDatesIntoList() {
        for (PracticeSession session : sessionRepo.findAll()) {
            if (!listModel.contains(session.getDate()))
                listModel.addElement(session.getDate());
        }
    }

    private void loadSessionsForDate(String date) {
        for (PracticeSession session : sessionRepo.findByDate(date)) {
            tableModel.addRow(new Object[]{
                    session.getPieceTitle(), session.getDuration(), session.getNotes()
            });
        }
    }

    public void addSessionToTable(PracticeSession session) {
        sessionRepo.save(session);
        refreshSessionTable(session.getDate());
        if (!listModel.contains(session.getDate()))
            listModel.addElement(session.getDate());
    }

    private void refreshSessionTable(String selectedDate) {
        tableModel.setRowCount(0);
        loadSessionsForDate(selectedDate);
        displayedSessions = sessionRepo.findByDate(selectedDate);
    }

    private void refreshAfterDeletion(String selectedDate) {
        if (sessionRepo.containsDate(selectedDate)) {
            refreshSessionTable(selectedDate);
        } else {
            listModel.removeElement(selectedDate);
            dateList.setSelectedIndex(0);
        }
        if (listModel.isEmpty()) {
            tableModel.setRowCount(0);
        }
    }
}
