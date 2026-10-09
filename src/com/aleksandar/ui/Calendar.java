package com.aleksandar.ui;

import javax.swing.*;
import java.awt.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;

import java.util.Arrays;

public class Calendar extends JPanel {

    private final DashboardPanel dashboard;

    private YearMonth shownMonth = YearMonth.from(LocalDate.now());
    private LocalDate selectedDate = LocalDate.now();

    private final int MAX_CALENDAR_ROWS = 6;
    private int lastFilledRow;
    private JLabel monthLabel;

    private static final String[] WEEKDAYS = {"MONDAY", "TUESDAY",
            "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    private JButton[][] dayButtons = new JButton[MAX_CALENDAR_ROWS][WEEKDAYS.length];

    private JButton previousMonth = new JButton("<");
    private JButton nextMonth = new JButton(">");

    Calendar(DashboardPanel dashboard) {
        this.dashboard = dashboard;
        setLayout(new BorderLayout());

        add(initializeCalendarSettings(), BorderLayout.NORTH);
        add(addWeekdayHeaders(), BorderLayout.CENTER);
        add(initializeCalendarGrid(), BorderLayout.SOUTH);

        loadDayLabels(shownMonth);
        hideEmptyRows(lastFilledRow);
        initializeActionListeners();
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
                JButton dayButton = initializeDayCell(row, col);
                calendarPanel.add(dayButton);
            }
        }
        return calendarPanel;
    }

    private JButton initializeDayCell(int row, int col) {
        JButton dayButton = new JButton("");
        dayButtons[row][col] = dayButton;
        return dayButton;
    }

    private void loadDayLabels(YearMonth month) {
        DayOfWeek firstDay = month.atDay(1).getDayOfWeek();
        int currentRow = 0;
        int currentCol = Arrays.asList(WEEKDAYS).indexOf(firstDay.toString());

        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            dayButtons[currentRow][currentCol].setText(String.valueOf(day));
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
            if (!dayButtons[row][0].getText().isEmpty())
                continue;
            for (int col = 0; col < WEEKDAYS.length; col++) {
                dayButtons[row][col].setBorder(null);
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
        initializeActionListenersDays();
    }

    private void initializeActionListenersDays() {
        for (JButton[] row : dayButtons) {
            for (JButton button : row) {
                button.addActionListener(e -> {
                    JButton clickedButton = (JButton) e.getSource();
                    if (!clickedButton.getText().isEmpty()) {
                        int day = Integer.parseInt(clickedButton.getText());
                        selectedDate = shownMonth.atDay(day);
                        dashboard.updateChartPanel(selectedDate);
                    }
                });
            }
        }
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
                dayButtons[row][col].setText("");
                dayButtons[row][col].setBorder(
                        UIManager.getBorder("Button.border")
                );
            }
        }
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }
}
