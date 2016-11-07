<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<script src="B-JUI/js/jquery-1.11.3.min.js"></script>
<script src="B-JUI/js/jquery.cookie.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/lib/sha256.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/Message.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/captcha.js"
	type="text/javascript"></script>
<link href="B-JUI/themes/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/st.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="content" style="margin-bottom: 150px;">
			<div>
				<h4>注册账号</h4>
				<hr>
				<form class="form-horizontal" method="post"
					onsubmit="return member_reg(this);">
					<input type="hidden" name="pass" id="reg-pass">
					<div class="form-group form-group-lg"></div>
					<div class="form-group">
						<label for="reg-input-name form-group-lg"
							class="col-sm-3 control-label">用户名：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-lg" name="username"
								id="reg-username" placeholder="Username" value="">
						</div>
						<label style="margin-top: 10px;" for="reg-input-name">建议3-8个字之间</label>
					</div>
					<div class="form-group form-group-lg">
						<label for="reg-input-password" class="col-sm-3 control-label">密码：</label>
						<div class="col-sm-4">
							<input type="password" class="form-control input-lg"
								id="reg-password" placeholder="Password" value="">
						</div>
						<div class="col-sm-5">
							<p style="margin-top: 10px;" class=""></p>
						</div>
					</div>
					<div class="form-group form-group-lg">
						<label for="reg-input-phone" class="col-sm-3 control-label">手机号：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-lg" id="reg-phone"
								placeholder="PhoneNumber" onblur="checkPhone()" value="">
						</div>
						<p style="margin-top: 10px;" id="reg-PhoneTip"></p>
					</div>

					<div class="form-group form-group-lg">
						<label for="reg-input-captcha" class="col-sm-3 control-label">验证码：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-lg" id="reg-captcha"
								name="captcha" placeholder="请输入验证码"
								aria-describedby="sizing-addon-captcha">
						</div>
						<span id="basic-addon-code"> <input type="button"
							id="btnSendCode" class="btn btn-primary btn-lg" value="发送验证码"
							onclick="sendCaptcha()"></input>
						</span>
					</div>

					<div class="form-group form-group-lg">
						<div class="col-sm-offset-3 col-sm-9">
							<button type="submit" class="btn btn-primary btn-lg">确认注册</button>
							&nbsp;&nbsp;&nbsp;&nbsp; <a href="./login"
								class="btn btn-success btn-lg">已有账号，点此登录</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</script>
<script type="text/javascript">
	function member_reg(form) {
		var $u = $('#reg-username'), u = $u.val(), $s = $('#reg-password'), s = $s
				.val(), captcha = $('#reg-captcha').val(), phone = $(
				'#reg-phone').val(), $p = $('#reg-pass'), $f = $(form)

		if (!u || !s) {
			Message.warn('账号和密码不能为空！')
			return false
		} else if (!captcha) {
			Message.warn('验证码不能为空！')
			return false;
		}

		$p.val(HMAC_SHA256_MAC(u, s))

		$f.find(':submit').addClass('disabled').prop('disabled', true).text(
				'注册中...')

		$.post("./checkReg", {
			username : u,
			password : s,
			phone : phone,
			captcha : captcha
		}, function(data, status) {
			if (!status) {
				Message.error('系统错误，请刷新重试！');
			} else {
				console.log(data);
				if (data.indexOf('true') != -1) {
					Message.success('注册成功！正在跳转...');
					window.setTimeout(function() {
						window.location.href = './login'
					}, 2000);
				} else {
					Message.warn(data);
					$f.find(':submit').removeClass('disabled').prop('disabled',
							false).text('确认注册')
				}
			}
		});
		return false
	}
</script>
</html>
