/*
 * @author 隋磊
 * @date 2018-6-28
 * @description   封装表单验证插件 基于jquery2.1.1
 * 		使用:初始化时传入表单项父级节点ID
 * 			  在需要进行表单验证的标签中加入 valid-type="" 属性.
 * 			 valid-type的值是需要验证的类型  支持多项验证 使用管道符号 | 分割
 * 			 例如 : valid-type="require|num|phone" 
 * 			 默认支持类型在正则对象__form_reg 中,如有拓展只需继续添加想要的验证即可
 * @return obj{}  flag 布尔类型 true时表示所有验证都合规. msg为提示信息.
 * */
;
(function($) {
    "use strict";

    $.extend({
        /*
         * @param {ele} 参数是表单外层ID
         * */
        validInit: function(ele) {

            //判断ID
            if($("#" + ele).length != 1) return console.error("Id不存在");
            var _parent = "#" + ele; //目标表单ID

            var nowElement = null, //当前表单项DOM节点对象
                needValidValue = null;
            //input 类型
            $(_parent + " input[valid-type]" + "," + _parent + " select[valid-type]" + "," + _parent + " textarea[valid-type]").each(function() {
                //判断 该表单有valid-special属性么 值为true
                if($(this).attr('valid-special') == "true") { //特殊类型验证
                    console.log($(this).attr('id'));
                } else {
                    // 常规表单失去焦点事件验证
                    $(this).on('blur', function() {
                        var validType = $(this).attr("valid-type");
                        var nowElement = $(this);
                        var nowElementLabel = $(this).attr("valid-key");
                        var needValidValue = $(this).val(); //每个表单项的值
                        if(validType != "" || validType != undefined) {
                            var typeArrys = validType.split("|"); //用户指定的验证类型
                            for(var i = 0; i < typeArrys.length; i++) {
                                if(typeArrys[i] in __form_reg) {
                                    var temp = typeArrys[i];
                                    var tempReg = __form_reg[temp].reg;
                                    if(!tempReg.test(needValidValue)) {

                                        doValid(nowElement, nowElementLabel, __form_reg[temp].msg);
                                        nowElement.css("border-color", "red");

                                        setTimeout(closeAlert, 500); //点击空白区域关闭弹窗
                                        break;
                                    } else {
                                        //合规后移除红边
                                        nowElement.css("border-color", "");
                                    }
                                }
                            }
                        }
                    });

                }

            });

        },
        //用于表单提交前验证
        validBeforeSubmit: function(ele) {
            //判断ID
            if($("#" + ele).length != 1) return console.error("Id不存在");
            var _parent = "#" + ele; //目标表单ID
            
            
            //方法返回的信息 flag true  表示所有的验证都合规
            var _obj = {
                flag: true,
                msg: ""
            };

            $(_parent + " input[valid-type]:visible" + "," + _parent + " select[valid-type]:visible" + "," + _parent + " textarea[valid-type]:visible").each(function() {
                var validType = $(this).attr("valid-type");
                var nowElementLabel = $(this).attr("valid-key");
                var nowElement = $(this);
                var needValidValue = $(this).val(); //每个表单项的值
                if(validType != "" || validType != undefined) {
                    var typeArrys = validType.split("|"); //用户指定的验证类型
                    for(var i = 0; i < typeArrys.length; i++) {
                        if(typeArrys[i] in __form_reg) {
                            var temp = typeArrys[i];
                            var tempReg = __form_reg[temp].reg;
                            if(!tempReg.test(needValidValue)) {
                                _obj.flag = false;
                                _obj.msg = nowElementLabel + __form_reg[temp].msg;
                                break;
                            } else {
                                //合规后移除红边
                                nowElement.css("border-color", "");
                            }
                        }
                    }
                }

            });

            return _obj;
        }

    });
    /*
     *插件私有函数.不对外暴露 
     * */

    //默认支持的正则
    var __form_reg = {
        //必填项
        require: {
            reg: /\S/,
            msg: "不能为空"
        },
        //非负整数
        num: {
            reg: /^-?\d+$/,
            msg: "整数格式不正确"
        },
        //ipv4格式IP
        ipv4: {
            reg: /^(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))$/,
            msg: "格式不正确"
        },
        //ipv6格式IP
        ipv6: {
            reg: /^([\\da-fA-F]{1,4}:){7}([\\da-fA-F]{1,4})$/,
            msg: "请输入IPV6格式的IP地址"
        },
        //手机号
        phone: {
            reg: /^[1][3,4,5,7,8][0-9]{9}$/,
            msg: "手机号码格式不正确"
        },
        //邮箱
        email: {
            reg: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            msg: "电子邮箱格式不正确"
        },
        //金额 默认保留两位小数
        money: {
            reg: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
            msg: "金额格式不正确"
        }
    }

    //提示验证信息
    function doValid(temp, temp_label, error_msg) {
        var _top = temp.position().top + temp.outerHeight() + 5;
        var _left = temp.position().left;
        var tip_template = '<div class="input-check" style="top: ' + _top + 'px;left:' + _left + 'px;">' +
            '                <span class="warning-icon">' +
            '                    <img src="../../src/img/input-warning.png" alt="warning">' +
            '                </span>' +
            '                <span class="tip-txt">' + temp_label + error_msg + '</span>' +
            '            </div>';
        $(tip_template).insertAfter(temp);
    }
    //关闭提示信息
    function closeAlert() {
        $(document).one('click', function() {
            $(".input-check").remove();
        })
    }

})(jQuery);