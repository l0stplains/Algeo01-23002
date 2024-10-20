package algeo01_23002.cli.menus.submenus;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.getCenteredText;
import static algeo01_23002.cli.Utilities.getChoice;

public class RegressionMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("REGRESSION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Multiple Linear Regression");
            System.out.println("2.  Multiple Quadratic Regression");
            System.out.println("3.  Back to main menu");
            System.out.print(PURPLE + "->  Select an option (1-3): " + RESET);

            int choice = getChoice(1, 3);
            switch(choice) {
                case 1 -> System.out.println("Multiple Linear Regression");
                case 2 -> System.out.println("Multiple Quadratic Regression");
                case 3 -> { isRunning = false; }
            }
        }
    }
}
