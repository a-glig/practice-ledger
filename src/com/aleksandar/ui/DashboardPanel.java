package com.aleksandar.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final MainFrame frame;

    private static final String[] WEEKDAYS = {"Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    DashboardPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        add(addWeekdays(), BorderLayout.CENTER);
    }

    private JPanel addWeekdays() {
        JPanel panel = new JPanel(new GridLayout(
                0, WEEKDAYS.length, 5,5)
        );
        for (String day : WEEKDAYS) {
            JPanel dayPanel = new JPanel(new BorderLayout());
            dayPanel.setPreferredSize(new Dimension(80,40));

            JLabel label = new JLabel(day);
            label.setHorizontalAlignment(JLabel.CENTER);
            dayPanel.add(label, BorderLayout.CENTER);

            panel.add(dayPanel);
        }
        return panel;
    }
}
