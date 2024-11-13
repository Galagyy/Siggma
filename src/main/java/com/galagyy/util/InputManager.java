package com.galagyy.util;

import java.util.Scanner;

public class InputManager {
    private final Scanner mainScanner;

    public InputManager(){
        this.mainScanner = new Scanner(System.in);
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
