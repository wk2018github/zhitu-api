
var _host = $.getHost(); //获取服务器指定地址

$('#save').click(function () {
    $.ajax({
        url:_host + '/zhitu-api/user/login',
        type:'post',
        contentType: 'application/json',
        data: JSON.stringify({
            email:$('#email').val(),//账号	string	@mock=admin@admin.com
            password:$('#password').val(),//
        }),
        success:function (res) {
            if(res.code=='000000'){
                var data=res.data.user;
                sessionStorage.setItem('user', JSON.stringify(data));
                window.location.href='index.html';
            }
        }
    })
});