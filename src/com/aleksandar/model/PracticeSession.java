package com.aleksandar.model;

import java.time.LocalDate;
import java.util.Objects;

public class PracticeSession {

    private LocalDate date;
    private String pieceTitle;
    private int duration;
    private String notes;

    public PracticeSession(
            LocalDate date,
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
        return getDateString() + "|" + getPieceTitle() + "|" + getDuration() + "|" + getNotes();
    }

    public LocalDate getDate() {return date;}

    public String getDateString() {return date.toString();}

    public String getPieceTitle() {return pieceTitle;}

    public int getDuration() {return duration;}

    public String getNotes() {return notes;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PracticeSession that = (PracticeSession) o;
        return duration == that.duration && Objects.equals(date, that.date)
                && Objects.equals(pieceTitle, that.pieceTitle) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, pieceTitle, duration, notes);
    }
}