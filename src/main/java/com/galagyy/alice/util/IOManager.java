package com.galagyy.alice.util;

import com.galagyy.alice.App;

import java.util.Scanner;

public class IOManager {
    private final Scanner mainScanner;

    public IOManager(){
        this.mainScanner = new Scanner(System.in);
    }

    public void displayMenu(){
        System.out.println(
                "           _ _          \n" +
                "     /\\   | (_)         \n" +
                "    /  \\  | |_  ___ ___ \n" +
                "   / /\\ \\ | | |/ __/ _ \\\n" +
                "  / ____ \\| | | (_|  __/\n" +
                " /_/    \\_\\_|_|\\___\\___|\n" +
                "                        \n"
        );

        System.out.println("Version: " + App.VERSION);
        System.out.println("Execution Time: " + java.time.Clock.systemDefaultZone().instant().getEpochSecond());
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

    public String prompt(String prompt){
        System.out.print(prompt);
        return mainScanner.next();
    }

    public int promptInt(String prompt){
        System.out.print(prompt);
        return mainScanner.nextInt();
    }
}
