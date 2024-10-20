package algeo01_23002.cli.menus.submenus;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.getCenteredText;
import static algeo01_23002.cli.Utilities.getChoice;

public class MatrixTransformationMenu {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
        System.out.println("║" + getCenteredText("MAIN MENU", WIDTH - 2)+  "║");
        System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
        System.out.println("1.  Linear System Solver");
        System.out.println("2.  Interpolation");
        System.out.println("3.  Regression");
        System.out.println("4.  Matrix Transformation");
        System.out.println("5.  Matrix Operations");
        System.out.print(PURPLE + "->  Select an option (1-5): " + RESET);

        boolean isRunning = true;
        while(isRunning) {
            int choice = getChoice(1, 5);
        }
    }
}
