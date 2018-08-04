package com.zhuanzhi.structuring.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  预算审计
 *     - 类型： 收入    auditType
 *  	- 审计年度：2012年    auditTime
 *  	- 被审计对象： 省民族宗教事务局    auditTarget
 *  	- 包括：
 *  		- 款项 1
 *  		- 款项 2
 *  		- 款项 3
 */
public class Enforcement extends Model {

    public ArrayList<EnforcementItem> enforcementItems = null;

    public Enforcement(){

    }

    @Override
    public String toString() {

        return "Enforcement{" +
            "enforcementItems=" + enforcementItems +
            '}';
    }

    public ArrayList<HashMap<String, String>> getHashMapArray() {

        ArrayList<HashMap<String, String>> enforcementItemsHMArray = new ArrayList<HashMap<String, String>>();

        if(enforcementItems != null) {
            for (EnforcementItem enforcementItem : enforcementItems) {
                HashMap<String, String> enforcementItemHM = enforcementItem.getHashMap();
                enforcementItemsHMArray.add(enforcementItemHM);
            }
        }

        return enforcementItemsHMArray;
    }
}
