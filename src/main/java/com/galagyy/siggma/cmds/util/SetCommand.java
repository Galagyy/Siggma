package com.galagyy.siggma.cmds.util;

import lombok.extern.slf4j.Slf4j;

import com.galagyy.siggma.cmds.ICommand;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.event.message.MessageCreateEvent;

@Slf4j
public class SetCommand implements ICommand {
    private Channel screenshotChannel;
    private Channel logsChannel;

    @Override
    public void run(MessageCreateEvent event, String[] args) {
        if(args[1] == null){
            log.warn("Not enough fields were provided for the log command.");
            return;
        }

        switch (args[2]){
            case "screenshot":
                this.screenshotChannel = event.getChannel();
                break;

            case "log":
                this.logsChannel = event.getChannel();
                break;
            default:
                log.warn("An improper option was specified.");
        }
    }
}
