package com.zhuanzhi.structuring.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 审计建议
 * 	- 审计建议 1
 * 	- 审计建议 2
 * 	- 审计建议 3
 */
public class Advice extends Model {

    public ArrayList<String> advices = null;

    @Override
    public String toString() {
        String advices = "";
        for (String advice : this.advices){
            advices = advices + advice + ";";
        }
        return "Advice{" +
            "advices=" + advices +
            '}';
    }

    public HashMap<String, String> getHashMap() {

        HashMap<String, String> hm = new HashMap<>();

        hm.put("advices", this.advices.toString());

        return hm;
    }
}
