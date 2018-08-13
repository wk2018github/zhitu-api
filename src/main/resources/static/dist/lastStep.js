$('body').on('click','#compC',function () {
    window.location.href='knowledgeList.html';
});
$(function () {
    // function link() {
    //     $.ajax({
    //         url: ' /zhitu-api/taskInfo/selectById',
    //         type: 'post',
    //         contentType: 'application/json',
    //         data: JSON.stringify({
    //             taskId: $.getParam('taskId')
    //         }),
    //         success: function success(res) {
    //             if (res.code == '000000') {
    //                 if(res.data.data.status=='1'){
    //                     setTimeout(link,5000);
    //                 }else if(res.data.data.status=='2'){
    //                     $('#step-content12').show();
    //                     $('#step-content11').hide();
    //                 }else if(res.data.data.status=='3'){
    //                     window.location.href = 'failed.html';
    //                 }
    //             }
    //         }
    //     });
    // }
    // link();
});