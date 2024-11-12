package com.galagyy.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

@Slf4j
public class FileManager {
    private String configLocation = "";
    private HashMap<String, String> config;

    public FileManager(String configLocation){
        this.configLocation = configLocation;

        loadConfiguration(configLocation);
    }

    public FileManager(){
        this.configLocation = "./config.txt";

        loadConfiguration(configLocation);
    }

    public String getValue(String key){
        return config.get(key);
    }

    private void loadConfiguration(String configLocation){
        File configFile = new File(configLocation);

        if(validateFile(configLocation)){
            try(BufferedReader reader = new BufferedReader(new FileReader(configLocation))){
                String line = reader.readLine();

                while(line != null){
                    String[] split = line.split(":");

                    this.config.put(split[0], split[1]);
                    line = reader.readLine();
                }
            } catch (Exception exception){
                // TODO: Implement logger.
            }
        }

    }

    private boolean validateFile(String location){
        File configLocation = new File(location);

        return configLocation.exists() && !configLocation.isDirectory();
    }
}
