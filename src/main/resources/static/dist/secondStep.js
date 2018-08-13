$('#next').click(function () {
    var description=$.getParam('description');
    var name=$.getParam('name');
    if($('input[name="source"]:checked').val()=='本地文件'){
        window.location.href='thirdStep.html?source=1&name='+name+'&description='+description;
    }else {
        window.location.href='thirdStep.html?source=2&name='+name+'&description='+description;
    }
});