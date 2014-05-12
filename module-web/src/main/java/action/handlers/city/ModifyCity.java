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

public class ModifyCity implements HttpAction {

	private static final Logger logger = Logger.getLogger(ModifyCity.class);	

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to modify city");

			int id = Integer.valueOf(request.getParameter("id"));

			LoggerUtils.info(logger, "Get region id:", request.getParameter("id"));
		
			City city = new City();
			city.setID(id);
			city.setName(request.getParameter("name"));
			city.setPopulation(Integer.valueOf(request.getParameter("population")));
			city.setSquare(Integer.valueOf(request.getParameter("square")));
			city.setParentID(Integer.valueOf(request.getParameter("parent_id")));

			logger.info("New city properties: ");
			
			LoggerUtils.info(logger, "Name:", request.getParameter("name"));
			LoggerUtils.info(logger, "Population:", request.getParameter("population"));
			LoggerUtils.info(logger, "Square:", request.getParameter("square"));
			LoggerUtils.info(logger, "ParentID:", request.getParameter("parent_id"));
		
			GatewayResolver.getGateway().modify(city);
			
			logger.info("City was successfully modified");			
			logger.info("Send forward to showAllUniversityInCity page");

			return "action?code=showAllUniversityInCity&parent_id=" + id;
		}
		catch (Exception e) {
			logger.error("Error occured in ModifyCity action", e);
			throw new ActionException(e);
		}
	}
}