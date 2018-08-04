package com.zhuanzhi.structuring.model;

import java.util.HashMap;

/**
 * 被审计对象
 * 	- 名称： 省民族宗教事务局
 * 	- 预算单位： 2
 * 	- 编制总人数： 75
 * 		- 行政编制： 30
 * 		- 全额补助事业编制： 45
 * 		- 实有人数：61
 * 	- 职能处室: 6
 * 		- 行政编制： 30
 * 		- 事业编制： 9
 * 		- 实有人数：42
 */
public class Underlying extends Model {
    public String auditName;
    public String budgetUnit;
    public String theTotalNumberOfPeople;
    public String Administrate;
    public String fullSubsidyCareeaStaffing;
    public String theActualNumber;
    public String functionsDepartment;
    public String fd_Administrate;
    public String fd_Careea;
    public String fd_theActualNumber;


    @Override
    public String toString() {
        return "Underlying{" +
            "auditName='" + auditName + '\'' +
            ", budgetUnit='" + budgetUnit + '\'' +
            ", theTotalNumberOfPeople='" + theTotalNumberOfPeople + '\'' +
            ", Administrate='" + Administrate + '\'' +
            ", fullSubsidyCareeaStaffing='" + fullSubsidyCareeaStaffing + '\'' +
            ", theActualNumber='" + theActualNumber + '\'' +
            ", functionsDepartment='" + functionsDepartment + '\'' +
            ", fd_Administrate='" + fd_Administrate + '\'' +
            ", fd_Careea='" + fd_Careea + '\'' +
            ", fd_theActualNumber='" + fd_theActualNumber + '\'' +
            '}';
    }

    public HashMap<String, String> getHashMap() {

        HashMap<String, String> hm = new HashMap<>();

        hm.put("auditName", this.auditName);
        System.err.println(this.auditName);
        hm.put("budgetUnit", this.budgetUnit);
        hm.put("theTotalNumberOfPeople", this.theTotalNumberOfPeople);
        hm.put("Administrate", this.Administrate);
        hm.put("fullSubsidyCareeaStaffing", this.fullSubsidyCareeaStaffing);
        hm.put("theActualNumber", this.theActualNumber);
        hm.put("functionsDepartment", this.functionsDepartment);
        hm.put("fd_Administrate", this.fd_Administrate);
        hm.put("fd_Careea", this.fd_Careea);
        hm.put("fd_theActualNumber", this.fd_theActualNumber);

        return hm;
    }
}
