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

public class RemoveCountry implements HttpAction {
		
	private static final Logger logger = Logger.getLogger(RemoveCountry.class);		

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {		
			logger.info("Prepare to remove country");
			
			LoggerUtils.info(logger, "Removing country id:", request.getParameter("id"));
			
			int id = Integer.valueOf(request.getParameter("id"));
			Gateway<Country> gateway = GatewayResolver.getGateway();
			Country country = gateway.get(Country.class, id);
			gateway.remove(country);

			logger.info("Country was successfully removed");			
			logger.info("Send redirect to country/showAllCountry page");

			return "action?code=showAllCountry";
		}
		catch (Exception e) {
			logger.error("Error occured in RemoveCountry action", e);
			throw new ActionException(e);
		}
	}
}