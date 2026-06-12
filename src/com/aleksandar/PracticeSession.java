package com.aleksandar;

import java.time.LocalDate;

public class PracticeSession {

    private LocalDate date;

    private Piece piece;
    private short duration;
    private String notes;

    public PracticeSession(Piece piece, short duration, String notes) {
        this.date = LocalDate.now();
        this.piece = piece;
        this.duration = duration;
        this.notes = notes;
    }

    public LocalDate getDate() {return date;}

    public Piece getPiece() {return piece;}

    public short getDuration() {return duration;}

    public String getNotes() {return notes;}
}