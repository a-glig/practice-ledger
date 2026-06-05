import model.Piece;
import model.PracticeSession;
import repository.PieceRepository;
import repository.PracticeSessionRepository;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean isRunning = true;
        int choice;
        var pieceRepo = new PieceRepository(10);
        var sessionRepo = new PracticeSessionRepository(10);

        while(isRunning) {
            // DISPLAY MENU
            System.out.println("*****************");
            System.out.println("PRACTICE SCHEDULE");
            System.out.println("*****************");
            System.out.println("1. Add Piece");
            System.out.println("2. Show all Pieces");
            System.out.println("3. Add Practice Session");
            System.out.println("4. Show all Practice Sessions");
            System.out.println("5. Exit");
            System.out.println("*****************");

            // GET AND PROCESS USERS CHOICE
            System.out.print("Enter your choice (1-3): ");
            choice = scanner.nextInt();

            switch(choice) {
                case 1 -> {
                    var newPiece = Piece.createPieceFromInput();
                    pieceRepo.add(newPiece);
                }
                case 2 -> pieceRepo.show();
                case 3 -> {
                    var newSession = PracticeSession.createSessionFromInput(pieceRepo);
                    sessionRepo.add(newSession);
                }
                case 4 -> sessionRepo.show();
                case 5 -> isRunning = false;
                default -> System.out.println("INVALID CHOICE");
            }
        }
        scanner.close();
    }
}
