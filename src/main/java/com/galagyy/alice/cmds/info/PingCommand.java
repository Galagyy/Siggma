package com.galagyy.alice.cmds.info;

import com.galagyy.alice.cmds.ICommand;

import org.javacord.api.event.message.MessageCreateEvent;

import java.time.Instant;

public class PingCommand implements ICommand {
    @Override
    public void run(MessageCreateEvent event, String[] args) {
        Instant messageRecieved = Instant.now();
        Instant messageSent = event.getMessage().getCreationTimestamp();

        event.getChannel().sendMessage("Pong! Responded in " + (messageRecieved.toEpochMilli() - messageSent.toEpochMilli()) + " ms");
    }
}
