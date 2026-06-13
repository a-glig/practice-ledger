package com.aleksandar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddSessionDialog extends JDialog {

    private PieceRepository pieceRepo;
    private SessionRepository sessionRepo;

    private ArrayList<String> pieceTitleList;
    JComboBox<String> pieceTitles;

    private JSpinner duration;
    private JTextField notesField;

    private JButton save;

    AddSessionDialog(
            MainFrame frame,
            PieceRepository pieceRepo,
            SessionRepository sessionRepo
    ) {
        super(frame, "Add Session", true);
        this.pieceRepo = pieceRepo;
        this.sessionRepo = sessionRepo;

        initializeComponents();
        initializeLayout();
        initializeActionListener();

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

    private void initializeActionListener() {
        save.addActionListener(e -> {
            String pieceTitle = getPieceTitle();
            int duration = getDuration();
            String notes = getNotesText();
            PracticeSession session = new PracticeSession(pieceTitle, duration, notes);
            sessionRepo.save(session);
            dispose();
        });
    }

    private String getPieceTitle() {return (String) pieceTitles.getSelectedItem();}

    private int getDuration() {return (Integer) duration.getValue();}

    private String getNotesText() {return notesField.getText();}
}
