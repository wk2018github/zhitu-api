/*
本文件使用了Babel编译.
如果修改者没有配置类似的编译脚本
请将修改内容复制一份到 src/js/dataSet/ 目录下同名文件中
以免后续编译删除修改内容
 * */

"use strict";

const _host = $.getHost(); //获取服务器指定地址

var table = layui.table; //数据表格组件
var laypage = layui.laypage; //分页组件
let onceFlag = true; //初始分页组件一次
let pieData = []; //饼图数据

//执行渲染

var initTableData = ({
    curr,
    limit,
    keyWords
}) => {
    $.ajax({
        type: "post",
        url: _host + "/zhitu-api/dataSet/queryDateSet",
        contentType: 'application/json',
        data: JSON.stringify({
            page: curr,
            rows: limit,
            name: keyWords,
            projectId: ""
        }),
        success: function(res) {
            //          drawTable(res.data);
            if(res.code == "000000") {
                //饼图数据
                let tableData = []; //表格数据
                $.each(res.data.rows, function(i, v) {
                    tableData.push({
                        "id": v.id, //id
                        "name": v.name, //数据集名称
                        "typeDesc": v.typeDesc, //类型名称
                        "typeId": v.typeId, //类型ID
                        "dataTable": v.dataTable, //数据库表名
                        "description": v.description, //描述
                        "createTime": v.createTime, //创建时间
                        "rdbId": v.rdbId
                    });
                });

                drawTable(tableData);
                if(onceFlag) {
                    onceFlag = false;
                    initPager(res.data.records); //分页组件
                }

            }

        }
    });
}

//获取表格数据
initTableData({
    curr: 1,
    limit: 10,
    keyWords: ""
});

var initPager = (total) => {
    laypage.render({
        elem: 'pager', //注意，这里的 test1 是 ID，不用加 # 号
        count: total, //数据总数，从服务端得到
        limit: 10,
        layout: ["prev", "page", "next", "first", "last", "skip"],
        jump: function(obj, first) {
            console.log(obj);
            //首页不执行
            if(!first) {
                initTableData({
                    curr: obj.curr,
                    limit: 10
                })
            }

        }
    });
}

//绘制数据表格
var drawTable = (data) => {
    table.render({
        elem: '#table', //指定原始表格元素选择器（推荐id选择器）
        height: 430, //容器高度
        limit: 10,
        data: data,
        skin: 'nob' ,//行边框风格
        even: true, //开启隔行背景
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        cols: [
            [{
                    type: "checkbox",
                    field: "id"
                },
                {
                    field: 'order-num',
                    title: '序号',
                    sort: false,
                    type: 'number',
                    event: "visualPage",
                    templet: '#order-num', //序号自增模板
                    width: 80
                }, {
                    field: 'name',
                    event: "visualPage",
                    title: '数据集名'
                }, {
                    field: 'typeDesc',
                    event: "visualPage",
                    title: '类型',
                    sort: false
                }, {
                    field: 'description',
                    event: "visualPage",
                    title: '描述',
                    sort: false
                },
                {
                    title: '创建时间',
                    event: "visualPage",
                    templet: '#format-date', //序号自增模板
                    sort: false
                }, {
                    fixed: 'right',
                    title: '操作',
                    width: 150,
                    align: 'center',
                    toolbar: '#barDemo'
                } //这里的toolbar值是模板元素的选择器
            ]
        ],
        done: function(res, curr, count) {

        }

        //,…… //更多参数参考右侧目录：基本参数选项
    });
}

//饼图接口
var getPieData = () => {
    $.ajax({
        type: "post",
        url: _host + "/zhitu-api/dataSet/chartsByName",
        contentType: 'application/json',
        data: JSON.stringify({
            name: "",
            projectId: ""
        }),
        success: function(res) {
            if(res.code == "000000") {
                //饼图数据
                $.each(res.data.data, function(i, v) {
                    pieData.push({
                        "y": v.cout,
                        "name": v.description
                    });
                });
                initPie(pieData);

            }

        }
    });
}

getPieData();

//饼图
var initPie = (data) => {
    Highcharts.chart('container', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        credits: {
            enabled: false
        },
        title: {
            text: null
        },
        legend: {
            enabled: true,
            squareSymbol: true,
            //          itemDistance:80,
            symbolRadius: 0,
            align: "right",
            layout: "vertical",
            verticalAlign: "middle"
        },
        colors: ["#fcd257", "#f4746e", "#8a9ef2", "#9ce9f6"],
        exporting: {
            enabled: false
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: '数据集类型',
            colorByPoint: true,
            data: data
        }]
    });
}

//绑定操作事件
//可视化

