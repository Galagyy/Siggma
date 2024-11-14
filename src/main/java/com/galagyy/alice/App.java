package com.galagyy.alice;

import com.galagyy.alice.cmds.action.ClickCommand;
import com.galagyy.alice.cmds.info.PingCommand;
import lombok.extern.slf4j.Slf4j;

import com.galagyy.alice.cmds.CommandHandler;

import com.galagyy.alice.util.FileManager;
import com.galagyy.alice.util.InputManager;

import com.galagyy.alice.service.ClickService;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {
    private CommandHandler commandHandler;
    private ClickService clickService;

    private ScheduledExecutorService scheduler;

    private String token;
    private String prefix;

    public static void main(String[] args){
        App mainApp = new App();
        mainApp.start();
    }

    private void start(){
        setupConfigurations();
        setupJavacord();
        loadCommands();
    }

    private void setupJavacord(){
        try {
            DiscordApi api = new DiscordApiBuilder()
                    .setToken(token)
                    .setAllIntents()
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

    private void loadCommands(){
        this.commandHandler.registerCommand("ping", new PingCommand());
        this.commandHandler.registerCommand("click", new ClickCommand(this.clickService));
        this.commandHandler.registerCommand("type", new PingCommand());
    }

    private void setupScheduledTasks(int poolSize){
        this.clickService = new ClickService();
        this.scheduler = Executors.newScheduledThreadPool(poolSize);

        scheduler.scheduleAtFixedRate(() -> clickService.click(), 10, 15, TimeUnit.MINUTES);
    }
}
