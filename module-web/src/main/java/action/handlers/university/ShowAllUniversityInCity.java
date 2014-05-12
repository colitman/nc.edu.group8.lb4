package action.handlers.university;

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

public class ShowAllUniversityInCity implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(ShowAllUniversityInCity.class);	

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to show all universities");

			int parentID = Integer.valueOf(request.getParameter("parent_id"));

			LoggerUtils.info(logger, "Get parent id:", request.getParameter("parent_id"));

			Gateway<City> parentGateway = GatewayResolver.getGateway();
			City parent = parentGateway.get(City.class, parentID);

			logger.info("Get parent object");
            
			if (parent != null) {
				GatewayResolver.unsetGateway();
        	    		Gateway<Region> regionGateway = GatewayResolver.getGateway();
            			Region region = regionGateway.get(Region.class, parent.getParentID());
            			logger.info("Get region object");
            
            			Gateway<Country> countryGateway = GatewayResolver.getGateway();
            			Country country = countryGateway.get(Country.class, region.getParentID());
            			logger.info("Get country object");

				request.setAttribute("region", region);
            			request.setAttribute("country", country);
			}

			Gateway<University> gateway = GatewayResolver.getGateway();
			Collection<University> data = gateway.getAllBy(University.class, parentID);
		
			logger.info("Get all universities");

			request.setAttribute("data", data);
	
			logger.info("Set universities into request attributes");

			request.setAttribute("parent", parent);
            		
			logger.info("Set parent object into request attributes");
			logger.info("Send forward to university/showAllInCity.jsp page");

			return "university/showAllInCity.jsp";
		}
		catch (Exception e) {
			logger.error("Error  occured in ShowAllUniversityInCity action", e);
			throw new ActionException(e);
		}
	}
}