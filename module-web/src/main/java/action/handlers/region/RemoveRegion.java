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

public class RemoveRegion implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(RemoveRegion.class);		

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to remove region");
			
			LoggerUtils.info(logger, "Removing region id:", request.getParameter("id"));
			
			int parentID = Integer.valueOf(request.getParameter("parent_id"));
	
			LoggerUtils.info(logger, "Get parent id:", request.getParameter("parent_id"));
	
			Gateway gateway = GatewayResolver.getGateway();
			int id = Integer.valueOf(request.getParameter("id"));
			Region region = (Region) gateway.get(Region.class, id);
			gateway.remove(region);
	
			logger.info("Region was successfully removed");			
			logger.info("Send redirect to showAllRegion page");

			return "action?code=showAllRegionInCountry&parent_id=" + parentID;
		}	
		catch (Exception e) {
			logger.error("Error occured in RemoveRegion action", e);
			throw new ActionException(e);
		}
	}
}