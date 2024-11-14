package com.galagyy.siggma.cmds;

import org.javacord.api.event.message.MessageCreateEvent;

public interface ICommand {
    void run(MessageCreateEvent event, String args[]);
}
