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

public class ModifyRegion implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(ModifyRegion.class);	

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to modify region");

			int id = Integer.valueOf(request.getParameter("id"));

			LoggerUtils.info(logger, "Get region id:", request.getParameter("id"));

			Region region = new Region();
			region.setID(id);
			region.setName(request.getParameter("name"));
			region.setPopulation(Integer.valueOf(request.getParameter("population")));
			region.setSquare(Integer.valueOf(request.getParameter("square")));
			region.setParentID(Integer.valueOf(request.getParameter("parent_id")));			

			logger.info("New region properties: ");
			
			LoggerUtils.info(logger, "Name:", request.getParameter("name"));
			LoggerUtils.info(logger, "Population:", request.getParameter("population"));
			LoggerUtils.info(logger, "Square:", request.getParameter("square"));
			LoggerUtils.info(logger, "ParentID:", request.getParameter("parent_id"));
			
			Gateway<Region> gateway = GatewayResolver.getGateway();
			gateway.modify(region);

			logger.info("Region was successfully modified");			
			logger.info("Send redirect to city/showAllInRegion page");

			return "action?code=showAllCityInRegion&parent_id=" + id;
		}
		catch (Exception e) {
			logger.error("Error occured in ModifyRegion action", e);
			throw new ActionException(e);
		}
	}
}