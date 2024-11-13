package com.galagyy.alice;

import lombok.extern.slf4j.Slf4j;

import com.galagyy.alice.cmds.CommandHandler;

import com.galagyy.alice.util.FileManager;
import com.galagyy.alice.util.InputManager;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

@Slf4j
public class App {
    private CommandHandler commandHandler;

    private String token;
    private String prefix;

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

            this.commandHandler = new CommandHandler(this.prefix);

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
            this.prefix = fileManager.getValue("prefix");
            log.info("Retrieved token from config file.");

            return;
        }

        this.token = inputManager.prompt("Please enter the token: ");
        this.prefix = inputManager.prompt("Please enter the prefix: ");

        fileManager.setValue("token", this.token);
        fileManager.setValue("prefix", this.prefix);

        log.info("Retrieved token from user input.");
    }
}
