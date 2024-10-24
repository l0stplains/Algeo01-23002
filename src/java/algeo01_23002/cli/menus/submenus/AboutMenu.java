package algeo01_23002.cli.menus.submenus;

import java.util.Scanner;

import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.getCenteredText;

public class AboutMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        String message = String.valueOf(new StringBuilder()
                .append(CYAN + "╔══════════════════════════════════════════════════════╗\n")
                .append("║" + getCenteredText("ABOUT THE PROJECT", WIDTH - 2) + "║\n")
                .append("╚══════════════════════════════════════════════════════╝\n" + RESET)
                .append(YELLOW + "Project Name: " + RGB + " - Java Linear Algebra Library" + "\n")
                .append(YELLOW + "Version: " + RESET + "1.0.0\n")
                .append(YELLOW + "Authors: " + RED + "R" + RESET + "efki Alfarizi, " + GREEN + "G" + RESET + "untara Hambali, "+ "Adhimas Aryo " + BLUE + "B" + RESET + "imo" +  "\n\n")
                .append(CYAN + "════════════════════════════════════════════════════════\n")
                .append(getCenteredText(RGB + " Team's Milestone Project", WIDTH + 20) + "\n")
                .append(CYAN + "════════════════════════════════════════════════════════\n" + RESET)
                .append("This project is part of our Linear Algebra and Geometry course at " + CYAN + "Institut Teknologi Bandung" + RESET + ".\n")
                .append("Our aim is to develop a comprehensive " + YELLOW + "Linear Algebra Library" + RESET + " that provides\n")
                .append("various linear algebra utilities such as linear system solver, regression, interpolation, matrix\n")
                .append("operations, and many more. This utility simplifies the calculation process by\n")
                .append("offering a clean and interactive command-line interface (CLI).\n\n")
                .append(CYAN + "Milestone: " + RESET + "The culmination of our learning in Linear Algebra and Geometry\n")
                .append("through the practical implementation of concepts into a usable software tool.\n\n")
                .append(CYAN + "Key Features:\n" + RESET)
                .append("   - Easy matrix input and operations.\n")
                .append("   - Supports a ton of linear algebra utilities.\n")
                .append("   - User-friendly CLI for navigation and interaction.\n\n")
                .append(CYAN + "Acknowledgments:\n" + RESET)
                .append("   - We would like to thank our course instructors and mentors for their guidance.\n")
                .append("   - Special thanks to the RGB team for collaboration and efforts in building this project.\n\n")
                .append(CYAN + "╔══════════════════════════════════════════════════════╗\n")
                .append("║" + RESET + getCenteredText("Created by " + RGB +" Team, 2024", WIDTH + 17) + CYAN + "║\n")
                .append(CYAN + "╚══════════════════════════════════════════════════════╝\n\n" + RESET)
                .append(YELLOW + getCenteredText("PRESS ENTER TO EXIT", WIDTH) + RESET +  "\n")
        );
        System.out.println(message);
        scanner.nextLine();
    }
}
