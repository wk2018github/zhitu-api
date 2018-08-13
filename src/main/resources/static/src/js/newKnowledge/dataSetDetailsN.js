"use strict";
var _host = $.getHost(); //获取服务器指定地址

var name=$.getParam('name');
var description=$.getParam('description');

var table = layui.table;
var laypage = layui.laypage; //分页组件
var onceFlag = true; //初始分页组件一次

//执行渲染
//绘制数据表格
var drawTable = function drawTable(data) {
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
                field: 'order-num',
                title: '序号',
                sort: false,
                type: 'number',
                templet: '#order-num', //序号自增模板
                width: 80
            },{
                field: 'fileName',
                title: '文件名称',
                sort: false
            },{
                field: 'ftpurl',
                title: '上传地址',
                sort: false
            } , {
                title: '上传时间',
                templet: '#format-date', //序号自增模板
                sort: false
            }]
        ],
    });
};

var initTableData = function initTableData(_ref) {
    var curr = _ref.curr,
        limit = _ref.limit;

    $.ajax({
        type: "post",
        url: _host+"/zhitu-api/ftpFile/queryFtpFile",
        contentType: 'application/json',
        data: JSON.stringify({
            id:$.getParam('id'),//	数据集id	string
            page: curr,
            rows: limit,
        }),
        success: function success(res) {
            if (res.code == "000000") {
                $('#location-parent').text(res.data.dataSetName);
                var tableData = []; //表格数据
                if (res.data.rows.length > 0) {
                    $.each(res.data.rows, function (i, v) {
                        tableData.push(v);
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


var form;
layui.use('form', function() {
    form = layui.form; //高版本建议把括号去掉，有的低版本，需要加()
});

function renderForm() {
    form.render();
}
var knowledgeChoice = '';
$(function () {
    //获取知识抽取器
    $.ajax({
        url:_host+'/zhitu-api/dict/findKnowledge',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({}),
        success: function(res) {
            if(res.code == '000000') {
                var str = '<option value="">请选择数据库类型</option>';
                $.each(res.data.data, function(i, e) {
                    str += '<option value="' + e.key + '">' + e.value + '</option>'
                });
                $("#knowledge-choice").html(str);
                //重新渲染
                renderForm();
            }
        }
    });
    form.on('select(knowledgeChoice)', function(data) {
        knowledgeChoice = data.value;
        // 获取槽位
        $.ajax({
            url: _host+'/zhitu-api/dict/findByCodeAndValue',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify({
                parentValue:knowledgeChoice
            }),
            success: function(res) {
                if(res.data.data.length>0){
                    var str = ''
                    $.each(res.data.data, function(i, e) {
                        str += '<input type="checkbox" name="" data-key="'+e.key+'" lay-skin="primary" title="'+e.name+'">'
                    });
                    $("#slot").html(str);
                    //重新渲染
                    renderForm();
                }
            }
        });
    });
});

//知识抽取跳转
$('#knowledge-get').click(function () {
    var keys=[];
    $('.layui-form input[type=checkbox]').each(function (i, v) {
        if($(v).is(":checked")){
            keys.push($(v).attr('data-key'));
        }
    });
    if(keys.length==0||knowledgeChoice==''){
        layer.alert('请选择知识抽取器和槽位', {
            title: "提示"
        });
    }else {
        $.ajax({
            url: _host+'/zhitu-api/knowledge/extractKnowledge',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify({
                audit:keys,//	槽位 key	array<string>	@mock=$order('activity','underlying','enforcement','opinion','problem','advice')
                datasetId:$.getParam('id'),//	数据集 id	string	@mock=DATASET_1533546327728
                description:description,//	知识库描述	string	@mock=测试描述
                key:knowledgeChoice,//	提取器key	string	@mock=01
                name:name,
            }),
            success: function success(res) {
                if (res.code == '000000') {
                    window.location.href='knowledgeList.html';
                }else {
                    layer.alert(res.message, {
                        title: "提示"
                    });
                }
            }
        });
    }
    // window.location.href='lastStep.html?description='+description+'&name='+name;
});