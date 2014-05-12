package action.handlers.country;

import logger.LoggerUtils;

import action.GatewayResolver;
import action.ActionException;
import action.HttpAction;

import hibernate.dao.Gateway;
import hibernate.dao.EJBBeansGateway;
import hibernate.logic.City;
import hibernate.logic.Country;
import hibernate.logic.Region;
import hibernate.logic.University;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.util.Collection;

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