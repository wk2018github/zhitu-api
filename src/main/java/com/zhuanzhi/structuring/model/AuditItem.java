package com.zhuanzhi.structuring.model;


import java.util.ArrayList;

/**
 * 款项
 * - 项目名  收入预算总额
 * - 项目值  1000万
 * - 子项    其中
 */
public class AuditItem {
    public String name;
    public String value;
    public ArrayList<AuditItem> childs;

    public AuditItem(String name, String value) {
        this.name = name;
        this.value = value;
        this.childs = new ArrayList<AuditItem>();
    }

    @Override
    public String toString() {
        return "AuditItem{" +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            ", childs=" + childs +
            '}';
    }
}
