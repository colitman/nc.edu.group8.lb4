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

public class ShowOneUniversity implements HttpAction {

	private static final Logger logger = Logger.getLogger(ShowOneUniversity.class);	
	
	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to show university");

			int id = Integer.valueOf(request.getParameter("parent_id"));

			University university = null;
			        	
			if (GatewayResolver.getGateway() instanceof EJBBeansGateway) {

				Gateway<University> gateway = GatewayResolver.getGateway();
				university = gateway.get(University.class, id);
		
				logger.info("University properties: ");
		
				LoggerUtils.info(logger, "Name:", university.getName());
				LoggerUtils.info(logger, "DepartamentsCount:", String.valueOf(university.getDepartamentsCount()));
				LoggerUtils.info(logger, "WWW:", university.getWWW());
				LoggerUtils.info(logger, "ParentID:", String.valueOf(university.getParentID()));	
				
				Gateway<City> cityGateway = GatewayResolver.getGateway();
				City city = cityGateway.get(City.class, university.getParentID());
	            
	            Gateway<Region> regionGateway = GatewayResolver.getGateway();
				Region region = regionGateway.get(Region.class, city.getParentID());
				
	            Gateway<Country> countryGateway = GatewayResolver.getGateway();
				Country country = countryGateway.get(Country.class, region.getParentID());
		
	            request.setAttribute("city", city);
	            request.setAttribute("region", region);
	            request.setAttribute("country", country);
			}
			
			request.setAttribute("parent", university);
			
			logger.info("Set university into session");
			logger.info("Send forward to showAllUniversity page");	
			
			return "university/showOne.jsp";
		}
		catch (Exception e) {
			logger.error("Error occured in ShowOneUniversity action", e);
			throw new ActionException(e);
		}
	}
}