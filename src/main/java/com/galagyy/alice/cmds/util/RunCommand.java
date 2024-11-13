package com.galagyy.alice.cmds.util;

import com.galagyy.alice.cmds.ICommand;

import lombok.extern.slf4j.Slf4j;

import org.javacord.api.event.message.MessageCreateEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Arrays;

@Slf4j
public class RunCommand implements ICommand {
    @Override
    public void run(MessageCreateEvent event, String args[]) {
        if(args.length <= 1){
            log.error("Not enough fields provided.");
            event.getChannel().sendMessage("You must provide more fields: `run <command>`");
            return;
        }

        String command = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String temp;

            while ((temp = stdInput.readLine()) != null) {
                output.append(temp).append("\n");
            }

            event.getMessage().getChannel().sendMessage("Command Output:\n" + output.toString());
        } catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
}
