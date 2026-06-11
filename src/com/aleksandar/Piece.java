package com.aleksandar;

import java.util.Objects;

public class Piece {

    private String title;
    private String composer;
    private int difficulty;
    private String category;

    public Piece(String title, String composer, int difficulty, String category) {
        this.title = title;
        this.composer = composer;
        this.difficulty = difficulty;
        this.category = category;
    }

    public String toFileString() {
        return getTitle() + "," + getComposer() + "," + getDifficulty() + "," + getCategory();
    }

    public String getCategory() {return category;}

    public String getTitle() {
        return title;
    }

    public String getComposer() {return composer;}

    public int getDifficulty() {return difficulty;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return difficulty == piece.difficulty && Objects.equals(category, piece.category)
                && Objects.equals(title, piece.title) && Objects.equals(composer, piece.composer);
    }

}
