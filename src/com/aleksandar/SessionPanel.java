package com.aleksandar;

import javax.swing.*;
import java.awt.*;

public class SessionPanel extends JPanel {

    private MainFrame frame;
    private PieceRepository pieceRepo;
    private SessionRepository sessionRepo;

    private DefaultListModel<String> model;
    private JList<String> dateList;
    private JScrollPane scrollPaneDates;

    private JButton addSession;

    SessionPanel(MainFrame frame, SessionRepository sessionRepo, PieceRepository pieceRepo) {
        this.frame = frame;
        this.pieceRepo = pieceRepo;
        this.sessionRepo = sessionRepo;

        initializeComponents();
        initializeLayout();
        initializeActionListener();

        createDateList();
    }

    private void initializeComponents() {
        model = new DefaultListModel<>();
        addSession = new JButton("Add Session");
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(createButtonPanel(),BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(addSession);
        return panel;
    }

    private void createDateList() {
        loadDatesIntoList();
        dateList = new JList<>(model);
        scrollPaneDates = new JScrollPane(dateList);
        add(scrollPaneDates);
    }

    private void loadDatesIntoList() {
        for (PracticeSession session : sessionRepo.findAll()) {
            model.addElement(session.getDate().toString());
        }
    }

    private void initializeActionListener() {
        addSession.addActionListener(e ->
                new AddSessionDialog(frame, pieceRepo, sessionRepo));
    }
}
