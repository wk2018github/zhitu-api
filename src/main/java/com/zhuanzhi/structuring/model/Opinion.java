package com.zhuanzhi.structuring.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 审计意见
 * 	- 被审计单位： 1
 * 	- 审计覆盖面： 100%
 * 	- 审计资金总额： 1429.76万元
 * 	- 审计意见 1    审计结果表明(.*?)，
 * 	- 审计意见 2
 * 	- 审计意见 3
 */
public class Opinion extends Model {

    public String auditTarget;
    public String auditCoverage;
    public String auditTotalFunds;
    public ArrayList<String> advice;

    @Override
    public String toString() {
        return "Opinion{" +
            "auditTarget='" + auditTarget + '\'' +
            ", auditCoverage='" + auditCoverage + '\'' +
            ", auditTotalFunds='" + auditTotalFunds + '\'' +
            ", advice=" + advice +
            '}';
    }

    public HashMap<String, String> getHashMap() {

        HashMap<String, String> hm = new HashMap<>();

        hm.put("auditTarget", this.auditTarget);
        hm.put("auditCoverage", this.auditCoverage);
        hm.put("auditTotalFunds", this.auditTotalFunds);
        hm.put("advice", this.advice.toString());

        return hm;
    }
}
