package com.aleksandar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddPieceDialog extends JDialog {

    private JRadioButton technique;
    private JRadioButton repertoire;
    private ButtonGroup category;

    private JTextField titleField;
    private JTextField composerField;
    private JSpinner difficulty;

    private JButton save;

    private MainFrame frame;
    private PieceRepository pieceRepo;

    AddPieceDialog(MainFrame frame, PieceRepository pieceRepo) {
        super(frame, "Add Piece", true);
        this.frame = frame;
        this.pieceRepo = pieceRepo;
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
        technique = new JRadioButton("Technique");
        repertoire = new JRadioButton("Repertoire");
        titleField = new JTextField(20);
        composerField = new JTextField(20);
        difficulty = new JSpinner(new SpinnerNumberModel(5,1,10,1));
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

    private String getTitleText() {return titleField.getText();}

    private String getComposerText() {return composerField.getText();}

    private int getDifficulty() {return (Integer) difficulty.getValue();}

    private String getCategory() {return technique.isSelected() ? "Technique": "Repertoire";}

    private void initializeActionListener() {
        save.addActionListener(e -> {
            String title = getTitleText();
            String composer = getComposerText();
            int difficulty = getDifficulty();
            String category = getCategory();
            pieceRepo.save(new Piece(title, composer, difficulty, category));
            frame.refreshPieceTable();
            dispose();
        });
    }

}
