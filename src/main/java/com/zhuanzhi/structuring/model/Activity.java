package com.zhuanzhi.structuring.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 审计活动
 *  - 审计发起单位  auditReporter
 * 	- 审计发生时间  auditTime2Event
 * 	- 被审计对象  auditTarget
 * 	- 被审计年度  auditTime
 * 	- 审计行业  auditIndustry
 * 	- 审计分类  auditClassification
 * 	- 法律法规  auditLaws
 */
public class Activity extends Model {
    public String auditReporter;
    public ArrayList<String> auditTime2Event;
    public String auditTarget;
    public String auditTime;
    public String auditIndustry;
    public String auditClassification;
    public ArrayList<String> auditLaws;

//    public String toString(){
//        String[] law = (String[]) this.laws.toArray();
//        System.out.println(law);
//        return StringUtil.join(law);
//    }


    @Override
    public String toString() {
        return "Activity{" +
            "auditReporter='" + auditReporter + '\'' +
            ", auditTime2Event=" + auditTime2Event +
            ", auditTarget='" + auditTarget + '\'' +
            ", auditTime='" + auditTime + '\'' +
            ", auditIndustry='" + auditIndustry + '\'' +
            ", auditClassification='" + auditClassification + '\'' +
            ", auditLaws=" + auditLaws +
            '}';
    }

    public HashMap<String, String> getHashMap() {

        HashMap<String, String> hm = new HashMap<>();

        hm.put("auditReporter", this.auditReporter);
        hm.put("auditTime2Event", this.auditTime2Event.toString());
        hm.put("auditTarget", this.auditTarget);
        hm.put("auditTime", this.auditTime);
        hm.put("auditIndustry", this.auditIndustry);
        hm.put("auditLaws", this.auditLaws.toString());

        return hm;
    }
}
