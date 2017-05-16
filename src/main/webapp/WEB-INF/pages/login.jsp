<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>登录</title>
<meta name="renderer" content="webkit">
<script src="B-JUI/js/jquery-1.11.3.min.js"></script>
<script src="B-JUI/js/jquery.cookie.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/lib/bootstrap/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/lib/sha256.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Message.js"></script>
<link rel="shortcut icon" href="B-JUI/assets/ico/favion.png">
<link href="B-JUI/themes/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/login.css" rel="stylesheet" type="text/css">
<link href="resources/css/st.css" rel="stylesheet">
<!-- plug - css -->
<link href="resources/css/font-awesome.min.css" rel="stylesheet">


<script type="text/javascript">
$(function() {
	choose_bg();
    changeCode();
});
function member_login(form) {
    var $u = $('#j_username'), u = $.trim($u.val()),
        $s = $('#j_password'), s = $s.val(),
        $c = $('#j_captcha'), c = $c.val(),
        $p = $('#j_randomKey'),
        $f = $(form)
        
    if (!u) {
        Message.warn('账号不能为空！')
        return false
    } else if (!s) {
    	Message.warn('密码不能为空！')
        return false
    } else if (!c) {
    	Message.warn('验证码不能为空！')
        return false
    }
    
    $p.val(HMAC_SHA256_MAC(u, s))
    
    $f.find(':submit').addClass('disabled').prop('disabled', true).text('登录中...')
    var result = login(u,s,c,$f);
    if (result == 'true') {
    	return true
    }
    return false
}
    
        /* if (json['statusCode'] == 200) {
            Message.success('登录成功！')
            if (json.message) {
                window.location.href = '/'+ json.message
            } else {
                window.location.href = '/member'
            }
        } else if (json['statusCode'] == 301) {
            Message.warn('账号未激活！')
            if (json.message) {
                window.location.href = '/'+ json.message
            }
        } else {
            Message.warn(json.message)
        }
        $f.find(':submit').removeClass('disabled').prop('disabled', false).text('确认登录')
    }, 'json') */
    


function login(u,s,c,$f){
   	var result = 'false';
	 /* 验证用户信息 */
	$.ajaxSetup({   
            async : false  
        }); 
	$.post("./checkLogin",
    		{username:u,
			 password:s,
			 captcha:c}, function(data, status) {
				 console.log(data);		
				 console.log(status);	
		if(data.indexOf('true')!=-1){
			Message.success('登录成功！');
			result = 'true';
			} else {
				/* 用户名密码错误 */
				Message.warn(data);
				if (data != "验证码错误!") {
					changeCode();
				}
				$f.find(':submit').removeClass('disabled').prop('disabled', false).text('确认登录')
			}
		});
	return result;
}

function changeCode(){
    $("#captcha_img").attr("src", "getCaptcha?t="+ (new Date().getTime()));
}
/* function checkCodeImg(code){
    var url = "${pageContext.request.contextPath}/checkCodeImg";
    $.post(url,{"validateCode":code},function(data){
        if(data=="ok"){
            alert("ok!")
        }else{
            alert("error!")
            flushValidateCode();
        }
    })
} */
function choose_bg() {
    var bg = Math.floor(Math.random() * 9 + 1);
    $('body').css('background-image', 'url(resources/images/loginbg_0'+ bg +'.jpg)');
}

