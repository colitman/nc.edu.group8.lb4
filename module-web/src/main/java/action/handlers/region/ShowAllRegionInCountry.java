package action.handlers.region;

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

public class ShowAllRegionInCountry implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(ShowAllRegionInCountry.class);	

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to show all regions");

			int parentID = Integer.valueOf(request.getParameter("parent_id"));

			LoggerUtils.info(logger, "Get parent id:", request.getParameter("parent_id"));

			Gateway<Country> countryGateway = GatewayResolver.getGateway();
			Country parent = countryGateway.get(Country.class, parentID);

			logger.info("Get parent object");

			Gateway<Region> regionGateway = GatewayResolver.getGateway();
			Collection<Region> data = regionGateway.getAllBy(Region.class, parentID);
		
			logger.info("Get all regions");

			request.setAttribute("data", data);
	
			logger.info("Set regions into request attributes");

			request.setAttribute("parent", parent);

			logger.info("Set parent object into request attributes");
			logger.info("Send forward to region/showAllInCountry.jsp page");

			return "region/showAllInCountry.jsp";
		}
		catch (Exception e) {
			logger.error("Error  occured in ShowAllRegionInCountry action", e);
			throw new ActionException(e);
		}
	}
}