var name=$.getParam('name');
var description=$.getParam('description');

var _host = $.getHost(); //获取服务器指定地址

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
var form;
layui.use('form', function() {
    form = layui.form; //高版本建议把括号去掉，有的低版本，需要加()
});

function renderForm() {
    form.render();
}
var ids=sessionStorage.getItem('ids');
    ids=JSON.parse(ids);
    // sessionStorage.removeItem('ids');
//    获取两个表
$.ajax({
    url:_host + '/zhitu-api/knowledge/selectByDataSetId',
    type:'post',
    contentType:'application/json',
    data:JSON.stringify({
        ids:ids,
    }),
    success:function (res) {
        if(res.code=='000000'){
            if ( res.data.data.length==1){
                $('#tableName1,#tableName2').html('<option value="'+res.data.data[0].id+'" selected>'+res.data.data[0].tableName+'</option>');
                var _html='';
                $.each(res.data.data[0].fileds,function (i, v) {
                    _html+='<option value="'+v+'">'+v+'</option>';
                });
                $('#fileds1,#fileds2').html(_html);
                renderForm();
            }else {
                var _html_1='';
                var _html_2='';
                $.each(res.data.data,function (i, v) {
                    if(i==0){
                        _html_1+='<option value="'+v.id+'" selected>'+v.tableName+'</option>';
                        _html_2+='<option value="'+v.id+'">'+v.tableName+'</option>';
                    }else {
                        _html_1+='<option value="'+v.id+'">'+v.tableName+'</option>';
                        _html_2+='<option value="'+v.id+'" selected>'+v.tableName+'</option>';
                    }
                });
                $('#tableName1').html(_html_1);
                $('#tableName2').html(_html_2);
                $.each(res.data.data,function (i, v) {
                    var _html='';
                    $.each(v.fileds,function (ii, vv) {
                        _html+='<option value="'+vv+'">'+vv+'</option>';
                    });
                    $('#fileds'+(i+1)).html(_html);
                });
                renderForm();
            }
        }
    }
});
loadRight();
function loadRight(){
    // 关系类型
    $.ajax({
        url:_host + '/zhitu-api/knowledge/queryTypes',
        type:'post',
        contentType:'application/json',
        data:JSON.stringify({
        }),
        success:function (res) {
            if(res.code=='000000'){
                var _html=''
                $.each(res.data,function (i, v) {
                    _html+='<span class="span-item">'+v+'</span>';
                });
                $('#relation-type').html(_html);
            }
        }
    });
// 节点标签
    $.ajax({
        url:_host + '/zhitu-api/knowledge/queryNodes',
        type:'post',
        contentType:'application/json',
        data:JSON.stringify({
        }),
        success:function (res) {
            if(res.code=='000000'){
                var _html=''
                $.each(res.data,function (i, v) {
                    _html+='<span class="span-item">'+v+'</span>';
                });
                $('#point-tag').html(_html);
            }
        }
    });
}
//添加至知识图谱

var form;
layui.use('form', function() {
    form = layui.form; //高版本建议把括号去掉，有的低版本，需要加()
});

$('#knowledge-add').click(function () {
    if($('#relationship').val()==''){
        layer.alert("请填写关系名", {
            title: "提示"
        });
    }else {
        $.ajax({
            url:_host + '/zhitu-api/knowledge/addToMap',
            type:'post',
            contentType:'application/json',
            data:JSON.stringify({
                relationship:$('#relationship').val(),//	关系	string	@mock=工作
                sourceId:$('#tableName1').val(),	//源知识表id	string	@mock=KN_1483797784
                sourceKey:$('#fileds1').val(),	//源外键	string	@mock=KN_1483797784
                targetId:$('#tableName2').val(),	//目标知识表id	string	@mock=KN_1483797784
                targetKey:$('#fileds2').val(),
            }),
            success:function (res) {
                if(res.code=='000000'){
                    layer.confirm("添加成功", {
                        title: "提示"
                    },function (index) {
                        layer.close(index);
                        loadRight();
                    });
                }else {
                    layer.alert("添加失败", {
                        title: "提示"
                    });
                }
            }
        });
    }
});
//组织右侧右击默认
$(".body-right-detail").on("contextmenu", ".span-item,.delete", function(){
    return false;
});
//右击事件
var _thiz;
$(".body-right-detail").on("mousedown", ".span-item",function(e) {
    //右键为3
    if (3 == e.which) {
        _thiz=$(this);
        var X = $(this).position().top+31;
        var Y = $(this).position().left+22;
        $('.delete').css({
            'left':Y,
            'top':X
        }).show();
    }
});
//点击删除
$(".body-right-detail").on("click", ".delete", function(e){
    e.stopPropagation();
    var url='';
    var data={};
    if(_thiz.parent().attr('id')=='point-tag'){
        url='/zhitu-api/knowledge/deleteNodes';
        data={node:_thiz.text()}
    }else {
        url='/zhitu-api/knowledge/deleteTypes';
        data={type:_thiz.text()}
    }
    $.ajax({
        url:_host + url,
        type:'post',
        contentType:'application/json',
        data:JSON.stringify(data),
        success:function (res) {
            if(res.code='000000'){
                layer.alert("删除成功", {
                    title: "提示"
                });
                _thiz.remove();
            }else {
                layer.alert("删除失败", {
                    title: "提示"
                });
            }
            $('.delete').hide();
        }
    })
});
$('body').click(function () {
    $('.delete').hide();
});