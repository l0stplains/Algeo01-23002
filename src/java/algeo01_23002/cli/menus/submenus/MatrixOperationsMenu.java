package algeo01_23002.cli.menus.submenus;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.getCenteredText;
import static algeo01_23002.cli.Utilities.getChoice;

public class MatrixOperationsMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("MATRIX OPERATIONS MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Matrix Multiplication");
            System.out.println("2.  Matrix Addition");
            System.out.println("3.  Matrix Subtraction");
            System.out.println("4.  Back to main menu");
            System.out.print(PURPLE + "->  Select an option (1-4): " + RESET);

            int choice = getChoice(1, 4);
            switch(choice) {
                case 1 -> System.out.println("Matrix Multiplication");
                case 2 -> System.out.println("Matrix Addition");
                case 3 -> System.out.println("Matrix Subtraction");
                case 4 -> { isRunning = false; }
            }
        }
    }
}
