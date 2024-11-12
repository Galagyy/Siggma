package com.galagyy.cmds;

import org.javacord.api.event.message.MessageCreateEvent;

public interface ICommand {
    void run(MessageCreateEvent event);
}
