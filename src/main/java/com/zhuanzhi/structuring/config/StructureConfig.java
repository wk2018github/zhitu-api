package com.zhuanzhi.structuring.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;

public class StructureConfig {

    public static String CONF_DIR;
    public static String CONF_NAME_API = "config.properties";
    public static Configuration structureConfiguration;


    static{

//        System.setProperty("CONF_DIR", "/zhuanzhi_conf");
//        System.setProperty("CONF_DIR", "/home/cds/chouqu/extract/structuring");
        System.setProperty("CONF_DIR", "F:\\ZhiTu\\ZhituBackend\\src\\main\\resources");

        try{
            CONF_DIR = System.getProperty("CONF_DIR");
            System.out.println("CONF_DIR = " + CONF_DIR);
            if(CONF_DIR == null){
                throw new Exception("CONF_DIR is null");
            }
            Configurations configs = new Configurations();
            FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "utf-8");
            structureConfiguration = configs.properties(new File(CONF_DIR, CONF_NAME_API));

        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }


    }
}
