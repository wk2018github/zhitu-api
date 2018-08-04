package com.zhuanzhi.structuring;

import com.zhuanzhi.structuring.config.Config;
import com.zhuanzhi.structuring.event.Audit;
import com.zhuanzhi.structuring.factory.FileReaderFactory;
import com.zhuanzhi.structuring.fileReader.Reader;
import com.zhuanzhi.structuring.model.*;
import com.zhuanzhi.structuring.scriptEngine.GroovyEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class App {

    //    private static String base_dir = "/home/cds/chouqu/extract/data/";
    private static String base_dir = "G:\\\\data\\";

    public static String[] fileList = {
            "AA省审计厅关于SS市人防易地建设费征收管理情况的专项审计调查报告-未标注.doc",
            "AA省审计厅关于对省民族宗教事务局2012年预算执行情况的审计报告 -未标注.doc",
            "工伤生育市本级审计报告 -未标注.doc"
    };


    public static void main(String[] args) throws Exception {
        HashMap<String, ArrayList<HashMap<String, String>>> allEntitiesHashMap =
                new HashMap<String, ArrayList<HashMap<String, String>>>();

        for (String file: fileList) {
            processFile(base_dir + file, allEntitiesHashMap);
        }
        //allEntitiesHashMap中有6个key（problem，activity,advice,enforcement，underlying，opinion）
        /*Set<String> keys = allEntitiesHashMap.keySet();
        for(String key :keys){
            System.out.println(key);
        }*/
        System.out.println("##################################allEntitiesHashMap打印开始######################################");
        System.out.println(allEntitiesHashMap.toString());
        System.out.println("##################################allEntitiesHashMap打印结束######################################");
//        processFile(fileList[2]);
    }

    public static void processFile(String filename,
                                   HashMap<String, ArrayList<HashMap<String, String>>> allEntitiesHashMap) throws Exception {
        Reader docReader = FileReaderFactory.CreateFileReader(FileReaderFactory.DOC);
        String fileContent = docReader.readFile(filename);
        Config auditConfig = new Config();


        Audit jijinAuditReport = (Audit) GroovyEngine.mine(auditConfig, fileContent);


        System.out.println("================== audit report ===========");
        System.err.println("====activity : ");
        Activity activity = jijinAuditReport.activity;
        if(activity == null) {
            System.err.println("activity is null");
        }else {
            HashMap<String, String> hm_activity = activity.getHashMap();
            for (String key : hm_activity.keySet()) {
                System.err.println("key :" + key);
                System.err.println("value :" + hm_activity.get(key));
            }

            saveEntityHashMap("activity", hm_activity, allEntitiesHashMap);
        }

        System.out.println("====advice ; ");
        Advice advice = jijinAuditReport.advice;
        if(advice == null) {
            System.err.println("advice is null");
        }else {
            HashMap<String, String> hm_advice = advice.getHashMap();
            for (String key : hm_advice.keySet()) {
                System.err.println("key :" + key);
                System.err.println("value :" + hm_advice.get(key));
            }

            saveEntityHashMap("advice", hm_advice, allEntitiesHashMap);
        }

        System.out.println("====opinion ; ");
        Opinion opinion = jijinAuditReport.opinion;
        if(opinion == null) {
            System.err.println("opinion is null");
        }else {
            HashMap<String, String> hm_opinion = opinion.getHashMap();
            for (String key : hm_opinion.keySet()) {
                System.err.println("key :" + key);
                System.err.println("value :" + hm_opinion.get(key));
            }

            saveEntityHashMap("opinion", hm_opinion, allEntitiesHashMap);
        }

        System.out.println("====underlying ; ");
        Underlying underlying = jijinAuditReport.underlying;
        if(underlying == null) {
            System.err.println("underlying is null");
        }else {
            HashMap<String, String> hm_underlying = underlying.getHashMap();
            for (String key : hm_underlying.keySet()) {
                System.err.println("key :" + key);
                System.err.println("value :" + hm_underlying.get(key));
            }

            saveEntityHashMap("underlying", hm_underlying, allEntitiesHashMap);
        }

        System.out.println("====problem ; ");
        Problem problem = jijinAuditReport.problem;
        if(problem == null) {
            System.err.println("problem is null");
        }else {
            HashMap<String, String> hm_problem = problem.getHashMap();
            for (String key : hm_problem.keySet()) {
                System.err.println("key :" + key);
                System.err.println("value :" + hm_problem.get(key));
            }

            saveEntityHashMap("problem", hm_problem, allEntitiesHashMap);
        }

        System.out.println("====enforcement ; ");
        Enforcement enforcement = jijinAuditReport.enforcement;
        if(enforcement == null) {
            System.err.println("enforcement is null");
        }else {
            ArrayList<HashMap<String, String>> enforcementItemsHMArray = enforcement.getHashMapArray();
            for(HashMap<String, String> enforcementItemHM : enforcementItemsHMArray) {
                for (String key : enforcementItemHM.keySet()) {
                    System.err.println("key :" + key);
                    System.err.println("value :" + enforcementItemHM.get(key));
                }
            }

            for(HashMap<String, String> enforcementItemHM : enforcementItemsHMArray) {
                saveEntityHashMap("enforcement", enforcementItemHM, allEntitiesHashMap);
            }
        }


    }

    private static void saveEntityHashMap(String key,
                                          HashMap<String, String> entityHashMap,
                                          HashMap<String, ArrayList<HashMap<String, String>>> allEntitiesHashMap) {
        if (allEntitiesHashMap.containsKey(key)) {
            allEntitiesHashMap.get(key).add(entityHashMap);
        }else{
            ArrayList<HashMap<String, String>> entityHashMaps = new ArrayList<HashMap<String, String>>();
            entityHashMaps.add(entityHashMap);
            allEntitiesHashMap.put(key, entityHashMaps);
        }
    }
}
