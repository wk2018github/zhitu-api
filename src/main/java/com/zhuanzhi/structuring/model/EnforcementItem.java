package com.zhuanzhi.structuring.model;

import java.util.ArrayList;
import java.util.HashMap;

public class EnforcementItem {

    public String auditType;
    public String auditTime;
    public String auditTarget;
    public ArrayList<AuditItem> auditItems;


    @Override
    public String toString() {
        return "EnforcementItem{" +
            "auditType='" + auditType + '\'' +
            ", auditTime='" + auditTime + '\'' +
            ", auditTarget='" + auditTarget + '\'' +
            ", auditItems=" + auditItems +
            '}';
    }

    public HashMap<String, String> getHashMap() {

        HashMap<String, String> hm = new HashMap<>();

        hm.put("auditType", this.auditType.toString());
        hm.put("auditTime", this.auditTime.toString());
        hm.put("auditTarget", this.auditTarget.toString());
        hm.put("auditItems", this.auditItems.toString());

        return hm;
    }
}
