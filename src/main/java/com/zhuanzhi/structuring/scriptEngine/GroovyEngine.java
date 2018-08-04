package com.zhuanzhi.structuring.scriptEngine;

import com.zhuanzhi.structuring.config.Config;
import com.zhuanzhi.structuring.event.Audit;
import com.zhuanzhi.structuring.model.Model;
import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;

import java.lang.reflect.Field;
import java.util.HashMap;

public class GroovyEngine {

    public static Audit mine(Config auditConfig, String context) throws Exception {
        HashMap<String, String> structure = mineMap(auditConfig, context);
//        System.out.println(structure.get("Enforcement"));

//        System.out.println("problem text : " + structure.get("Problem"));
//
        for(String key : structure.keySet()) {
            System.out.println("key : " + key);
//            System.out.println("value : " + structure.get(key));
        }

        Audit audit = mineValue(auditConfig, structure);
//        Class auditClass = auditfile.getClass();
//        for (String key: structure.keySet()){
//            HashMap<String, String> script  = mineValue(auditConfig.scriptMap.get(key), structure.get(key));
//            for (String filed: script.keySet()){
//                Field auditField = auditClass.getDeclaredField(filed);
//                auditField.setAccessible(true);
//                auditField.set(auditfile, script);
//            }
//        }

        return audit;

    }

    private static HashMap<String, String> mineMap(Config auditConfig, String context) throws Exception {
        GroovyScriptEngine engine = new GroovyScriptEngine(auditConfig.engineRoot);
        HashMap<String, String> menus = run4map(auditConfig, engine, auditConfig.menuScript, context);
        return menus;
    }

//
//    private static String mineValue(String scriptName, String context) throws Exception {
//        GroovyScriptEngine engine = new GroovyScriptEngine(auditConfig.engineRoot);
//        String script = run4value(engine, auditConfig.scriptMap.get(feature), context);
//    }

    public static Audit mineValue(Config auditConfig, HashMap<String, String> structure) throws Exception {
        GroovyScriptEngine engine = new GroovyScriptEngine(auditConfig.engineRoot);
        Audit audit = new Audit();
        Class auditClass = audit.getClass();


        // 搜所有的 Key, 去auditConfig 的 scriptMap 中查配置的提取脚本
        for (String part : structure.keySet()) {
            Model model = run4value(engine, auditConfig.scriptMap.get(part), structure.get(part));
            Field auditField = auditClass.getDeclaredField(part.toLowerCase());
            auditField.setAccessible(true);
            auditField.set(audit, model);
        }

        return audit;
    }


    /**
     * @param engine     运行 Groovy脚本的引擎
     * @param scriptName 需要运行的脚本名
     * @param context    原始内容
     * @return 提取的到东西
     * @throws Exception 脚本找不到之类的
     */
    private static Model run4value(GroovyScriptEngine engine, String scriptName, String context) throws Exception {
        if (scriptName == null) {
            throw new Exception("scriptName is Null");
        } else {
            System.out.println("loading " + scriptName);
        }
        Class scriptClass = engine.loadScriptByName(scriptName);
        GroovyObject scriptInstance = (GroovyObject) scriptClass.newInstance();
        return (Model) scriptInstance.invokeMethod("extractor", context);
    }


    /**
     * @param auditConfig 配置文件
     * @param engine      运行 Groovy脚本的引擎
     * @param scriptName  需要运行的脚本名
     * @param context     原始内容
     * @return 提取的到东西
     * @throws Exception 脚本找不到之类的
     */
    private static HashMap<String, String> run4map(Config auditConfig, GroovyScriptEngine engine, String scriptName, String context) throws Exception {
        Class scriptClass = engine.loadScriptByName(scriptName);
        GroovyObject scriptInstance = (GroovyObject) scriptClass.newInstance();
        return (HashMap<String, String>) scriptInstance.invokeMethod("extractor", new Object[]{auditConfig, context});
    }
}
