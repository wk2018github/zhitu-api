$.extend({
    /**
     * 获取地址栏参数
     *
     * */
    getParam: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return decodeURI(r[2]);
        return null; //返回参数值
    },
    
    //指定服务器host地址,方便反向代理.
    getHost:function(){
        var host = "";
        return host;
    }
});