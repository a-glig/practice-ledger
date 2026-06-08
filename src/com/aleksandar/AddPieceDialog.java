package com.aleksandar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddPieceDialog extends JDialog {

    private JTextField titleField;
    private JTextField composerField;
    private JSpinner difficulty;
    private JButton save;

    private PieceRepository pieceRepo;

    AddPieceDialog(JFrame frame, PieceRepository pieceRepo) {
        super(frame, "Add Piece", true);
        this.pieceRepo = pieceRepo;
        initializeComponents();
        initializeLayout();
        initializeActionListener();

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

    private String getTitleText() {return titleField.getText();}

    private String getComposerText() {return composerField.getText();}

    private int getDifficulty() {return (Integer) difficulty.getValue();}

    // TESTING FUNCTIONALITY OF METHODS REGARDING PIECE REPOSITORY
    private void initializeActionListener() {
        save.addActionListener(e -> {
            String title = getTitleText();
            String composer = getComposerText();
            int difficulty = getDifficulty();
            pieceRepo.writeToFile(new Piece(title, composer, difficulty));
            // TO BE REMOVED LATER
            pieceRepo.add(new Piece(title, composer, difficulty));
        });
    }

}
