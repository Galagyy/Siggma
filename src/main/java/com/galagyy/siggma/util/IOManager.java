package com.galagyy.siggma.util;

import com.galagyy.siggma.App;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.File;

import java.util.Scanner;

public class IOManager {
    private final Scanner mainScanner;

    public IOManager(){
        this.mainScanner = new Scanner(System.in);
    }

    public void displayMenu(){
        System.out.println(
                "   _____ _                             \n" +
                "  / ____(_)                            \n" +
                " | (___  _  __ _  __ _ _ __ ___   __ _ \n" +
                "  \\___ \\| |/ _` |/ _` | '_ ` _ \\ / _` |\n" +
                "  ____) | | (_| | (_| | | | | | | (_| |\n" +
                " |_____/|_|\\__, |\\__, |_| |_| |_|\\__,_|\n" +
                "            __/ | __/ |                \n" +
                "           |___/ |___/                 \n"
        );

        System.out.println("Version: " + App.VERSION);
        System.out.println("Execution Time: " + java.time.Clock.systemDefaultZone().instant().getEpochSecond());
        System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

    public void saveImage(BufferedImage image){

    }

    public void saveImage(BufferedImage image, String name) throws Exception{
        if(name.endsWith(".png")){
            ImageIO.write(image, "png", new File(name));
            return;
        }

        ImageIO.write(image, "PNG", new File(name.replaceAll(".", "") + ".png"));
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
