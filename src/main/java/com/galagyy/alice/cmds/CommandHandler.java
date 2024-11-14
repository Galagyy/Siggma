package com.galagyy.alice.cmds;

import com.galagyy.alice.cmds.action.ClickCommand;
import com.galagyy.alice.cmds.action.TypeCommand;
import com.galagyy.alice.cmds.info.PingCommand;
import com.galagyy.alice.cmds.util.RunCommand;
import lombok.extern.slf4j.Slf4j;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
public class CommandHandler {
    private HashMap<String, ICommand> commands;
    private final String prefix;

    public CommandHandler(String prefix) {
        this.prefix = prefix;

        this.commands = new HashMap<>();
    }

    public CommandHandler(){
        this.prefix = "!";

        this.commands = new HashMap<>();
    }

    public void handleCommand(MessageCreateEvent event){
        if(!validateCommand(event)){
            return;
        }

        String splitCommand[] = extractCommand(event);

        Optional<ICommand> optionalCommand = Optional.ofNullable(commands.get(splitCommand[0]));

        if (optionalCommand.isPresent()) {
            optionalCommand.get().run(event, splitCommand);
        } else {
            log.error("Unable to find command: \"{}\"", splitCommand[0]);
        }
    }

    public void registerCommand(String prefix, ICommand command){
        commands.put(prefix, command);
    }

    private String[] extractCommand(MessageCreateEvent event){
        return event.getMessageContent().substring(prefix.length()).split(" ");
    }

    private boolean validateCommand(MessageCreateEvent event){
        return event.getMessageContent().startsWith(prefix) && event.getMessageAuthor().isRegularUser();
    }
}
