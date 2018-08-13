var _host = $.getHost(); //获取服务器指定地址

$('body').on('click','embed',function () {
    alert(111)
});
$('.to-left').click(function () {
    $('.layui-col-md2').hide();
    var num='layui-col-md'+(parseInt($('.layui-col-md2').next().attr('class').split('md')[1])+2);
    $('.layui-col-md2').next().attr('class',num)
});
$('.to-right').click(function () {
    $('.layui-col-md3').hide();
    var num='layui-col-md'+(parseInt($('.layui-col-md3').prev().attr('class').split('md')[1])+3);
    $('.layui-col-md3').prev().attr('class',num)
});