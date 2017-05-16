package shadow.web.utils.util;

import javax.servlet.http.HttpServletRequest;

public class Server {


	public static Object findBean(String beanName) {
		return StaticContextAccessor.getBean(beanName);
	}
	
	public static <T> T findBean(String beanName,Class<T> type) {
		return StaticContextAccessor.getBean(beanName, type);
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	public static void main(String[] args) {
		System.out.println(CallUtil.getInstance());
	}
}
