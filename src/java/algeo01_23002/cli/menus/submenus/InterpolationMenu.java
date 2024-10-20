package algeo01_23002.cli.menus.submenus;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.getCenteredText;
import static algeo01_23002.cli.Utilities.getChoice;

public class InterpolationMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("INTERPOLATION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Polynomial Interpolation");
            System.out.println("2.  Bicubic Spline Interpolation");
            System.out.println("3.  Back to main menu");
            System.out.print(PURPLE + "->  Select an option (1-3): " + RESET);

            int choice = getChoice(1, 3);
            switch(choice) {
                case 1 -> System.out.println("Polynomial Interpolation");
                case 2 -> System.out.println("Bicubic Spline Interpolation");
                case 3 -> {isRunning = false;}
            }
        }
    }
}
