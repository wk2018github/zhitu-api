"use strict";


const _host = $.getHost(); //获取服务器指定地址


var dbName = $.getParam('dbName'); //   数据库名称   string  @mock=db_kg
var host = $.getParam('host'); //   IP地址    string  @mock=localhost
var password = $.getParam('password'); //   密码  string  @mock=123456
var port = $.getParam('port'); //   端口  string  @mock=3306
var tableName = $.getParam('tableName'); // 表名称 string  @mock=zt_sys_dict
var user = $.getParam('user'); //
var charset = $.getParam('charset'); //
var databaseType = $.getParam('databaseType'); //
var description = $.getParam('description'); //
var name = $.getParam('name'); //
var onceFlag=true;

var colArrOD = [];
var colArrO = [];
//渲染右侧字段
$(function () {
    $.ajax({
        url: _host + '/zhitu-api/dataSet/queryTableColumn',
        type: 'post',
        async: false,
        contentType: 'application/json',
        data: JSON.stringify({
            dbName: dbName,
            host: host,
            password: password,
            port: port,
            tableName: tableName,
            user: user
        }),
        success: function success(res) {
            if (res.code == '000000') {
                var str = '';
                $.each(res.data, function (i, e) {
                    colArrOD.push(e);
                    str += '<span class="span-item" title="' + e + '">' + e + '</span>';
                });
                $('#flex-wrap').html(str);
            }
        }
    });
    var colArr = [{
        type: "checkbox",
        field: "id"
    }, {
        field: 'order-num',
        title: '序号',
        sort: false,
        type: 'number',
        templet: '#order-num', //序号自增模板
        width: 80
    }];
    $.each(colArrOD, function (i, v) {
        colArr.push({
            field: v,
            title: v,
            sort: false
        });
    });
    colArr.push({
        fixed: 'right',
        title: '操作',
        width: 150,
        align: 'center',
        fixed:"",
        toolbar: '#barDemo' //这里的toolbar值是模板元素的选择器
    });
    colArrO.push(colArr);
});
var table = layui.table;
var laypage = layui.laypage; //分页组件
var layer = layui.layer;
//执行渲染

var initTableData = function initTableData(_ref) {
    var curr = _ref.curr,
        limit = _ref.limit;

    $.ajax({
        type: "post",
        url: _host + "/zhitu-api/dataSet/queryTableData",
        //      url: '../../src/json/tableDataTest.json',
        contentType: 'application/json',
        data: JSON.stringify({
            dbName: dbName, //  数据库名称   string  @mock=db_kg
            host: host, //  IP  string  @mock=localhost
            password: password, //  密码  string  @mock=123456
            port: port, //  端口  string  @mock=3306
            tableName: tableName, //    表名称 string  @mock=zt_sys_dict
            user: user, //
            page: curr,
            rows: limit,
            projectId: ""
        }),
        success: function success(res) {
            if (res.code == "000000") {
                var tableData = []; //表格数据
                $.each(res.data.list, function (i, v) {
                    tableData.push(JSON.parse(v));
                });

                drawTable(tableData);
                if(onceFlag){
                    onceFlag=false;
                    initPager(res.data.total); //分页组件
                }
            }
        }
    });
};

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
            console.log(obj);
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

//绘制数据表格
var drawTable = function drawTable(data) {
    table.render({
        elem: '#table', //指定原始表格元素选择器（推荐id选择器）
        height: 460, //容器高度
        limit: 10,
        data: data,
        skin: 'nob' ,//行边框风格
        even: true, //开启隔行背景
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        cols: colArrO,
        done: function done(res, curr, count) {}

        //,…… //更多参数参考右侧目录：基本参数选项
    });
};

//绑定字段名选择
var arr = [],
    str = '';
$(".flex-wrap").on("click", ".span-item", function () {
    if ($(this).hasClass("chose")) {
        $(this).removeClass("chose");
        arr.pop($(this).text());
    } else {
        $(this).addClass("chose");
        arr.push($(this).text());
    }
    str = arr.join(",");
});

//确定
$('#sure-btn').click(function () {
    if ($('input[name="database-type"]:checked').val() == '0') {
        window.location.href = '../newData/thirdStep.html?uping=1';
    } else {
        $.ajax({
            url:_host + '/zhitu-api/dataSet/saveSqlDb',
            type:'post',
            contentType:'application/json',
            data:JSON.stringify({
                charset:charset,//  数据字符集   string  @mock=string
                columnNames:str,//  列名（多个列已逗号隔开）    string  @mock=string
                databaseType:databaseType,//    数据库类型   string
                dbName:dbName,//    数据库名    string  @mock=string
                description:description,//  数据集描述   string  @mock=string
                host:host,//    数据库地址   string  @mock=string
                name:name,//    数据集名称   string  @mock=string
                password:password,//    数据库连接密码 string  @mock=string
                port:port,//    数据库端口号  number  @mock=0
                projectId:'2121',// 项目id    string  @mock=string
                tableName:tableName,//  数据库表名   string  @mock=string
                user:user,//
            }),
            success:function (res) {
                if(res.code=='000000'){
                    window.location.href = 'userDataSet.html?dbName=' + dbName + '&host=' + host + '&password=' + password + '&port=' + port + '&tableName=' + tableName + '&user=' + user + '&charset=' + charset + '&databaseType=' + databaseType + '&columnNames=' + str;
                }
            }
        });
    }
});

//监听工具条
table.on('tool', function (obj) {
    //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
    var tr = obj.tr; //获得当前行 tr 的DOM对象
    console.log(data);

    if (layEvent === 'del') {
        //删除
        layer.confirm('是否删除该数据集?', function (index) {
            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            layer.close(index);
            //向服务端发送删除指令
        });
    } else if (layEvent === 'edit') {
        //编辑
        //do something

        //同步更新缓存对应的值
        obj.update({
            username: '123',
            title: 'xxx'
        });
    }
});