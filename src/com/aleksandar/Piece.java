package com.aleksandar;

public class Piece {

    private String title;
    private String composer;
    private int difficulty;

    public Piece(String title, String composer, int difficulty) {
        this.title = title;
        this.composer = composer;
        this.difficulty = difficulty;
    }

    public void show() {
        System.out.println();
        System.out.println(this.title);
        System.out.println(this.composer);
        System.out.println(this.difficulty);
        System.out.println();
    }

    public static Piece createPieceFromInput() {
        String title = Console.readText("Title: ");
        String composer = Console.readText("Composer: ");
        int difficulty = (int) Console.readNumber("Difficulty: ", 0,10);
        return new Piece(title, composer, difficulty);
    }

    public String getTitle() {
        return title;
    }

    public String getComposer() {return composer;}

    public int getDifficulty() {return difficulty;}
}
