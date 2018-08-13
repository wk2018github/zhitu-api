/*
 * layui table中一些公用函数
 * 箭头函数
 * */
"use strict";

//将table中的操作转换成对应文本
var changeOptions = (type) => {
    switch(type) {
        case "101":
            return "<span class='see-details option'>查看详情</span>";
            break;
        case "102":
            return "<span class='edit option'>编辑</span><span class='del option'>删除</span>";
            break;
        case "103":
            return "<span class='see-details option'>查看详情</span>";
            break;
        default:
            return "<span class='see-details option'>查看详情</span>";
            break;
    }
};

//JS标准时间转换  2018-05-19T08:04:52.000+0000

var formatData = (str) => {
    let targetData = null;

    //  var time="2018-05-19T08:04:52.000+0000";
    var d = new Date(str);

    targetData = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();

    return targetData;
}