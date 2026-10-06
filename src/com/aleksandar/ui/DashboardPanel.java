package com.aleksandar.ui;

import javax.swing.*;
import java.awt.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

import java.util.Arrays;


public class DashboardPanel extends JPanel {

    private final MainFrame frame;
    private final int MAX_CALENDAR_ROWS = 6;
    private final YearMonth thisMonth = YearMonth.from(LocalDate.now());

    private static final String[] WEEKDAYS = {"MONDAY", "TUESDAY",
        "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    private JLabel[][] dayLabels = new JLabel[MAX_CALENDAR_ROWS][WEEKDAYS.length];
    private JPanel[][] dayPanels = new JPanel[MAX_CALENDAR_ROWS][WEEKDAYS.length];

    DashboardPanel(MainFrame frame) {
        this.frame = frame;
        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(initializeCalendar(),BorderLayout.CENTER);
    }

    private JPanel initializeCalendar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(addWeekdayHeaders(), BorderLayout.NORTH);
        panel.add(initializeCalendarGrid(), BorderLayout.CENTER);
        loadLabelsThisMonth();
        return panel;
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

    private void loadLabelsThisMonth() {
        DayOfWeek firstDay = thisMonth.atDay(1).getDayOfWeek();
        int currentRow = 0;
        int currentCol = Arrays.asList(WEEKDAYS).indexOf(firstDay.toString());

        for (int day = 1; day <= thisMonth.lengthOfMonth(); day++) {
            dayLabels[currentRow][currentCol].setText(String.valueOf(day));
            if (currentCol == WEEKDAYS.length - 1) {
                currentRow++;
                currentCol = 0;
            } else {
                currentCol++;
            }
        }
        hideEmptyRows(currentRow);
    }

    private void hideEmptyRows(int lastRow) {
        for (int row = lastRow; row < MAX_CALENDAR_ROWS; row++) {
            if (!dayLabels[row][0].getText().isEmpty())
                continue;
            for (int col = 0; col < WEEKDAYS.length; col++) {
                dayPanels[row][col].setBorder(null);
            }
        }
    }
}