</script>
</head>
<body>
	<div class="container">
		<div class="main_box">
		<form class="form-horizontal" form modelAttribute="sessionUser" action="./index" method="post" onsubmit="return member_login(this);">
                    <input type="hidden" name="pass" id="login-input-pass">
                    <p class="text-center logo">
					<img src="resources/images/logo.png" width="120" height="200" >
				</p>
				<div class="login_msg text-center">
					<font color="red"></font>
				</div>
				<div class="form-group">
					<div class="input-group">
					<label class="sr-only" for="text">登录账号</label>
						<span class="input-group-addon" id="sizing-addon-user"><span
							class="glyphicon glyphicon-user"></span></span> <input type="text"
							class="form-control" id="j_username" name="username" value=""
							placeholder="登录账号" aria-describedby="sizing-addon-user">
					</div>
				</div>
                    <div class="form-group">
					<div class="input-group">
						<span class="input-group-addon" id="sizing-addon-password"><span
							class="glyphicon glyphicon-lock"></span></span> <input type="password"
							class="form-control" id="j_password" name="password"
							placeholder="登录密码" aria-describedby="sizing-addon-password">
					</div>
				</div>
				
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon" id="sizing-addon-password"><span
							class="glyphicon glyphicon-exclamation-sign"></span></span> <input
							type="text" class="form-control" id="j_captcha" name="captcha"
							placeholder="请输入验证码" aria-describedby="sizing-addon-password" >
							<span class="input-group-addon code" id="basic-addon-code"><img
							id="captcha_img" onClick="javascript:changeCode();" alt="点击更换" title="点击更换"
							class="m"></span>
					</div>
				</div>
				
                    <div class="form-group form-group-lg">
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary btn-lg">确认登录</button>
                            &nbsp;&nbsp;
                            <a href="./reg" class="btn btn-danger btn-lg">账号注册</a>
                            &nbsp;&nbsp;
                        </div>
                    </div>
                     <div class="text-center">
                		<hr>
                		<a href="./forget">忘记了密码？</a>
            		</div>
                    
                </form>
		
		
		
		
			<!-- <form action="./checkUser" id="login_form" form modelAttribute="sessionUser" method="post" onsubmit="return member_login(this);">
				<input type="hidden" value="" id="j_randomKey" /> <input
					type="hidden" name="jfinal_token" value="" />
				<p class="text-center logo">
					<img src="resources/images/logo.png" width="120" height="200" >
				</p>
				<div class="login_msg text-center">
					<font color="red"></font>
				</div>
				<div class="form-group">
					<div class="input-group">
					<label class="sr-only" for="text">登录账号</label>
						<span class="input-group-addon" id="sizing-addon-user"><span
							class="glyphicon glyphicon-user"></span></span> <input type="text"
							class="form-control" id="j_username" name="username" value=""
							placeholder="登录账号" aria-describedby="sizing-addon-user">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon" id="sizing-addon-password"><span
							class="glyphicon glyphicon-lock"></span></span> <input type="password"
							class="form-control" id="j_password" name="password"
							placeholder="登录密码" aria-describedby="sizing-addon-password">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
					<label class="sr-only" for="text">手机号</label>
						<span class="input-group-addon" id="sizing-addon-phone"><span
							class="glyphicon glyphicon-phone"></span></span> <input type="text"
							class="form-control" id="j_phone" name="phone" value=""
							placeholder="手机号" aria-describedby="sizing-addon-phone" onblur="checkPhone()">
					</div>
					<span id="j_PhoneTip" class="glyphicon">
                                <s:fielderror cssStyle="color:red;">  
                                    <s:param>
                                    <font></font>
                                    </s:param>  
                                </s:fielderror>  
                    </span>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon" id="sizing-addon-password"><span
							class="glyphicon glyphicon-exclamation-sign"></span></span> <input
							type="text" class="form-control" id="j_captcha" name="captcha"
							placeholder="请输入验证码" aria-describedby="sizing-addon-password" >
							<span class="input-group-addon code" id="basic-addon-code"><img
							id="captcha_img" onClick="javascript:changeCode();" alt="点击更换" title="点击更换"
							class="m"></span>
							
						<span class="input-group-addon code" id="basic-addon-code">
							<input type="button" id="btnSendCode" class="btn btn-primary" value="发送验证码" onclick="sendCaptcha()"></input>
                        </span>
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
						<label for="j_remember" class="m"><input id="j_remember"
							type="checkbox" value="true">&nbsp;记住登陆账号!</label>
					</div>
				</div>
				<div class="text-center">
					<input type="submit" id="login_ok" class="btn btn-primary btn-lg" value="&nbsp;登&nbsp;录&nbsp;"></input>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="reset" class="btn btn-default btn-lg">&nbsp;重&nbsp;置&nbsp;</button>
				</div>
			</form> -->
		</div>
	</div>
</body>
</html>