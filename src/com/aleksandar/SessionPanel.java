package com.aleksandar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
    private static final String[] SESSION_COLUMNS = {
            "Piece",
            "Duration (in min)",
            "Comments"
    };

    private JSplitPane splitPane;

    private JButton addSession;

    SessionPanel(MainFrame frame, SessionRepository sessionRepo, PieceRepository pieceRepo) {
        this.frame = frame;
        this.pieceRepo = pieceRepo;
        this.sessionRepo = sessionRepo;

        initializeComponents();
        initializeLayout();
        initializeActionListener();
    }

    private void initializeComponents() {
        listModel = new DefaultListModel<>();
        tableModel = new DefaultTableModel(SESSION_COLUMNS,0);

        initializeDateList();
        initializeSessionTable();
        initializeSplitPane();

        addSession = new JButton("Add Session");
    }

    private void initializeDateList() {
        loadDatesIntoList();
        dateList = new JList<>(listModel);
        scrollPaneList = new JScrollPane(dateList);
        add(scrollPaneList);
    }

    private void loadDatesIntoList() {
        for (PracticeSession session : sessionRepo.findAll()) {
            listModel.addElement(session.getDate().toString());
        }
    }

    private void initializeSessionTable() {
        loadSessionsIntoTable();
        sessionTable = new JTable(tableModel);
        scrollPaneTable = new JScrollPane(sessionTable);
        add(scrollPaneTable);
    }

    private void loadSessionsIntoTable() {
        for (PracticeSession session : sessionRepo.findAll()) {
            tableModel.addRow(new Object[]{
                    session.getPieceTitle(),
                    session.getDuration(),
                    session.getNotes()
            });
        }
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
        return panel;
    }

    private void initializeActionListener() {
        addSession.addActionListener(e ->
                new AddSessionDialog(frame, pieceRepo, sessionRepo));
    }
}
