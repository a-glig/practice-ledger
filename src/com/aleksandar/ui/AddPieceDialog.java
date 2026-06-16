package com.aleksandar.ui;

import com.aleksandar.model.Piece;

import javax.swing.*;
import java.awt.*;

public class AddPieceDialog extends JDialog {

    private PiecePanel piecePanel;

    private JRadioButton technique;
    private JRadioButton repertoire;
    private ButtonGroup category;

    private JTextField titleField;
    private JTextField composerField;
    private JSpinner difficulty;

    private JButton save;

    AddPieceDialog(MainFrame frame, PiecePanel piecePanel) {
        super(frame, "Add Piece", true);
        this.piecePanel = piecePanel;

        initializeComponents();
        initializeLayout();
        initializeActionListener();

        add(createFieldPanel("Title", titleField));
        add(createFieldPanel("Composer", composerField));
        add(createFieldPanel("Difficulty", difficulty));
        add(createCategoryPanel());
        addButtonGroup();
        add(createButtonPanel());

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void initializeComponents() {
        titleField = new JTextField(20);
        composerField = new JTextField(20);
        difficulty = new JSpinner(new SpinnerNumberModel(5,1,10,1));
        technique = new JRadioButton("Technique");
        repertoire = new JRadioButton("Repertoire");
        save = new JButton("Save");
    }

    private void initializeLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel("Category ");

        panel.add(label);
        panel.add(technique);
        panel.add(repertoire);
        return panel;
    }

    private void addButtonGroup() {
        category = new ButtonGroup();
        category.add(technique);
        category.add(repertoire);
    }

    private JPanel createFieldPanel(String fieldTitle, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(fieldTitle);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(component);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(save);
        return panel;
    }

    private void initializeActionListener() {
        save.addActionListener(e -> {
            if (isValidInput()) {
                String title = getTitleText();
                String composer = getComposerText();
                int difficulty = getDifficulty();
                String category = getCategory();

                piecePanel.addPieceToTable(new Piece(title, composer, difficulty, category));
                dispose();
            }
        });
    }

    private boolean isValidInput() {
        if (getTitleText().contains("|") || getComposerText().contains("|")) {
            JOptionPane.showMessageDialog(
                    this, "Please don't use the (|) symbol in your input."
            );
            return false;
        }
        return true;
    }

    private String getTitleText() {return titleField.getText();}

    private String getComposerText() {return composerField.getText();}

    private int getDifficulty() {return (Integer) difficulty.getValue();}

    private String getCategory() {return technique.isSelected() ? "Technique": "Repertoire";}
}
