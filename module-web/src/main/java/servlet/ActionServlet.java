package servlet;

import logger.*;
import action.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;

public class ActionServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(ActionServlet.class);	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("Building HttpAction");
			
			LoggerUtils.info(logger, "Code:", request.getParameter("code"));
			
			String code = request.getParameter("code");
			String url = ActionFactory.getInstance().build(code).perform(request, response);
			request.getRequestDispatcher(url).forward(request, response);
		}
		catch (Exception e) {
			logger.error("Error occured in ServletPrototype", e);
		}
	}
}