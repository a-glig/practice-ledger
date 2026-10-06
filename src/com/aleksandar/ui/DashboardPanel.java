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
    private YearMonth displayedMonth = YearMonth.from(LocalDate.now());

    private JLabel[][] dayLabels = new JLabel[MAX_CALENDAR_ROWS][WEEKDAYS.length];
    private JPanel[][] dayPanels = new JPanel[MAX_CALENDAR_ROWS][WEEKDAYS.length];

    private static final String[] WEEKDAYS = {"MONDAY", "TUESDAY",
        "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    private static final String[] MONTHS = {"JANUARY", "FEBRUARY",
        "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER",
        "OCTOBER", "NOVEMBER", "DECEMBER"
    };

    private JButton previousMonth;
    private JButton nextMonth;

    DashboardPanel(MainFrame frame) {
        this.frame = frame;

        nextMonth = new JButton(">");
        previousMonth = new JButton("<");

        setLayout(new BorderLayout());
        add(initializeCalendar(),BorderLayout.NORTH);
    }

    private JPanel initializeCalendar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(initializeCalendarSettings(), BorderLayout.NORTH);
        panel.add(addWeekdayHeaders(), BorderLayout.CENTER);
        panel.add(initializeCalendarGrid(), BorderLayout.SOUTH);
        loadLabelsThisMonth();
        
        return panel;
    }

    private JPanel initializeCalendarSettings() {
        JPanel panel = new JPanel();
        JLabel monthLabel = new JLabel(formatMonth(displayedMonth));

        panel.add(previousMonth);
        panel.add(monthLabel);
        panel.add(nextMonth);

        return panel;
    }

    private String formatMonth (YearMonth month) {
        int monthNumber = month.getMonthValue();
        return MONTHS[monthNumber-1] + " " + month.getYear();
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
        DayOfWeek firstDay = displayedMonth.atDay(1).getDayOfWeek();
        int currentRow = 0;
        int currentCol = Arrays.asList(WEEKDAYS).indexOf(firstDay.toString());

        for (int day = 1; day <= displayedMonth.lengthOfMonth(); day++) {
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
