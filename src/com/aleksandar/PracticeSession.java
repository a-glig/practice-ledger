package com.aleksandar;

import java.time.LocalDate;

public class PracticeSession {

    private String date;
    private String pieceTitle;
    private int duration;
    private String notes;

    public PracticeSession(
            String date,
            String pieceTitle,
            int duration,
            String notes
    ) {
        this.date = date;
        this.pieceTitle = pieceTitle;
        this.duration = duration;
        this.notes = notes;
    }

    public String toFileString() {
        return getDate() + "," + getPieceTitle() + "," + getDuration() + "," + getNotes();
    }

    public String getDate() {return date;}

    public String getPieceTitle() {return pieceTitle;}

    public int getDuration() {return duration;}

    public String getNotes() {return notes;}
}