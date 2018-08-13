var description = $.getParam('description');
var name = $.getParam('name');
var source = $.getParam('source');

var _host = $.getHost(); //获取服务器指定地址

$.validInit('modal-form');

layui.use('upload', function() {
    var $ = layui.jquery,
        upload = layui.upload;
    var demoListView = $('#demoList');
    upload.render({
        elem: '#drag-div',
        accept: 'file',
        url: _host + '/zhitu-api/dataSet/handleFileUpload',
        data: {
            describe: description, //	数据集描述	string	@mock=数据集描述
            // files:files,//	上传的文件集合	array<object>	上传的文件集合
            name: name, //	数据集名称	string	@mock=数据集名称
            projectId: 'ddddd', //
        },
        multiple: true,
        auto: false,
        bindAction: '#next',
        choose: function(obj) {
            $('#drag-div').hide();
            $('.layui-upload-list').show();
            var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function(index, file, result) {
                var tr = $(['<tr id="upload-' + index + '">', '<td>' + file.name + '</td>', '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                    // ,'<td>等待上传</td>'
                    , '<td>'
                    // ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>', '</td>', '</tr>'
                ].join(''));

                // //单个重传
                // tr.find('.demo-reload').on('click', function(){
                //     obj.upload(index, file);
                // });

                //删除
                tr.find('.demo-delete').on('click', function() {
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    // uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });

                demoListView.append(tr);
            });
        },
        before: function(obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            $('#step-content1').hide();
            $('#step-content11').show();
            $('#next').hide();
            $('#compD').show();
        },
        allDone: function(obj) {
            if(obj.aborted == 0) { //上传成功
                $('#step-content11').hide();
                $('#step-content12').show();
                $('#compD').hide();
                $('#compC').show();
                // var tr = demoListView.find('tr#upload-'+ index),tds = tr.children();
                // tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                // tds.eq(3).html(''); //清空操作
                // return delete this.files[index]; //删除文件队列已经上传成功的文件
            } else {
                window.location.href = 'failed.html';
            }
            // this.error(index, upload);
        },
        error: function(index, upload) {
            window.location.href = 'failed.html';
            // var tr = demoListView.find('tr#upload-'+ index),tds = tr.children();
            // tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
            // tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
        }
    });
});
var dbName = $('#dbName').val(); //	数据库名称	string	@mock=db_kg
var host = $('#host').val(); //	IP地址	string	@mock=localhost
var password = $('#password').val(); //	密码	string	@mock=123456
var port = $('#port').val(); //	端口	string	@mock=3306
var user = $('#user').val(); //
var charset = $('#charset').val(); //
$(function() {
    if(source == 2) {
        $('#step-content2').show();
        $('#step-content1').hide();
        $('#next').click(function() {

            var _return_obj = $.validBeforeSubmit('modal-form');
            if(!_return_obj.flag) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.open({
                        title: '提示'
                        ,content: '所有都必填！'
                    });
                });
                return false;
            }

            window.location.href = '../dataSet/mangerDataSet.html?dbName=' + dbName + '&host=' + host + '&password=' + password + '&port=' + port + '&user=' + user + '&tableName=' + tableName + '&charset=' + charset + '&databaseType=' + databaseType + '&name=' + name + '&description=' + description+'&source='+source;
        });
        //获取数据库类型
        $.ajax({
            url: _host + '/zhitu-api/dataSet/queryDBType',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify({}),
            success: function(res) {
                if(res.code == '000000') {
                    var str = '<option value="">请选择数据库类型</option>';
                    $.each(res.data, function(i, e) {
                        str += '<option value="' + e.name + '">' + e.name + '</option>'
                    });
                    $("#databaseType").html(str);
                    //重新渲染
                    renderForm();
                }
            }
        });
    } else if($.getParam('uping') == 1) {
        $('#step-content11').show();
        $('#step-content1').hide();
        $('#next').hide();
        $('#compD').show();

    //    判断跳转到详情页
        function link() {
            $.ajax({
                url: ' /zhitu-api/taskInfo/selectById',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify({
                    taskId: $.getParam('taskId')
                }),
                success: function success(res) {
                    if (res.code == '000000') {
                        if(res.data.data.status=='1'){
                            setTimeout(link,5000);
                        }else if(res.data.data.status=='2'){
                            window.location.href = '../dataSet/userDataSet.html?dbName=' + $.getParam('dbName') + '&host=' + $.getParam('host') + '&password=' + $.getParam('password') + '&port=' + $.getParam('port') + '&tableName=' + $.getParam('tableName') + '&user=' + $.getParam('user') + '&charset=' + $.getParam('charset') + '&databaseType=' + $.getParam('databaseType') + '&columnNames=' + $.getParam('columnNames') + '&id=' + $.getParam('id') + '&source=' + source + '&source1=1';
                        }else if(res.data.data.status=='3'){
                            window.location.href = 'failed.html';
                        }
                    }
                }
            });
        }
        link();

    }
});
$('#compC').click(function() {
    window.location.href = '../dataSet/dataSetList.html';
});

var form;
layui.use('form', function() {
    form = layui.form; //高版本建议把括号去掉，有的低版本，需要加()
});

function renderForm() {
    form.render();
}
var databaseType = '';
form.on('select(databaseType)', function(data) {
    databaseType = data.value;
});
var tableName = '';
form.on('select(tableName)', function(data) {
    tableName = data.value;
});
// 获取数据表名
$('#tableNameOut').on('click', '.layui-form-select', function() {
    $.ajax({
        url: _host + '/zhitu-api/dataSet/queryDBTables',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            charset: $('#charset').val(), //	字段集编码	string	@mock=utf-8
            databaseType: databaseType, //	数据库类型	string	@mock=mysql
            dbName: $('#dbName').val(), //	数据库名称	string	@mock=ldp_test
            host: $('#host').val(), //	IP地址	string	@mock=192.168.100.111
            password: $('#password').val(), //	密码	string	@mock=123456
            port: $('#port').val(), //	端口	string	@mock=30620
            user: $('#user').val(), //
        }),
        success: function(res) {
            var str = '<option value="">直接选择或搜索选择数据表名</option>'
            $.each(res.data, function(i, e) {
                str += '<option value="' + e + '">' + e + '</option>'
            });
            $("#tableName").html(str);
            //重新渲染
            renderForm();
            $('#tableNameOut .layui-form-select').addClass('layui-form-selected');
            $('#tableNameOut .layui-form-select').addClass(' layui-form-selectup');
        }
    });
});