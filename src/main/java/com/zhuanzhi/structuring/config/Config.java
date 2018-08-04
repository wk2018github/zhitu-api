package com.zhuanzhi.structuring.config;

import java.util.HashMap;
import java.util.Iterator;

public class Config {

    public int auditType;
    public HashMap<String, String> structMap;

    public String engineRoot;

    public String menuScript;
    public HashMap<String, String> scriptMap;



    public Config(){
//        this.structMap = new HashMap<String, String>();
//
//        // 审计活动
//        this.structMap.put("审计活动", "Activity");
//        // 被审计对象
//        this.structMap.put("基本情况", "Underlying");
//        // 审计实施情况
//        this.structMap.put("被调查事项的基本情况", "Enforcement");
//        this.structMap.put("被审计单位基本情况", "Enforcement");
//        this.structMap.put("审计实施情况", "Enforcement");
//        // 审计发现的主要问题和处理意见
//        this.structMap.put("审计调查发现的主要问题", "Problem");
//        this.structMap.put("审计发现的主要问题和处理意见", "Problem");
//        this.structMap.put("审计发现的主要问题及处理意见", "Problem");
//        // 审计评价意见
//        this.structMap.put("审计调查评价意见", "Opinion");
//        this.structMap.put("审计评价意见", "Opinion");
//        // 审计建议
//        this.structMap.put("审计调查建议", "Advice");
//        this.structMap.put("审计建议", "Advice");
//
//
//
//        this.engineRoot = "/home/cds/chouqu/extract/structuring/src/main/java/com/zhuanzhi/extractor/script";
//        this.menuScript = "MenusMap.groovy";
//
//        this.scriptMap = new HashMap<String, String>();
//        this.scriptMap.put("Activity", "ActivityModel.groovy");
//        this.scriptMap.put("Underlying", "UnderlyingModel.groovy");
//        this.scriptMap.put("Enforcement", "EnforcementModel.groovy");
//        this.scriptMap.put("Problem", "ProblemModel.groovy");
//        this.scriptMap.put("Opinion", "OpinionModel.groovy");
//        this.scriptMap.put("Advice", "AdviceModel.groovy");


//        this.scriptMap = new HashMap<String, String>();
//        this.scriptMap.put("laws", "script/Activity.groovy");

        this.readConfig();

    }

    private void readConfig() {
        Iterator<String> keys = StructureConfig.structureConfiguration.getKeys();
        this.engineRoot = StructureConfig.structureConfiguration.getString("engineRoot");
        this.menuScript = StructureConfig.structureConfiguration.getString("menuScript");

        this.scriptMap = new HashMap<String, String>();
        this.structMap = new HashMap<String, String>();

        while(keys.hasNext()) {
            String key = keys.next();

            String value = StructureConfig.structureConfiguration.getString(key);
            if(key.startsWith("scriptMap")) {
                scriptMap.put(key.split("\\.")[1], value);
                System.out.println(key.split("\\.")[1]);
            }else if(key.startsWith("structMap")) {
                structMap.put(key.split("\\.")[1], value);
                System.out.println(key.split("\\.")[1]);
            }else {

            }
        }

    }

    public static void main(String[] args) {
//        readConfig();
        Config c = new Config();
    }

}
