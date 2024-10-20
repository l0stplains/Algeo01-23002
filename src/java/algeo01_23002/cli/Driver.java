package algeo01_23002.cli;


import static algeo01_23002.cli.Const.*;
import static algeo01_23002.cli.Utilities.*;

import algeo01_23002.cli.menus.*;


public class Driver {



    public static void main(String[] args) {
        printRGBHeader();
        // printWelcomeMessage();
        MainMenu.show();
        exitProgram();
    }

    // Cool ASCII header with RGB team name
    private static void printRGBHeader() {
        System.out.println(RED + "                ██████  " + GREEN + " ██████  " + BLUE + "██████  " + RESET);
        System.out.println(RED + "                ██   ██ " + GREEN + "██       " + BLUE + "██   ██ " + RESET);
        System.out.println(RED + "                ██████  " + GREEN + "██   ███ " + BLUE + "██████  " + RESET);
        System.out.println(RED + "                ██   ██ " + GREEN + "██    ██ " + BLUE + "██   ██ " + RESET);
        System.out.println(RED + "                ██   ██ " + GREEN + " ██████  " + BLUE + "██████  " + RESET);
        System.out.println();
        System.out.println(YELLOW + getCenteredText("A Java Linear Algebra Library", WIDTH) + RESET);
        System.out.println(YELLOW + getCenteredText("Made by:", WIDTH) + RESET);
        System.out.println(YELLOW + getCenteredText(RED + "Refki " + GREEN + "Guntara " + BLUE + "Bimo", WIDTH + 15) + RESET);
        System.out.println();
    }

    // Exit Program
    private static void exitProgram() {
        System.out.println(CYAN + "Thank you for using " + RED + "R" + GREEN + "G" + BLUE + "B " + YELLOW + "Linear Algebra Library" + RESET);
        System.exit(0);
    }


}
