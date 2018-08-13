/*
    使用了Babel编译.如果修改人电脑没有配置 请在dist和本文件同时更改  以免编译后被清空
 * */

// "use strict";

//var _host = $.getHost(); //获取服务器指定地址
//
// var table = layui.table;
// var laypage = layui.laypage; //分页组件
// var onceFlag = true; //初始分页组件一次
// var fieldData = []; //表头数据
// //执行渲染
//
// var queryDataJson = sessionStorage.getItem('queryData');
// sessionStorage.removeItem("queryData"); //移除session信息
// var queryData = JSON.parse(queryDataJson);
//
// //获取表头数据
// var getFieldData = function getFieldData(dataSetId) {
//     console.log(dataSetId);
//     if (dataSetId != null && dataSetId != '') {
//         $.ajax({
//             type: "post",
//             url: _host+"/zhitu-api/dataSet/findByTableValue",
//             contentType: 'application/json',
//             async: false,
//             data: JSON.stringify({
//                 id: dataSetId
//             }),
//             success: function success(res) {
//                 if (res.code == "000000") {
//                     //序号
//                     fieldData.push({
//                         field: 'order-num',
//                         title: '序号',
//                         sort: false,
//                         type: 'number',
//                         templet: '#order-num', //序号自增模板
//                         width: 80
//                     });
//
//                     $.each(res.data.data.fileds, function (i, v) {
//                         if (v.indexOf("Time") != -1 || v.indexOf("Data") != -1) {
//                             fieldData.push({
//                                 title: v,
//                                 templet: '#format-date' //序号自增模板
//                             });
//                         } else {
//                             fieldData.push({
//                                 field: v,
//                                 title: v
//                             });
//                         }
//                     });
//                 }
//             }
//         });
//     }
// };
//
// getFieldData(queryData.id);
//
// var drawTable = function drawTable(data) {
//     table.render({
//         elem: '#table', //指定原始表格元素选择器（推荐id选择器）
//         height: 430, //容器高度
//         limit: 10,
//         data: data,
//         cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
//         cols: [fieldData]
//     });
// };
//
// var initTableData = function initTableData(_ref) {
//     var curr = _ref.curr,
//         limit = _ref.limit;
//
//     $.ajax({
//         type: "post",
//         url: _host+"/zhitu-api/dataSet/findById",
//         contentType: 'application/json',
//         data: JSON.stringify({
//             page: curr,
//             rows: limit,
//             id: queryData.id,
//             dataTable: queryData.dataTable,
//             rdbId: queryData.rdbId,
//             typeId: queryData.typeId
//         }),
//         success: function success(res) {
//             if (res.code == "000000") {
//                 var tableData = []; //表格数据
//                 if (res.data.rows.length > 0) {
//                     $.each(res.data.rows, function (i, v) {
//                         var tempObj = {};
//                         for (var s in res.data.rows[0]) {
//                             tempObj[s] = v[s];
//                         }
//                         tableData.push(tempObj);
//                     });
//                 }
//
//                 drawTable(tableData);
//                 if (onceFlag) {
//                     onceFlag = false;
//                     initPager(res.data.records); //分页组件
//                 }
//             }
//         }
//     });
// };
//
// //执行渲染
// //绘制数据表格
//
// //获取表格数据
// initTableData({
//     curr: 1,
//     limit: 10
// });
//
// var initPager = function initPager(total) {
//     laypage.render({
//         elem: 'pager', //注意，这里的 test1 是 ID，不用加 # 号
//         count: total, //数据总数，从服务端得到
//         limit: 10,
//         layout: ["prev", "page", "next", "first", "last", "skip"],
//         jump: function jump(obj, first) {
//             //首页不执行
//             if (!first) {
//                 initTableData({
//                     curr: obj.curr,
//                     limit: 10
//                 });
//             }
//         }
//     });
// };

//预览跳转
$('#scan').click(function () {
    window.location.href='scanning.html'
});