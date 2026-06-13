package com.aleksandar;

import java.time.LocalDate;

public class PracticeSession {

    private LocalDate date;

    private String pieceTitle;
    private int duration;
    private String notes;

    public PracticeSession(String pieceTitle, int duration, String notes) {
        this.date = LocalDate.now();
        this.pieceTitle = pieceTitle;
        this.duration = duration;
        this.notes = notes;
    }

    public String toFileString() {
        return getDate() + "," + getPieceTitle() + "," + getDuration() + "," + getNotes();
    }

    public LocalDate getDate() {return date;}

    public String getPieceTitle() {return pieceTitle;}

    public int getDuration() {return duration;}

    public String getNotes() {return notes;}
}