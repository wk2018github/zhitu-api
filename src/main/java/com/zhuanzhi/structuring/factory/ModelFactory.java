package com.zhuanzhi.structuring.factory;


import com.zhuanzhi.structuring.model.*;

public class ModelFactory {


    public static Model CreateModel(String className) throws Exception {
        switch (className){
            case "Activity":
                return new Activity();
            case "Underlying":
                return new Underlying();
            case "Enforcement":
                return new Enforcement();
            case "Problem":
                return new Problem();
            case "Opinion":
                return new Opinion();
            case "Advice":
                return new Advice();
        }
        throw new Exception(className + " 类没有配置");
    }
}
