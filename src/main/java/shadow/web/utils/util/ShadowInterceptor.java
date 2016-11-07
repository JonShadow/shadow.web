package shadow.web.utils.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ShadowInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		// 后台session控制
		String returnUrl = request.getRequestURI();
		String requestIp = Server.getIpAddr(request);
		System.out.println(returnUrl + requestIp);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
		builder.append("alert(\"身份验证失败。请重新登陆！\");");
		builder.append("window.location.href=\"/shadow.web/login\";");
		builder.append("</script>");
		out.print(builder.toString());
		out.close();
		return false;
	}

}
