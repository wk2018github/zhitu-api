/*
    使用了Babel编译.如果修改人电脑没有配置 请在dist和本文件同时更改  以免编译后被清空
 * */

"use strict";

var _host = $.getHost(); //获取服务器指定地址

var table = layui.table;
var laypage = layui.laypage; //分页组件
var onceFlag = true; //初始分页组件一次
/*使用 splice()函数向数组中间填表头信息*/
var fieldData = [{
    field: 'order-num',
    title: '序号',
    sort: false,
    type: 'number',
    templet: '#order-num', //序号自增模板
    width: 80
}, {
    fixed: 'right',
    title: '操作',
    width: 150,
    align: 'center',
    toolbar: '#barDemo' //这里的toolbar值是模板元素的选择器
}]; //表头数据
var fieldFlag = true; //是否获取表头数据 只一次

//执行渲染

var queryDataJson = sessionStorage.getItem('queryData');
sessionStorage.removeItem("queryData"); //移除session信息
var queryData = JSON.parse(queryDataJson);

var drawTable = function drawTable(data) {
    table.render({
        elem: '#table', //指定原始表格元素选择器（推荐id选择器）
        height: 430, //容器高度
        limit: 10,
        data: data,
        skin: 'nob' ,//行边框风格
        even: true, //开启隔行背景
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        cols: [fieldData]
    });
};

var initTableData = function initTableData(_ref) {
    var curr = _ref.curr,
        limit = _ref.limit;

    $.ajax({
        type: "post",
        url: _host+"/zhitu-api/knowledge/selectByDatasetId",
        contentType: 'application/json',
        data: JSON.stringify({
            page: curr,
            rows: limit,
            datasetId: queryData.datasetId

        }),
        success: function success(res) {
            if (res.code == "000000") {
                var tableData = []; //表格数据

                if (res.data.rows.length > 0) {
                    $.each(res.data.rows, function (i, v) {
                        var tempObj = {};
                        for (var s in res.data.rows[0]) {
                            if (fieldFlag) {
                                //表格列数不确定 所以需要遍历取表头
                                //                              if(s.indexOf("Time") != -1 || s.indexOf("Data") != -1) {
                                //                                  fieldData.splice(fieldData.length / 2, 0, {
                                //                                      title: s,
                                //                                      templet: '#format-date' //序号自增模板
                                //                                  });
                                //                              } else {
                                //                                  fieldData.splice(fieldData.length / 2, 0, {
                                //                                      field: s,
                                //                                      title: s
                                //                                  });
                                //                              }

                                fieldData.splice(fieldData.length / 2, 0, {
                                    field: s,
                                    title: s
                                });
                            }
                            tempObj[s] = v[s];
                        }
                        fieldFlag = false;
                        tableData.push(tempObj);
                    });
                }

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