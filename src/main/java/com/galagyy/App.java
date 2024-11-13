package com.galagyy;

import lombok.extern.slf4j.Slf4j;

import com.galagyy.cmds.CommandHandler;

import com.galagyy.util.FileManager;
import com.galagyy.util.InputManager;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

@Slf4j
public class App {
    private CommandHandler commandHandler;

    private String token;

    public static void main(String[] args){
        App mainApp = new App();
        mainApp.start();
    }

    private void start(){
        setupConfigurations();
        setupJavacord();
    }

    private void setupJavacord(){
        try {
            DiscordApi api = new DiscordApiBuilder()
                    .setToken(token)
                    .setAllNonPrivilegedIntents()
                    .login().join();

            this.commandHandler = new CommandHandler();

            api.addMessageCreateListener(event -> {
                this.commandHandler.handleCommand(event);
            });
        } catch (Exception exception) {
            log.error(exception.getMessage());
            System.exit(1);
        }
    }

    private void setupConfigurations() {
        FileManager fileManager = new FileManager();
        InputManager inputManager = new InputManager();

        if(fileManager.configExists()){
            this.token = fileManager.getValue("token");
            log.info("Retrieved token from config file.");

            return;
        }

        this.token = inputManager.prompt("Please enter the token:");
        fileManager.setValue("token", this.token);
        log.info("Retrieved token from user input.");
    }
}
