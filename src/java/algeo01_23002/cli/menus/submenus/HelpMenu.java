package algeo01_23002.cli.menus.submenus;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.getCenteredText;

public class HelpMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        String message = String.valueOf(new StringBuilder()
                .append(CYAN + "╔══════════════════════════════════════════════════════╗\n")
                .append("║" + getCenteredText("HELP MENU", WIDTH - 2)+  "║\n")
                .append("╚══════════════════════════════════════════════════════╝\n" + RESET)
                .append(YELLOW + "1. Navigating the Menu\n" + RESET)
                .append("   - Use the number keys (1-5) to select an option from the main menu.\n")
                .append("   - For example, type `1` to solve a linear system, or `4` to do matrix transformations.\n")
                .append("   - To return to the main menu after performing an operation, simply follow the prompts.\n\n")
                .append(YELLOW + "2. Inputting a Matrix\n" + RESET)
                .append("   - You will be prompted to input matrices after selecting an operation.\n")
                .append("   - Input format: Each matrix row should be separated by a newline.\n")
                .append("   - For example, for a 2x2 matrix, type:\n")
                .append("     1 2\n")
                .append("     3 4\n")
                .append("   - Matrix elements should be separated by spaces within a row.\n\n")
                .append(YELLOW + "3. Exiting\n" + RESET)
                .append("   - Select `8` from the main menu to exit the program.\n\n")
                .append(YELLOW + getCenteredText("PRESS ENTER TO EXIT", WIDTH) + RESET +  "\n")
        );
        System.out.println(message);
        scanner.nextLine();
    }
}
