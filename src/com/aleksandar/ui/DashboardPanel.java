package com.aleksandar.ui;

import com.aleksandar.model.Piece;
import com.aleksandar.model.PracticeSession;
import com.aleksandar.repository.SessionRepository;
import com.aleksandar.services.StatisticsService;

import javax.swing.*;
import java.awt.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;

import java.util.Arrays;


public class DashboardPanel extends JPanel {

    private final MainFrame frame;
    private final SessionRepository sessionRepo;
    private StatisticsService statistics;

    private final int MAX_CALENDAR_ROWS = 6;
    private int lastFilledRow;
    private YearMonth shownMonth = YearMonth.from(LocalDate.now());
    private JLabel monthLabel;

    private JLabel[][] dayLabels = new JLabel[MAX_CALENDAR_ROWS][WEEKDAYS.length];
    private JPanel[][] dayPanels = new JPanel[MAX_CALENDAR_ROWS][WEEKDAYS.length];

    private static final String[] WEEKDAYS = {"MONDAY", "TUESDAY",
        "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    private JButton previousMonth;
    private JButton nextMonth;

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

        nextMonth = new JButton(">");
        previousMonth = new JButton("<");

        setLayout(new BorderLayout());
        add(initializeCalendar(),BorderLayout.NORTH);
        initializePieceList();
        initializeStatisticsPanel();
        initializeSplitPane();
        add(splitPane, BorderLayout.CENTER);
        loadPiecesIntoList();
        initializeActionListeners();
    }

    private JPanel initializeCalendar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(initializeCalendarSettings(), BorderLayout.NORTH);
        panel.add(addWeekdayHeaders(), BorderLayout.CENTER);
        panel.add(initializeCalendarGrid(), BorderLayout.SOUTH);

        loadDayLabels(shownMonth);
        hideEmptyRows(lastFilledRow);

        return panel;
    }

    private JPanel initializeCalendarSettings() {
        JPanel panel = new JPanel();
        monthLabel = new JLabel(formatMonth(shownMonth));

        panel.add(previousMonth);
        panel.add(monthLabel);
        panel.add(nextMonth);

        return panel;
    }

    private String formatMonth (YearMonth month) {
        return month.getMonth() + " " + month.getYear();
    }

    private JPanel addWeekdayHeaders() {
        JPanel headerPanel = new JPanel(new GridLayout(
                0, WEEKDAYS.length, 5,5)
        );
        for (String day : WEEKDAYS) {
            JPanel dayPanel = new JPanel(new BorderLayout());

            JLabel label = new JLabel(day);
            label.setHorizontalAlignment(JLabel.CENTER);
            dayPanel.add(label, BorderLayout.CENTER);

            headerPanel.add(dayPanel);
        }
        return headerPanel;
    }

    private JPanel initializeCalendarGrid() {
        JPanel calendarPanel = new JPanel(new GridLayout(
                0, WEEKDAYS.length, 5, 5
        ));
        for (int row = 0; row < MAX_CALENDAR_ROWS; row++) {
            for (int col = 0; col < WEEKDAYS.length; col++) {
                JPanel dayPanel = initializeDayCell(row, col);
                calendarPanel.add(dayPanel);
            }
        }
        return calendarPanel;
    }

    private JPanel initializeDayCell(int row, int col) {
        JPanel dayPanel = new JPanel(new BorderLayout());
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel label = new JLabel("");
        label.setHorizontalAlignment(JLabel.CENTER);

        dayLabels[row][col] = label;
        dayPanels[row][col] = dayPanel;

        dayPanel.add(label, BorderLayout.CENTER);
        return dayPanel;
    }

    private void loadDayLabels(YearMonth month) {
        DayOfWeek firstDay = month.atDay(1).getDayOfWeek();
        int currentRow = 0;
        int currentCol = Arrays.asList(WEEKDAYS).indexOf(firstDay.toString());

        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            dayLabels[currentRow][currentCol].setText(String.valueOf(day));
            if (currentCol == WEEKDAYS.length - 1) {
                currentRow++;
                currentCol = 0;
            } else {
                currentCol++;
            }
        }
        lastFilledRow = currentRow;
    }

    private void hideEmptyRows(int lastFilledRow) {
        for (int row = lastFilledRow; row < MAX_CALENDAR_ROWS; row++) {
            if (!dayLabels[row][0].getText().isEmpty())
                continue;
            for (int col = 0; col < WEEKDAYS.length; col++) {
                dayPanels[row][col].setBorder(null);
            }
        }
    }

    private void initializeActionListeners() {
        previousMonth.addActionListener(e -> {
            shownMonth = shownMonth.minus(Period.ofMonths(1));
            updateCalendar(shownMonth);
        });
        nextMonth.addActionListener(e -> {
            shownMonth = shownMonth.plus(Period.ofMonths(1));
            updateCalendar(shownMonth);
        });
    }

    private void updateCalendar(YearMonth month) {
        resetCalendarGrid();
        loadDayLabels(month);
        hideEmptyRows(lastFilledRow);
        monthLabel.setText(formatMonth(month));
    }

    private void resetCalendarGrid() {
        for (int row = 0; row < MAX_CALENDAR_ROWS; row++) {
            for (int col = 0; col < WEEKDAYS.length; col++) {
                dayLabels[row][col].setText("");
                dayPanels[row][col].setBorder(
                        BorderFactory.createLineBorder(Color.black)
                );
            }
        }
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

        JLabel title = new JLabel("Statistics");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        statisticsPanel.add(title);
        statisticsPanel.add(Box.createVerticalStrut(15));
        addStatistics(statisticsPanel, LocalDate.now());
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

}
