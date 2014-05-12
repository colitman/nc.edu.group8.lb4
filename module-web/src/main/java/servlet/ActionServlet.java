package servlet;

import logger.LoggerUtils;

import action.ActionFactory;
import action.HttpAction;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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