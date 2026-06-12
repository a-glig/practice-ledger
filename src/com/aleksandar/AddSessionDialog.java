package com.aleksandar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddSessionDialog extends JDialog {

    private MainFrame frame;
    private PieceRepository pieceRepo;

    private ArrayList<String> pieceTitleList;
    JComboBox<String> pieceTitles;

    private JSpinner duration;
    private JTextField notesField;

    private JButton save;

    AddSessionDialog(MainFrame frame, PieceRepository pieceRepo) {
        super(frame, "Add Session", true);
        this.frame = frame;
        this.pieceRepo = pieceRepo;

        initializeComponents();
        initializeLayout();
        add(createFieldPanel("Piece", pieceTitles));
        add(createFieldPanel("Duration (in min)", duration));
        add(createFieldPanel("Notes", notesField));
        add(createButtonPanel());

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void initializeComponents() {
        pieceTitleList = pieceRepo.getTitles();
        pieceTitles = new JComboBox<>(pieceTitleList.toArray(String[]::new));
        duration = new JSpinner(new SpinnerNumberModel(10,5,120,5));
        notesField = new JTextField(20);
        save = new JButton("Save");
    }

    private void initializeLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
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

    private String getPieceTitle() {return (String) pieceTitles.getSelectedItem();}

    private short getDuration() {return (Short) duration.getValue();}

    private String getNotesText() {return notesField.getText();}
}
