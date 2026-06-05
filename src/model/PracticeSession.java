package model;

import repository.PieceRepository;
import ui.Console;

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

    public void show() {
        System.out.println();
        System.out.println(this.date);
        System.out.println(this.piece.getTitle());
        System.out.println(this.duration);
        System.out.println(this.notes);
        System.out.println();
    }

    public static PracticeSession createSessionFromInput(PieceRepository pieceRepo) {
        Piece piece = pieceRepo.selectPiece();
        String notes = "";
        if (piece == null)
            return null;
        short duration = (short) Console.readNumber("Duration (in Minutes): ",1);
        if (Console.getYesOrNo("Provide notes? (y/n)"))
            notes = Console.readText("Notes: ");
        return new PracticeSession(piece, duration, notes);
    }

}
