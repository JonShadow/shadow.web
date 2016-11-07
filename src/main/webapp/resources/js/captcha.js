var InterValObj; //timer变量，控制时间  
var count = 60; //间隔函数，1秒执行  
var curCount;//当前剩余秒数  
var code = ""; //验证码  
var codeLength = 6;//验证码长度  
  
function sendCaptcha() {  
    curCount = count;  
    var phone = $("#reg-phone").val();  
    var phoneTip = $("#reg-PhoneTip").text();  
    if (phone != "") {  
        if(phoneTip == "√ 该手机号码可以注册，输入正确" || phoneTip == "√ 短信验证码已发到您的手机,请查收"){  
            // 产生验证码  
            for ( var i = 0; i < codeLength; i++) {  
                code += parseInt(Math.random() * 9).toString();  
            }  
            // 设置button效果，开始计时  
            $("#btnSendCode").attr("disabled", "true");  
            $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");  
            InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次  
            // 向后台发送处理数据  
            $.ajax({  
                type: "POST", // 用POST方式传输  
                dataType: "text", // 数据格式:JSON  
                url: "sendCaptcha", // 目标地址  
                data: "phone=" + phone +"&code=" + code,  
                error: function (XMLHttpRequest, textStatus, errorThrown) {   
                      
                },  
                success: function (data){   
                    data = parseInt(data, 10);  
                    if(data == 1){  
                        $("#reg-PhoneTip").html("<font color='#339933'>√ 短信验证码已发到您的手机,请查收</font>");  
                    }else if(data == 0){  
                        $("#reg-PhoneTip").html("<font color='red'>短信验证码发送失败，请重新发送</font>");  
                    }else if(data == 2){  
                        $("#reg-PhoneTip").html("<font color='red'>该手机号码今天发送验证码过多</font>");  
                        focusPhone();
                    }  
                }  
            });  
        }  
    }else{  
        $("#reg-PhoneTip").html("<font color='red' size='2'>手机号码不能为空</font>");  
        focusPhone();
    }  
}  
  
//timer处理函数  
function SetRemainTime() {  
    if (curCount == 0) {                  
        window.clearInterval(InterValObj);// 停止计时器  
        $("#btnSendCode").removeAttr("disabled");// 启用按钮  
        $("#btnSendCode").val("重新发送验证码");  
        code = ""; // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效  
    }else {  
        curCount--;  
        $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");  
    }  
}  
  
$(document).ready(function() {  
    $("#SmsCheckCode").blur(function() {  
        var SmsCheckCodeVal = $("#SmsCheckCode").val();  
        // 向后台发送处理数据  
        $.ajax({  
            url : "UserAction_checkCode.action",   
            data : {SmsCheckCode : SmsCheckCodeVal},   
            type : "POST",   
            dataType : "text",   
            success : function(data) {  
                data = parseInt(data, 10);  
                if (data == 1) {  
                    $("#SmsCheckCodeTip").html("<font color='#339933' size='2'>√ 短信验证码正确，请继续</font>");  
                } else {  
                    $("#SmsCheckCodeTip").html("<font color='red' size='2'>短信验证码有误，请核实后重新填写</font>");  
                }  
            }  
        });  
    });  
});  

function checkPhone() {  
    var phone = $("#reg-phone").val();  
    var re= /(^1[3|5|8][0-9]{9}$)/;  
    console.log(phone);
    if (trim(phone) == "") {
        document.getElementById("reg-PhoneTip").innerHTML = "<font color='red' size='2'>手机号码不能为空</font>";  
        focusPhone();
        return false;  
    } else if(trim(phone) != ""){  
        if(!re.test(phone)){  
            document.getElementById("reg-PhoneTip").innerHTML = "<font color='red' size='2'>请输入有效的手机号码</font>"; 
            focusPhone();
            return false;  
        }else{  
            // 向后台发送处理数据  
            $.ajax({  
                url : "checkPhone",// 目标地址  
                data : {phone : phone}, // 目标参数  
                type : "POST", // 用POST方式传输  
                dataType : "text", // 数据格式:text  
                success : function(data) {  
                	console.log(data);
                    //data = parseInt(data, 10);  
                    if (data == "true") {
                        $("#reg-PhoneTip").html("<font color='#339933' size='2'>√ 该手机号码可以注册，输入正确</font>"); 
                        $("#reg-phone").css('border', '1px #339933 solid');
                    } else {  
                    	console.log(data+"111111111111");
                        $("#reg-PhoneTip").html("<font color='red' size='2'>该手机号码已经被注册，请重新输入</font>");
                        $("#reg-phone").css('border', '1px #ff0000 solid');
                    }  
                }  
            });  
            return true;  
        }  
    }  
      
} 

function trim(str) {  
    var strnew = str.replace(/^\s*|\s*$/g, "");  
    return strnew;  
}  

function focusPhone() {  
	  $("#reg-phone").css('border', '1px #ff0000 solid');
      $("#reg-phone").focus();
}  