package com.galagyy.siggma.cmds.action;

import lombok.extern.slf4j.Slf4j;

import com.galagyy.siggma.cmds.ICommand;

import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.Robot;
import java.awt.event.KeyEvent;

@Slf4j
public class TypeCommand implements ICommand {
    @Override
    public void run(MessageCreateEvent event, String[] args) {
        if(args[1] == null){
            log.warn("You did not provide a keyboard stream.");
            event.getChannel().sendMessage("You did not provide a keyboard stream.");
            return;
        }

        String stream = args[1].toLowerCase();
        runStream(stream);
    }

    private void runStream(String stream) {
        try {
            Robot robot = new Robot();
            int i = 0;

            while (i < stream.length()) {
                char ch = stream.charAt(i);

                if (ch == 'h' && stream.startsWith("hold(", i)) {
                    int endIdx = stream.indexOf(")", i);
                    if (endIdx == -1) {
                        log.warn("Invalid hold syntax.");
                        return;
                    }

                    String holdCommand = stream.substring(i + 5, endIdx);
                    String[] parts = holdCommand.split(",");
                    int holdTime = Integer.parseInt(parts[0].trim());
                    char holdChar = parts[1].trim().charAt(0);

                    holdKey(robot, holdChar, holdTime);
                    i = endIdx + 1;
                } else {
                    typeKey(robot, ch);
                    i++;
                }
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private void typeKey(Robot robot, char ch) {
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(ch);

        if (keyCode != KeyEvent.VK_UNDEFINED) {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
    }

    private void holdKey(Robot robot, char ch, int seconds) {
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(ch);

        if (keyCode != KeyEvent.VK_UNDEFINED) {
            robot.keyPress(keyCode);

            try {
                Thread.sleep(seconds * 1000L);
            } catch (InterruptedException e) {
                log.error("Interrupted while holding key: {}", ch);
            }

            robot.keyRelease(keyCode);
        }
    }
}
