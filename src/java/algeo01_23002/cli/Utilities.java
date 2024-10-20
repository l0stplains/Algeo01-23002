package algeo01_23002.cli;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;

public class Utilities {

    public static String getCenteredText(String text, int width){
        int rem = width - text.length();
        StringBuilder res = new StringBuilder();
        if(rem > 0){
            res.append(" ".repeat(rem / 2 + (rem % 2)));
        }
        res.append(text);
        if(rem > 0){
            res.append(" ".repeat(rem / 2));
        }
        return res.toString();
    }

    // Ensures valid input
    public static int getChoice(int min, int max) {
        int choice;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.print(YELLOW + "!!!  Invalid choice! Please choose again (" + min + "-" + max + "): " + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.print(YELLOW + "!!!  Not a valid number! Try again: " + RESET);
            } catch (Exception e) {
                System.out.print(RED + "!!!  ERROR! Try again: " + RESET);
            }
        }
        return choice;
    }
}
