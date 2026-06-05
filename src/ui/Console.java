package ui;

import java.util.Scanner;

public class Console {
    private static Scanner scanner = new Scanner(System.in);

    public static String readText(String prompt) {
        String input;
        System.out.print(prompt);
        input = scanner.nextLine();
        return input;
    }

    public static double readNumber(String prompt, double min, double max) {
        double value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextDouble();
            if (value >= min && value <= max)
                break;
            printNumberErrorMessage(min, max);
        }
        scanner.nextLine();
        return value;
    }

    public static double readNumber(String prompt, double min) {
        return readNumber(prompt, min, Double.MAX_VALUE);
    }

    private static void printNumberErrorMessage(double min, double max) {
        if (min == max)
            System.out.println("Invalid input. Only " + min + " is available");
        else
            System.out.println("Enter a value between " + min + " and " + max + ".");
    }

    public static boolean getYesOrNo(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y"))
                return true;
            if (input.equals("n"))
                return false;
            System.out.println("Please provide y or n as an answer.");
        }
    }
}
