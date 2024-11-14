package com.galagyy.siggma.cmds.action;

import lombok.extern.slf4j.Slf4j;

import com.galagyy.siggma.cmds.ICommand;

import com.galagyy.siggma.service.ClickService;

import org.javacord.api.event.message.MessageCreateEvent;

@Slf4j
public class ClickCommand implements ICommand {
    private final ClickService clickService;

    public ClickCommand(ClickService clickService) {
        this.clickService = clickService;
    }

    @Override
    public void run(MessageCreateEvent event, String args[]) {
        switch (args[1]) {
            case "set":
                if(args[2] != null && args[3] != null){
                    clickService.set(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                } else {
                    log.warn("Not enough fields were provided for the set command.");
                    event.getChannel().sendMessage("You must provide more fields: click set <x> <y>");
                }

                break;

            case "get":
                event.getChannel().sendMessage("The current position is (" + clickService.getX() + ", " + clickService.getY() + ").");
                break;

            case "reset":
                clickService.reset();

                event.getChannel().sendMessage("The position has been reset to it's default settings.");
                break;

            default:
                if(args[2] != null && args[3] != null){
                    clickService.click(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    return;
                }

                clickService.click();
        }
    }
}
