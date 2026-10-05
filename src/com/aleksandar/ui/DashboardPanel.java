package com.aleksandar.ui;

import javax.swing.*;
import java.awt.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

import java.util.Arrays;


public class DashboardPanel extends JPanel {

    private final MainFrame frame;
    private final int CALENDAR_ROWS = 6;
    private final YearMonth thisMonth = YearMonth.from(LocalDate.now());

    private static final String[] WEEKDAYS = {"MONDAY", "TUESDAY",
        "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    private JLabel[][] dayLabels = new JLabel[CALENDAR_ROWS][WEEKDAYS.length];

    DashboardPanel(MainFrame frame) {
        this.frame = frame;
        initializeLayout();
        loadLabelsThisMonth();
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
        return panel;
    }

    private JPanel addWeekdayHeaders() {
        JPanel panel = new JPanel(new GridLayout(
                0, WEEKDAYS.length, 5,5)
        );
        for (String day : WEEKDAYS) {
            JPanel dayPanel = new JPanel(new BorderLayout());

            JLabel label = new JLabel(day);
            label.setHorizontalAlignment(JLabel.CENTER);
            dayPanel.add(label, BorderLayout.CENTER);

            panel.add(dayPanel);
        }
        return panel;
    }

    private JPanel initializeCalendarGrid() {
        JPanel panel = new JPanel(new GridLayout(
                0, WEEKDAYS.length, 5, 5
        ));
        for (int row = 0; row < CALENDAR_ROWS; row++) {
            for (int col = 0; col < WEEKDAYS.length; col++) {
                JPanel dayPanel = initializeDayCell(row, col);
                panel.add(dayPanel);
            }
        }
        return panel;
    }

    private JPanel initializeDayCell(int row, int col) {
        JPanel dayPanel = new JPanel(new BorderLayout());
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel label = new JLabel(" ");
        dayLabels[row][col] = label;
        label.setHorizontalAlignment(JLabel.CENTER);
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
    }

}
