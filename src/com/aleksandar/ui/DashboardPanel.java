package com.aleksandar.ui;

import com.aleksandar.model.Piece;
import com.aleksandar.model.PracticeSession;
import com.aleksandar.repository.SessionRepository;
import com.aleksandar.services.StatisticsService;

import javax.swing.*;
import java.awt.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardPanel extends JPanel {

    private final MainFrame frame;
    private final SessionRepository sessionRepo;
    private StatisticsService statistics;

    private JSplitPane splitPane;

    private DefaultListModel<String> listModel;
    private JList<String> pieceList;
    private JScrollPane scrollPaneList;

    private JPanel statisticsPanel;

    DashboardPanel(MainFrame frame,
                   SessionRepository sessionRepo,
                   StatisticsService statistics
                   ) {
        this.frame = frame;
        this.sessionRepo = sessionRepo;
        this.statistics = statistics;

        initializeComponents();
        initializeLayout();
        loadPiecesIntoList();
    }

    private void initializeComponents() {
        initializePieceList();
        initializeStatisticsPanel();
        initializeSplitPane();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(new Calendar(),BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    private void initializeSplitPane() {
        splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, scrollPaneList, statisticsPanel
        );
        splitPane.setResizeWeight(0.5);
        splitPane.setEnabled(false);
    }

    private void initializePieceList() {
        listModel = new DefaultListModel<>();
        pieceList = new JList<>(listModel);
        scrollPaneList = new JScrollPane(pieceList);
    }

    private void loadPiecesIntoList() {
        for (PracticeSession session : sessionRepo.findAll()) {
            if (session.getDate().equals(LocalDate.now()))
                listModel.addElement(session.getPieceTitle());
        }
    }

    private void initializeStatisticsPanel() {
        statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.Y_AXIS));

        statisticsPanel.add(practiceSummary(LocalDate.now()));
        statisticsPanel.add(neglectedRepertoire());

    }

    private JPanel practiceSummary(LocalDate date) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Practice Summary");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));
        addStatistics(panel, date);

        return panel;
    }

    private void addStatistics(JPanel panel, LocalDate day) {
        panel.add(new JLabel(
                "Practice time (day): " + statistics.getPracticeDurationForDate(day))
        );
        statisticsPanel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel(
                "Practice time (week): " + statistics.getPracticeDurationForWeek(day))
        );
        statisticsPanel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel(
                "Days practiced this month: " + statistics.daysPracticedInMonth(day))
        );
    }

    private JPanel neglectedRepertoire() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Repertoire to revisit");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        ArrayList<Piece> neglectedRepertoire = statistics.getNeglectedRepertoire(5);
        for (Piece piece: neglectedRepertoire) {
            panel.add(new JLabel(piece.getTitle()));
            statisticsPanel.add(Box.createVerticalStrut(10));
        }

        return panel;
    }

}
