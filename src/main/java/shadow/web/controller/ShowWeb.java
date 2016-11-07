package shadow.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import shadow.web.domain.SendRecord;
import shadow.web.domain.ShadowUser;
import shadow.web.services.AlidayuMessageService;
import shadow.web.services.PageShowService;
import shadow.web.services.UserService;
import shadow.web.utils.util.RandomValidateCode;
import shadow.web.utils.util.Server;

@Controller
public class ShowWeb {

	private static final Log logger = LogFactory.getLog(ShowWeb.class);
	@Autowired
	AlidayuMessageService alidayuMessageService;
	@Autowired
	PageShowService pageShowService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/login")
	private String login(Model model, ShadowUser shadowUser) {
		model.addAttribute("sessionUser", new ShadowUser());
		return "login";
	}
	
	@RequestMapping(value = "/logout")
	@ResponseBody
	private String logout(Model model, HttpServletRequest request) {
		request.getSession().invalidate();
		model.addAttribute("sessionUser",null);
		return "logout";
	}
	
	@RequestMapping(value = "/changepassword")
	private String changepassword() {
		return "changepassword";
	}

	@RequestMapping(value = "/checkReg", produces = "text/html; charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	private String checkReg(String username, String password, String phone, String captcha, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return "用户名或密码不能为空！";
		} else {
			if (!checkCode(request, captcha)) {
				return "验证码错误!";
			} else if (userService.addUser(username, password, phone) ==0 ) {
				return "用户名已存在！";
			}
		}
		return "true";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	private String index(@ModelAttribute("sessionUser") ShadowUser sessionUser, HttpServletRequest request,
			HttpServletResponse response) {
		String ip = Server.getIpAddr(request);
		try {
			PrintWriter printWriter = response.getWriter();
			String username = sessionUser.getUsername();
			if (StringUtils.isNotEmpty(sessionUser.getPassword())) {
				logger.info("登录用户为："+username+",IP："+ip);
				return "index";
			} else {
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
				builder.append("alert(\"身份验证失败。请重新登陆！\");");
				builder.append("window.location.href=\"/shadow.web/login\";");
				builder.append("</script>");
				printWriter.print(builder.toString());
				printWriter.close();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/checkLogin", produces = "text/html; charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	private String checkLogin(String username, String password, String captcha, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return "用户名或密码不能为空！";
		} else {
			String pass = userService.getPassword(username);
			if (StringUtils.isEmpty(pass) || !password.equals(pass)) {
				return "用户名或密码错误！";
			} else if (!checkCode(request, captcha)) {
				return "验证码错误!";
			}
		}
		return "true";
	}

	@RequestMapping(value = "/getCaptcha")
	@ResponseBody
	public String getCaptcha(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Set-Cookie", "name=value; HttpOnly");// 设置HttpOnly属性,防止Xss攻击
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);// 输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 验证码验证
	public boolean checkCode(HttpServletRequest request, String captcha) {
		// 1:获取cookie里面的验证码信息
		/*
		 * String code = null; Cookie[] cookies = request.getCookies(); for
		 * (Cookie cookie : cookies) { if ("imagecode".equals(cookie.getName()))
		 * { code = cookie.getValue(); break; } }
		 */

		// 获取session验证码的信息
		String code = (String) request.getSession().getAttribute(request.getSession().getId());
		logger.info("获取到session中的验证码为"+code);
		// 判断验证码是否正确
		if (!StringUtils.isEmpty(captcha) && captcha.equalsIgnoreCase(code)) {
			return true;

		}
		return false;
	}

	@ResponseBody
	@RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
	private boolean checkPhone(String phone) {
		if (userService.checkPhone(phone)) {
			return true;
		}
		return false;
	}

	@ResponseBody
	@RequestMapping(value = "/sendCaptcha", method = RequestMethod.POST)
	private String messageSend(HttpServletRequest request, String phone, String code) {
		try {
			//String result = alidayuMessageService.SendMsg("123", "SMS_12870237", phone, "{\"code\":\"" + code + "\"}");
			request.getSession().setAttribute(request.getSession().getId(), code);
			System.out.println();
			return "1";
		} catch (Exception e) {
			logger.error("ERROR:" + e.getMessage());
			return "0";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/recordList")
	private List<SendRecord> sendRecords(@Param("bizId") String bizId) {
		List<SendRecord> SendRecords = new ArrayList<SendRecord>();
		SendRecords = pageShowService.getRecordList(bizId);
		return SendRecords;
	}
}
