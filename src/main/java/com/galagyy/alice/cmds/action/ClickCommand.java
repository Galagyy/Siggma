package com.galagyy.alice.cmds.action;

import com.galagyy.alice.cmds.ICommand;

import lombok.extern.slf4j.Slf4j;

import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.Toolkit;
import java.awt.Robot;

@Slf4j
public class ClickCommand implements ICommand {
    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;

    private int x;
    private int y;

    public ClickCommand() {
        this.MAX_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.MAX_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.x = MAX_WIDTH / 2;
        this.y = MAX_HEIGHT / 2;
    }

    @Override
    public void run(MessageCreateEvent event, String args[]) {
        switch (args[1]) {
            case "set":
                if(args[2] != null && args[3] != null){
                    setPosition(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                } else {
                    event.getChannel().sendMessage("You must provide more fields.");
                }

                break;

            case "get":
                event.getChannel().sendMessage("The current position is (" + this.x + ", " + this.y + ").");
                break;

            case "reset":
                this.x = MAX_WIDTH / 2;
                this.y = MAX_HEIGHT / 2;

                event.getChannel().sendMessage("The position has been reset to it's default settings.");
                break;

            case "click":
                int prevX = this.x;
                int prevY = this.y;

                if(args[2] != null && args[3] != null){
                    if(setPosition(Integer.parseInt(args[2]), Integer.parseInt(args[3]))){
                        click();
                    }

                    this.x = prevX;
                    this.y = prevY;
                } else {
                    log.error("You must provide more fields.");
                    event.getChannel().sendMessage("You must provide more fields.");
                }
                break;
            default:
                click();
        }
    }

    private void click(){
        try {
            Robot robot = new Robot();
            robot.mouseMove(this.x, this.y);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private boolean setPosition(int x, int y){
        if(this.x <= MAX_WIDTH && this.y <= MAX_HEIGHT && this.x >= 0 && this.y >= 0){
            this.x = x;
            this.y = y;

            return true;
        } else {
            log.error("Invalid position provided, ({}, {})", this.x, this.y);
        }

        return false;
    }
}
