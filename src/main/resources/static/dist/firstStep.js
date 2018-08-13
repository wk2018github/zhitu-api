$('#next').click(function() {

    var _return_obj = $.validBeforeSubmit('modal-form');
    if(!_return_obj.flag) {
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                title: '提示'
                ,content: '名称和简介必填！'
            });
        });
        return false;
    }

    var description = $('#description').val();
    var name = $('#name').val();
    if($(this).hasClass('layui-btn-my-n')){
        window.location.href = 'dataSetList.html?description=' + description + '&name=' + name;
    }else if ($(this).hasClass('layui-btn-my-a')) {
        window.location.href = 'atlasRelation.html?description=' + description + '&name=' + name;
    }else if ($(this).hasClass('layui-btn-my-p')) {
        window.location.href = 'atlasList.html?description=' + description + '&name=' + name;
    }else {
        window.location.href = 'secondStep.html?description=' + description + '&name=' + name;
    }
});

$.validInit('modal-form');