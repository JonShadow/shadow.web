package shadow.web.utils.timer;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoginHold {
	Logger logger = Logger.getLogger(LoginHold.class);

	@Scheduled(cron = "0 0/1 * * * ?")
	public void show() throws ClientProtocolException, IOException, Exception {
		System.out.println("Annotation��is show run");
		holdLogin();
	}

	public boolean holdLogin() throws ClientProtocolException, IOException, Exception {
		logger.info("定时任务....");
		// String token = Login.loginToken();
		// logger.info("��ǰtoken״̬��"+token);
		return true;
	}

	/*
	 * public boolean checkIsLogin() throws ClientProtocolException, IOException
	 * { try { HttpResponse response = Login.client.execute(new
	 * HttpGet("http://qw.simba.taobao.com/home.html?token=8FcugyS2")); Document
	 * document = Jsoup.parse(EntityUtils.toString(response.getEntity(),
	 * "utf8")); Element element = document.getElementById("nest-main-inner");
	 * if (element != null) { return true; } return false; } catch (Exception e)
	 * { logger.error(e.getMessage()); return false; } }
	 */

}
