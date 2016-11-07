package shadow.web.classLoad;

import javax.servlet.http.HttpServlet;

public class TmcSeverLoad extends HttpServlet {

	/**
	 */
	private static final long serialVersionUID = -3500826006073235251L;
	
	  public void init() {
		  Thread thread = new Thread(TmcClientDeal.getTmcClientDeal());  
		  thread.start();
	  }

}
