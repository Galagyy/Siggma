package com.galagyy.alice;

import com.galagyy.alice.cmds.action.ClickCommand;
import com.galagyy.alice.cmds.info.PingCommand;
import lombok.extern.slf4j.Slf4j;

import com.galagyy.alice.cmds.CommandHandler;

import com.galagyy.alice.util.FileManager;
import com.galagyy.alice.util.IOManager;

import com.galagyy.alice.service.ClickService;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {
    public static final String VERSION = "v1.0";

    private CommandHandler commandHandler;
    private ClickService clickService;

    private ScheduledExecutorService scheduler;
    private IOManager ioManager;

    private String token;
    private String prefix;

    public static void main(String[] args){
        App mainApp = new App();
        mainApp.start();
    }

    private void start(){
        setupMenu();
        setupConfigurations();
        setupJavacord();
        loadCommands();
        setupScheduledTasks(2);
    }

    private void setupMenu(){
        this.ioManager = new IOManager();
        this.ioManager.displayMenu();
    }

    private void setupConfigurations() {
        FileManager fileManager = new FileManager();

        if(fileManager.configExists()){
            this.token = fileManager.getValue("token");
            this.prefix = fileManager.getValue("prefix");
            log.info("Retrieved token from config file.");

            return;
        }

        this.token = ioManager.prompt("Please enter the token: ");
        this.prefix = ioManager.prompt("Please enter the prefix: ");

        fileManager.setValue("token", this.token);
        fileManager.setValue("prefix", this.prefix);

        log.info("Retrieved token from user input.");
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
