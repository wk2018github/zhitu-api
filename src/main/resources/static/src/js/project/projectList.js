/*
本文件使用了Babel编译.
如果修改者没有配置类似的编译脚本
请将修改内容复制一份到 src/js/dataSet/ 目录下同名文件中
以免后续编译删除修改内容
 * */

"use strict";

var  _host = $.getHost(); //获取服务器指定地址

var pieData = []; //饼图数据


//饼图接口
var getPieData = function getPieData() {
    $.ajax({
        type: "post",
        url: _host+"/zhitu-api/dataSet/chartsByName",
        contentType: 'application/json',
        data: JSON.stringify({
            name: "",
            projectId: ""
        }),
        success: function success(res) {
            if (res.code == "000000") {
                //饼图数据
                $.each(res.data.data, function (i, v) {
                    pieData.push({
                        "y": v.cout,
                        "name": v.description
                    });
                });
                initPie(pieData);
            }
        }
    });
};

getPieData();

//饼图
var initPie = function initPie(data) {
    Highcharts.chart('container', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        credits: {
            enabled: false
        },
        title: {
            text: null
        },
        legend: {
            enabled: true,
            squareSymbol: true,
            //          itemDistance:80,
            symbolRadius: 0,
            align: "right",
            layout: "vertical",
            verticalAlign: "middle"
        },
        colors: ["#fcd257", "#f4746e", "#8a9ef2", "#9ce9f6"],
        exporting: {
            enabled: false
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: '数据集类型',
            colorByPoint: true,
            data: data
        }]
    });
};

//搜索
$("#keyWords").on("keydown", function (e) {
    if (e.keyCode == 13) {

    }
});

$("#search-icon").on("click", function (e) {

});
$(function () {
    $('.project-list>div').each(function (i, v) {
        //随机颜色
        var colorArr=['#6695f5','#f9bc34','#ff7734'];
        var index = Math.floor((Math.random()*colorArr.length));
        $(v).css('background-color',colorArr[index]);
    });
});
//删除
$('.body-left-scroll').on('click','.project-close',function () {
   $(this).closest('.project-list').remove();
});
// 添加
$('#add').click(function () {
    window.location.href='firstStep.html';
});