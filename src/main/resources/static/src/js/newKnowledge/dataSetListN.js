var _host = $.getHost(); //获取服务器指定地址

var name=$.getParam('name');
var description=$.getParam('description');

var onceFlag = true;
var laypage = layui.laypage; //分页组件
var initPager = function initPager(data) {
    laypage.render({
        elem: 'pager', //注意，这里的 test1 是 ID，不用加 # 号
        count: data, //数据总数，从服务端得到
        limit: 8,
        layout: ["prev", "page", "next", "first", "last", "skip"],
        jump: function jump(obj, first) {
            console.log(obj);
            //首页不执行
            if (!first) {
                initTableData({
                    curr: obj.curr,
                    limit: 8,
                    name:''
                });
            }
        }
    });
};
//进入数据集详情页
var datasetId='';
$('#data-set-list').on('click','li',function () {
    if($(this).attr('data-type')=='ftp_file'){
        window.location.href='dataSetDetails.html?id='+$(this).attr('data-id')+'&description='+description+'&name='+name;
    }else {
        $(this).find('.data-per').addClass('active');
        $(this).siblings('li').find('.data-per').removeClass('active');
        datasetId=$(this).attr('data-id');
    }
});
//加载数据集列表
function initTableData(obj){
    var curr=obj.curr,
        limit=obj.limit,
        name=obj.name;
    $.ajax({
        url:_host+'/zhitu-api/dataSet/queryDateSet',
        type:'post',
        contentType:'application/json',
        data:JSON.stringify({
            name:name,//	项目名称（默认为空或不传）	string	@mock=项目名称
            page:curr,//		number	@mock=1
            rows:limit
        }),
        success:function (res) {
            if(res.code=='000000'){
                drawTable(res.data.rows);
                if (onceFlag) {
                    onceFlag = false;
                    initPager(res.data.records);
                }
            }
        }
    });
}
function drawTable(data) {
    var _html='';
    $.each(data,function (i,v) {
        _html+='<li class="layui-col-md6" data-id="'+v.id+'" data-type="'+v.typeId+'">' +
            '    <div class="data-per">' +
            '        <h4><span class="title">'+v.name+'</span><span class="clock">'+formatData(v.createTime)+'</span></h4>' +
            '        <p title="'+v.description+'">'+v.description+'</p>' +
            '    </div>' +
            '</li>'
    });
    $('#data-set-list').html(_html);
}
initTableData({
    curr: 1,
    limit: 8,
    name:''
});
$('#next').click(function () {
    if(datasetId==''){
        layer.alert('请选择数据集');
    }else {
        $.ajax({
            url:_host+'/zhitu-api/knowledge/saveKnowledge',
            type:'post',
            contentType:'application/json',
            data:JSON.stringify({
                datasetId:datasetId,
                name:name,
                description:description
            }),
            success:function (res) {
                if (res.code=='000000'){
                    window.location.href='knowledgeList.html';
                }
            }
        })
    }
});
//搜索
$('#search-icon').click(function () {
    onceFlag=true;
    initTableData({
        curr: 1,
        limit: 8,
        name:$('#keyWords').val()
    });
});