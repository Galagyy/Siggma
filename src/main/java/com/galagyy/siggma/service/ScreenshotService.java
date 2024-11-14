package com.galagyy.siggma.service;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;

import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.File;

@Slf4j
public class ScreenshotService {
    public void takeScreenshot(){
        takeScreenshot(System.currentTimeMillis()+".png");
    }

    public void takeScreenshot(String fileName) {
        String patchedFileName;

        if(fileName.endsWith(".png")){
            patchedFileName = fileName + ".png";
        } else {
            patchedFileName = fileName;
        }

        try {
            BufferedImage bufferedImage = new Robot().createScreenCapture(new Rectangle(
                    Toolkit.getDefaultToolkit().getScreenSize()
            ));

            ImageIO.write(bufferedImage, "png", new File(patchedFileName));
        } catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
}
