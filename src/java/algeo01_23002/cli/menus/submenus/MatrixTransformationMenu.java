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
        boolean isRunning = true;
        while(isRunning) {
            System.out.println(CYAN + "╔══════════════════════════════════════════════════════╗");
            System.out.println("║" + getCenteredText("MATRIX TRANSFORMATION MENU", WIDTH - 2)+  "║");
            System.out.println("╚══════════════════════════════════════════════════════╝" + RESET);
            System.out.println("1.  Matrix Row Echelon Form");
            System.out.println("2.  Matrix Reduced Row Echelon Form");
            System.out.println("3.  Matrix Adjoint");
            System.out.println("4.  Matrix Inverse with Adjoint");
            System.out.println("5.  Matrix Inverse with Row Reduction");
            System.out.println("6.  Back to main menu");
            System.out.print(ARROW + "  Select an option (1-6): " + RESET);

            int choice = getChoice(1, 6);
            switch (choice){
                case 1 -> System.out.println("1. Matrix Row Echelon Form");
                case 2 -> System.out.println("2. Matrix Reduced Row Echelon Form");
                case 3 -> System.out.println("3. Matrix Adjoint");
                case 4 -> System.out.println("4. Matrix Inverse with Adjoint");
                case 5 -> System.out.println("5. Matrix Inverse with Row Reduction");
                case 6 -> { isRunning = false; }
            }
        }
    }
}
