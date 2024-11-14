package com.galagyy.siggma.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.event.InputEvent;

@Slf4j
@Setter
@Getter
public class ClickService {
    private Robot robot;

    private final boolean enabled;

    private int x;
    private int y;

    public ClickService(int x, int y) {
        this.x = x;
        this.y = y;

        this.enabled = true;

        createRobot();
    }

    public ClickService() {
        this.x = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        this.y = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

        this.enabled = true;

        createRobot();
    }

    public synchronized void click() {
        click(this.x, this.y);
    }

    public synchronized void click(int x, int y) {
        this.robot.mouseMove(this.x, this.y);

        this.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        this.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void reset() {
        this.x = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        this.y = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] get(){
        return new int[]{this.x, this.y};
    }

    private void createRobot() {
        try {
            this.robot = new Robot();
        } catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
}
