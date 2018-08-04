package com.zhuanzhi.structuring.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 审计问题
 * 	- 问题：结转结余资金管理不够规范
 * 	- 情况： 该单位本级【被审计对象】2011年末结转结余资金304.15万元【金额】中，有225.2万元【金额】未纳入2012年初部门预算，其中项目支出结转资金138.1万元【金额】、项目支出结余资金87.1万元【金额】
 * 	- 情况： 该单位所属省宗教协会【被审计对象】2011年末结转结余资金116.15万元【金额】，属以前年度项目支出结余资金，未纳入2012年初部门预算。【审计发现的主要问题1-1上年结转结余资金部分未纳入当年年初部门预算】
 * 	- 违反法律法规： 以上不符合《AA省财政厅关于清理填报部门结余结转资金的通知》（GA财预〔2010〕187号）【法律法规1】“三......从2011年起，省直各部门、单位的所有结余结转资金必须全部编入年初预算”的规定，今后应全部纳入年初预算，统筹安排，规范使用【法律法规1-1】
 */
public class Problem  extends Model {

    public ArrayList<HashMap<String, String>> auditProblemsAndLaws;

    @Override
    public String toString() {
        return "Problem{" +
            "auditProblemsAndLaws=" + auditProblemsAndLaws +
            '}';
    }

    public HashMap<String, String> getHashMap() {

        HashMap<String, String> hm = new HashMap<>();

        hm.put("auditProblemsAndLaws", this.auditProblemsAndLaws.toString());

        return hm;
    }
}