var visualPage = (dataSetId) => {
    console.log(dataSetId);
    if(dataSetId != null && dataSetId != '') {
        $.ajax({
            type: "post",
            url: _host + "/zhitu-api/dataSet/findByTableValue",
            contentType: 'application/json',
            data: JSON.stringify({
                id: dataSetId
            }),
            success: function(res) {
                if(res.code == "000000") {
                    let _html = '';

                    $.each(res.data.data.fileds, function(i, v) {
                        _html += '<span class="span-item">' + v + '</span>';
                    });

                    $("#chose-field").html(_html);

                    //渲染数据
                    $("#msg-total").text(res.data.data.count);

                    //切换DIV
                    $("#list-normal").css("display", "none");
                    $("#list-detail").css("display", "flex");
                }
            }
        });
    }

}

//删除(批量删除)
var realDel = (arrs) => {
    $.ajax({
        type: "post",
        url: _host + "/zhitu-api/dataSet/deleteById",
        //      url: '../../src/json/tableDataTest.json',
        contentType: 'application/json',
        data: JSON.stringify({
            ids: arrs
        }),
        success: function(res) {
            if(res.code == "000000") {
                layer.confirm('删除成功', function(index) {
                    layer.close(index);
                    initTableData({
                        curr: 1,
                        limit: 10,
                        keyWords: ""
                    });
                });
            }

        }
    });

}

//编辑
var editDataSet = (data = {
    id,
    name,
    description
}, callback) => {
    $.ajax({
        type: "post",
        url: _host + "/zhitu-api/dataSet/updateDataSet",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(res) {
            if(res.code == "000000") {
                callback();
            }
        }
    });
}

//绑定字段名选择
$(".flex-wrap").on("click", ".span-item", function() {
    if($(this).hasClass("chose")) {
        $(this).removeClass("chose");
    } else {
        $(this).addClass("chose");
    }
});

//新建数据集

$("#add").on("click", function() {
    window.location.href = "../newData/firstStep.html";
});

//批量删除操作
$("#doDel").on("click", function() {
    const checkStatus = table.checkStatus('table'); //Array
    if(checkStatus.data.length == 0) {
        layer.alert("至少选择一条数据集", {
            title: "提示"
        });
        return false;
    }
    layer.confirm('是否删除选中的数据集?', {
        title: "批量删除"
    }, function(index) {
        layer.close(index);
        //向服务端发送删除指令
        let ids = [];
        for(let t of checkStatus.data) {
            ids.push(t.id);
        }
        realDel(ids); //typeof Array
    });

});

//监听工具条
table.on('tool', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
    var tr = obj.tr; //获得当前行 tr 的DOM对象
    //console.log(data);
    if(layEvent === 'visualPage') {
        $("#msg-type").text(data.typeDesc);
        visualPage(data.id); //单击可视化
    } else if(layEvent === 'details') {
        let queryData = {
            id: data.id,
            dataTable: data.dataTable,
            rdbId: data.rdbId,
            typeId: data.typeId
        }
        sessionStorage.setItem("queryData", JSON.stringify(queryData)); //初始化页面数据存在session中

        window.location.href = "dataSetDetails.html";
    } else if(layEvent === 'del') { //删除
        layer.confirm('是否删除该数据集?', {
            title: "删除"
        }, function(index) {
            //          obj.del(); //删除对应行（tr）的DOM结构，并更新缓存  
            layer.close(index);
            //向服务端发送删除指令
            realDel([data.id]); //typeof Array
        });
    } else if(layEvent === 'edit') { //编辑
        //弹出层编辑
        const _html = '<div class="diy-layer-wrap"><div class="form-wrap"><div class="edit-item">' +
            '<label for="dataset-name">数据集名:</label><input type="text" class="layui-input" id="dataset-name" value="" /></div> ' +
            '<div class="edit-item"><label for="dataset-name">描述:</label><textarea class="layui-textarea" id="description"></textarea></div> </div></div>';

        layer.open({
            type: 1,
            title: false, //不显示标题栏
            closeBtn: false,
            area: '500px;',
            shade: 0.3,
            id: 'LAY_layuipro', //设定一个id，防止重复弹出
            btn: ['保存', '取消'],
            yes: function(index, layero) {
                let _data = {
                    id: data.id,
                    name: $("#dataset-name").val(),
                    description: $("#description").val()
                }
                editDataSet(_data, () => {
                    obj.update({
                        name: $("#dataset-name").val(),
                        description: $("#description").val()
                    });
                    layer.close(index);
                });

            },
            btn2: function(index, layero) {
                //按钮【按钮二】的回调

                //return false 开启该代码可禁止点击该按钮关闭
            },
            btnAlign: 'c',
            content: _html,
            moveType: 1 //拖拽模式，0或者1

        });

        //同步更新缓存对应的值

    }
});

//搜索
$("#keyWords").on("keydown", function(e) {
    if(e.keyCode == 13) {
        onceFlag = true; //初始分页组件一次

        initTableData({
            curr: 1,
            limit: 10,
            keyWords: $("#keyWords").val()
        });
    }
});

$("#search-icon").on("click", function(e) {
    onceFlag = true; //初始分页组件一次

    initTableData({
        curr: 1,
        limit: 10,
        keyWords: $("#keyWords").val()
    });
});