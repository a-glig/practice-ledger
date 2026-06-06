package com.aleksandar;

import javax.swing.*;
import java.awt.*;

public class AddPieceDialog extends JDialog {

    private JTextField titleField;
    private JTextField composerField;
    private JSpinner difficulty;
    private JButton save;

    AddPieceDialog(JFrame frame) {
        super(frame, "Add Piece", true);
        initializeComponents();
        initializeLayout();

        add(createFieldPanel("Title", titleField));
        add(createFieldPanel("Composer", composerField));
        add(createFieldPanel("Difficulty", difficulty));
        add(createButtonPanel());

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void initializeComponents() {
        titleField = new JTextField(20);
        composerField = new JTextField(20);
        difficulty = new JSpinner(new SpinnerNumberModel(5,1,10,1));
        save = new JButton("Save");
    }

    private void initializeLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private JPanel createFieldPanel(String fieldTitle, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(fieldTitle));
        panel.add(component);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(save);
        return panel;
    }

}
