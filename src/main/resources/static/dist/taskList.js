/*
   ES6
 * */

"use strict";

var _host = $.getHost(); //获取服务器指定地址

var table = layui.table;
var laypage = layui.laypage; //分页组件
var onceFlag = true; //初始分页组件一次
var fieldData = []; //表头数据

//执行渲染

var drawTable = function drawTable(data) {
    table.render({
        elem: '#table', //指定原始表格元素选择器（推荐id选择器）
        height: 430, //容器高度
        limit: 10,
        data: data,
        skin: 'nob' ,//行边框风格
        even: true, //开启隔行背景
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        cols: [[{
            field: 'order-num',
            title: '序号',
            sort: false,
            type: 'number',
            templet: '#order-num', //序号自增模板
            width: 80
        }, {
            title: '创建时间',
            templet: '#format-date', //序号自增模板
            sort: false
        }, {
            field: 'status',
            title: '状态',
            sort: false
        }]]
    });
};

var initTableData = function initTableData(_ref) {
    var curr = _ref.curr,
        limit = _ref.limit;

    $.ajax({
        type: "post",
        url: _host+"/zhitu-api/taskInfo/selectAllTask",
        contentType: 'application/json',
        data: JSON.stringify({
            page: curr,
            rows: limit
        }),
        success: function success(res) {
            if (res.code == "000000") {
                var tableData = []; //表格数据
                $.each(res.data.rows, function (i, v) {
                    tableData.push({
                        "id": v.id, //id
                        "name": v.name, //数据集名称
                        "status": v.status, //数据库表名
                        "description": v.description, //描述
                        "createTime": v.createTime, //创建时间
                        "projectId": v.projectId
                    });
                });

                drawTable(tableData);
                if (onceFlag) {
                    onceFlag = false;
                    initPager(res.data.records); //分页组件
                }
            }
        }
    });
};

//执行渲染
//绘制数据表格

//获取表格数据
initTableData({
    curr: 1,
    limit: 10
});

var initPager = function initPager(total) {
    laypage.render({
        elem: 'pager', //注意，这里的 test1 是 ID，不用加 # 号
        count: total, //数据总数，从服务端得到
        limit: 10,
        layout: ["prev", "page", "next", "first", "last", "skip"],
        jump: function jump(obj, first) {
            //首页不执行
            if (!first) {
                initTableData({
                    curr: obj.curr,
                    limit: 10
                });
            }
        }
    });
};