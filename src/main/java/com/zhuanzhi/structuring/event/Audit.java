package com.zhuanzhi.structuring.event;

import com.zhuanzhi.structuring.model.*;

/**
 *  审计事件
 * 	- 审计活动 Activity
 * 	- 被审计对象 Underlying
 * 	- 审计实施情况 Enforcement
 * 	- 审计意见 Opinion
 * 	- 审计问题 Problem
 * 	- 审计建议 Advice
 */
public class Audit {

    public Activity activity;
    public Underlying underlying;
    public Enforcement enforcement;
    public Opinion opinion;
    public Problem problem;
    public Advice advice;

    @Override
    public String toString() {
        return "Audit{" +
            "activity=" + activity +
            ", underlying=" + underlying +
            ", enforcement=" + enforcement +
            ", opinion=" + opinion +
            ", problem=" + problem +
            ", advice=" + advice +
            '}';
    }
}
