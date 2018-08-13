/* 首页  js文件 */
"use strict";
var _host = $.getHost(); //获取服务器指定地址
//顶部菜单点击事件
$(".redirect-menu").on("click", function() {
    //更改样式
    $(this).addClass("active");
    $(this).find("span").addClass("active");
    
    $(this).siblings().removeClass("active");
    $(this).siblings().find("span").removeClass("active");
    
    
    let _type = $(this).attr("data-type");
    let _url = ""; //指定路径
    switch(_type) {
        case "task":
            _url = "task/taskList.html";
            break;
        case "knowledge":
            _url = "newKnowledge/knowledgeList.html";
            break;
        case "data-set":
            _url = "dataSet/dataSetList.html";
            break;
        case "project":
            _url = "project/projectList.html";
            break;
        default:
            break;
    }

    $("#body-iframe").attr("src", _url);
});

//获取头像和账户名
$(function () {
    var userJsonStr = JSON.parse(sessionStorage.getItem('user'));
    if (userJsonStr.avatarUrl == ''||userJsonStr.avatarUrl ==null) {
        return false;
    } else {
        $('#personal img').attr('src', userJsonStr.avatarUrl);
    }
    $('#pesonal-name').text(userJsonStr.nickname);
});
//退出
$('#out').click(function () {
    $.ajax({
        url: _host + '/zhitu-api/user/loginOut',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({}),
        success: function success(res) {
            if (res.code == '000000') {
                window.location.href = 'login.html';
            }
        }
    });
});