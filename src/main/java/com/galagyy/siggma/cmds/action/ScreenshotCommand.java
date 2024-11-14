package com.galagyy.siggma.cmds.action;

import lombok.extern.slf4j.Slf4j;

import com.galagyy.siggma.cmds.ICommand;

import com.galagyy.siggma.service.ScreenshotService;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.File;

@Slf4j
public class ScreenshotCommand implements ICommand {
    private final String tempFileLocation;
    private final ScreenshotService screenshotService;

    public ScreenshotCommand(ScreenshotService screenshotService, String tempFileLocation){
        this.screenshotService = screenshotService;
        this.tempFileLocation = tempFileLocation;
    }

    public ScreenshotCommand(ScreenshotService screenshotService) {
        this.screenshotService = screenshotService;
        this.tempFileLocation = "./ssCommand.png";
    }

    @Override
    public void run(MessageCreateEvent event, String[] args) {
        screenshotService.takeScreenshot(tempFileLocation);

        new MessageBuilder()
                .addAttachment(new File(this.tempFileLocation))
                .send(event.getChannel());

    }
}
