package action.handlers.country;

import action.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.beans.factory.*;
import org.apache.log4j.*;
import org.apache.log4j.xml.*;
import hibernate.dao.*;
import hibernate.logic.*;
import java.util.Collection;
import org.springframework.util.*;

public class ShowAllCountry implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(ShowAllCountry.class);	

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			//DOMConfigurator.configure("log4j.xml");
			logger.info("Logger installed");
			logger.info("Prepare to show all countries");

			Gateway<Country> gateway = GatewayResolver.getGateway();
			Collection<Country> data = gateway.getAll(Country.class);
		
			logger.info("Get all countries");

			request.setAttribute("data", data);
	
			logger.info("Set countries into request attributes");
			logger.info("Send forward to country/showAll.jsp page");

			return "country/showAll.jsp";
		}
		catch (Exception e) {
			logger.error("Error  occured in ShowAllCountry action", e);
			throw new ActionException(e);
		}
	}
}