package com.galagyy.siggma.service;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;

public class MessageService {
    public static void sendMessage(String message, TextChannel channel) {
        new MessageBuilder().append(message).send(channel);
    }
}
