package com.galagyy.siggma.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.HashMap;

@Slf4j
public class FileManager {
    private final String configLocation;
    private HashMap<String, String> config;

    public FileManager(String configLocation){
        this.configLocation = configLocation;
        this.config = new HashMap<>();

        if(new File(configLocation).exists()){
            loadConfiguration();
        }
    }

    public FileManager(){
        this.configLocation = "./config.txt";
        this.config = new HashMap<>();

        if(new File(configLocation).exists()){
            loadConfiguration();
        }
    }

    public String getValue(String key){
        return config.get(key);
    }

    public void setValue(String key, String value){
        config.put(key, value);

        writeConfiguration();
    }

    public boolean configExists(){
        File configFile = new File(configLocation);

        return configFile.exists();
    }

    private void loadConfiguration(){
        try (FileInputStream fileInputStream = new FileInputStream(this.configLocation)){
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.config = (HashMap<String, String>) objectInputStream.readObject();

            objectInputStream.close();
        } catch (Exception exception){
            log.warn(exception.getMessage());
        }
    }

    private void writeConfiguration(){
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.configLocation)){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(config);

            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception exception){
            log.warn(exception.getMessage());
        }
    }
}
