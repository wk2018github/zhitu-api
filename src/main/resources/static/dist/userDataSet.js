/*员工数据集*/
"use strict";

var _host = $.getHost(); //获取服务器指定地址

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var table = layui.table;
var laypage = layui.laypage; //分页组件
var layer = layui.layer;

var dbName = $.getParam('dbName'); //  数据库名称   string  @mock=db_kg
var host = $.getParam('host'); //  IP地址    string  @mock=localhost
var password = $.getParam('password'); //  密码  string  @mock=123456
var port = $.getParam('port'); //  端口  string  @mock=3306
var tableName = $.getParam('tableName'); //    表名称 string  @mock=zt_sys_dict
var user = $.getParam('user'); //
var charset = $.getParam('charset'); //
var databaseType = $.getParam('databaseType'); //
var columnNames = $.getParam('columnNames'); //
var id = $.getParam('id'); //
var source = $.getParam('source'); //
var source1 = $.getParam('source1'); //
var columnNamesA = columnNames.split(',');
var onceFlag = true;
//执行渲染

var initTableData = function initTableData(_ref) {
    var data=_ref.data,
        url=_ref.url;
        data.page=_ref.curr;
        data.rows=_ref.limit;
    $.ajax({
        type: "post",
        url: _host + url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function success(res) {
            if (res.code == "000000") {
                $('#location-parent').text(res.data.dataSetName);
                var tableData = []; //表格数据
                if(source1=='2'){
                    $.each(res.data.list, function (i, v) {
                        tableData.push(JSON.parse(v));
                    });
                }else {
                    tableData=res.data.rows;
                }
                drawTable(tableData);
                if (onceFlag) {
                    onceFlag = false;
                    if(source1=='2'){
                        initPager(res.data.total); //分页组件
                    }else {
                        initPager(res.data.records); //分页组件
                    }
                }
            }
        }
    });
};



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
                if(source1=='2'){
                    //获取表格数据
                    initTableData({
                        curr: obj.curr,
                        limit: 10,
                        url:'/zhitu-api/dataSet/queryTableColumnData',
                        data:{
                            charset: charset, //  字符集 string  @mock=utf-8
                            columnNames: columnNames, //选中的列用，分割 string  @mock=id,code,name
                            databaseType: databaseType, //    数据库类型   string  @mock=01
                            dbName: dbName, //    数据库名称   string  @mock=ldp_test
                            host: host, //    IP地址    string  @mock=192.168.100.111
                            password: password, //    密码  string  @mock=123456
                            port: port, //    端口  string  @mock=30620
                            tableName: tableName, //  表名  string  @mock=ldp_asset_object
                            user: user,
                            // page: 1,
                            // rows: 10,
                            projectId: "",
                            id:id
                        }
                    });
                }
                else {
                    //获取表格数据
                    initTableData({
                        curr: obj.curr,
                        limit: 10,
                        url:'/zhitu-api/dataSet/findByLocalTable',
                        data:{
                            // page: 1,
                            // rows: 10,
                            id:id
                        }
                    });
                }
                // initTableData({
                //     curr: obj.curr,
                //     limit: 10
                // });
            }
        }
    });
};
var colArrO = [];
$(function () {
    if(source=='1'){
        $('#location-target').text('非关系型');
    }else {
        $('#location-target').text('关系型');
        $('.add').hide();
    }

    if(source1=='2'){
        //获取表格数据
        initTableData({
            curr: 1,
            limit: 10,
            url:'/zhitu-api/dataSet/queryTableColumnData',
            data:{
                charset: charset, //  字符集 string  @mock=utf-8
                columnNames: columnNames, //选中的列用，分割 string  @mock=id,code,name
                databaseType: databaseType, //    数据库类型   string  @mock=01
                dbName: dbName, //    数据库名称   string  @mock=ldp_test
                host: host, //    IP地址    string  @mock=192.168.100.111
                password: password, //    密码  string  @mock=123456
                port: port, //    端口  string  @mock=30620
                tableName: tableName, //  表名  string  @mock=ldp_asset_object
                user: user,
                // page: 1,
                // rows: 10,
                projectId: "",
                id:id
            }
        });
    }
    else {
        //获取表格数据
        initTableData({
            curr: 1,
            limit: 10,
            url:'/zhitu-api/dataSet/findByLocalTable',
            data:{
                // page: 1,
                // rows: 10,
                id:id
            }
        });
    }

    var _colArr$push;

    var colArr = [{
        type: "checkbox",
        width: 48
    }, {
        field: 'order-num',
        title: '序号',
        sort: false,
        type: 'number',
        event: "click",
        templet: '#order-num', //序号自增模板
        width: 58
    }];
    $.each(columnNamesA, function (i, v) {
        colArr.push({
            field: v,
            title: v,
            sort: false
        });
    });
    if(source=='1') {
        colArr.push((_colArr$push = {
            fixed: 'right',
            title: '操作',
            width: 120,
            align: 'center'
        }, _defineProperty(_colArr$push, 'fixed', ""), _defineProperty(_colArr$push, 'toolbar', '#barDemo'), _colArr$push))
    };
    colArrO.push(colArr);
});
//绘制数据表格
var options;
var tableIns;
var drawTable = function drawTable(data) {
    options = {
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
    };
    tableIns = table.render(options);
};

//添加按钮绑定事件
$("button.add").on("click", function () {
    $(".option-con").css({
        "display": "block"
    });
    $(".table-con").css("right", "340px");
    tableIns.reload(options);
});

function hideOption() {
    $(".option-con").css({
        "display": "none"
    });
    $(".table-con").css("right", "10px");
    initTable();
}

//编辑
$('table').on("click", ".edit", function () {
    doDel();
});
//删除
$('table').on("click", ".del", function () {
    doDel();
});

//删除函数
function doDel() {
    layer.confirm('<span>ssss</span>确认删除该条信息？', {
        btn: ['删除', '取消'], //可以无限个按钮
        title: "删除"
    }, function (index, layero) {
        //按钮【按钮一】的回调
    }, function (index) {
        //按钮【按钮二】的回调
    });
}
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