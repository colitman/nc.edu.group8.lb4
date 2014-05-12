package action.handlers.city;

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

public class ShowAllCityInRegion implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(ShowAllCityInRegion.class);	

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to show all cities");

			int parentID = Integer.valueOf(request.getParameter("parent_id"));
			
			LoggerUtils.info(logger, "Get parent id:", request.getParameter("parent_id"));

			Gateway<Region> parentGateway = GatewayResolver.getGateway();
			Region parent = parentGateway.get(Region.class, parentID);
            		logger.info("Get parent object");
            
			if (parent != null) {
				GatewayResolver.unsetGateway();
        	    		Gateway<Country> countryGateway = GatewayResolver.getGateway();
            			Country country = countryGateway.get(Country.class, parent.getParentID());
            			logger.info("Get country object");
				request.setAttribute("country", country);
			}

			Gateway<City> gateway = GatewayResolver.getGateway();
			Collection<City> data = gateway.getAllBy(City.class, parentID);
		
			logger.info("Get all cities");

			request.setAttribute("data", data);
	
			logger.info("Set cities into request attributes");

			request.setAttribute("parent", parent);
            		
			logger.info("Set parent object into request attributes");
			logger.info("Send forward to city/showAllInRegion.jsp page");

			return "city/showAllInRegion.jsp";
		}
		catch (Exception e) {
			logger.error("Error  occured in ShowAllCityInRegion action", e);
			throw new ActionException(e);
		}
	}
}