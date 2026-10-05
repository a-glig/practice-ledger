package com.aleksandar.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final MainFrame frame;

    private final int CALENDAR_ROWS = 6;

    private static final String[] WEEKDAYS = {"Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

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
                JPanel dayPanel = createDayCell();
                panel.add(dayPanel);
            }
        }
        return panel;
    }

    private JPanel createDayCell() {
        JPanel dayPanel = new JPanel(new BorderLayout());
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel label = new JLabel(" ");
        label.setHorizontalAlignment(JLabel.CENTER);
        dayPanel.add(label, BorderLayout.CENTER);

        return dayPanel;
    }


}
