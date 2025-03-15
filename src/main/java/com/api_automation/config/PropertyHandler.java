package com.api_automation.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {
    FileInputStream inputStream;
    Properties properties;
    public PropertyHandler(String fileName){
        String filePath=System.getProperty("user.dir")+"/src/main/resources/"+fileName;
        try {
            inputStream=new FileInputStream(filePath);
            properties=new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getProperty(String key){
        return properties.getProperty(key);
    }
    public void tearDown(){
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
