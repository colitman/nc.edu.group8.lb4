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

public class AddCountry implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(AddCountry.class);

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to add country");

			Country country = new Country();
			country.setName(request.getParameter("name"));
			country.setLanguage(request.getParameter("language"));
			country.setCapital(request.getParameter("capital"));
			country.setPopulation(Integer.valueOf(request.getParameter("population")));
			country.setTimezone(Integer.valueOf(request.getParameter("timezone")));

			logger.info("Country properties: ");

			LoggerUtils.info(logger, "Name:", request.getParameter("name"));
			LoggerUtils.info(logger, "Language:", request.getParameter("language"));
			LoggerUtils.info(logger, "Capital:", request.getParameter("capital"));
			LoggerUtils.info(logger, "Population:", request.getParameter("population"));
			LoggerUtils.info(logger, "Timezone:", request.getParameter("timezone"));

			GatewayResolver.getGateway().add(country);
			
			logger.info("Country was successfully added");			
			logger.info("Send forward to showAllCountry page");

			return "action?code=showAllCountry";
		} 	
		catch (Exception e) {	
			logger.error("Error occured in AddCountry action", e);
			throw new ActionException(e);
		}
	}
}