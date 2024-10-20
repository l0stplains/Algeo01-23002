package algeo01_23002.cli.menus.submenus;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.getCenteredText;
import static algeo01_23002.cli.Utilities.getChoice;

public class LinearSystemSolverMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("LINEAR SYSTEM SOLVER MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Gaussian Elimination Method");
            System.out.println("2.  Gauss-Jordan Elimination Method");
            System.out.println("3.  Cramer's Rule Method");
            System.out.println("4.  Inverse Method");
            System.out.println("5.  Back to main menu");
            System.out.print(PURPLE + "->  Select an option (1-5): " + RESET);

            int choice = getChoice(1, 5);
            switch (choice){
                case 1 -> System.out.println("Gaussian Elimination Method");
                case 2 -> System.out.println("Gauss-Jordan Elimination Method");
                case 3 -> System.out.println("Cramer's Rule Method");
                case 4 -> System.out.println("Inverse Method");
                case 5 -> { isRunning = false; }
            }
        }
    }
}
