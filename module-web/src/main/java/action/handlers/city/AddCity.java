package action.handlers.city;

import logger.*;
import action.*;
import hibernate.dao.*;
import hibernate.logic.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import org.springframework.util.*;

public class AddCity implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(AddCity.class);

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to add city");
			City city = new City();

			city.setName(request.getParameter("name"));
			city.setPopulation(Integer.valueOf(request.getParameter("population")));
			city.setSquare(Integer.valueOf(request.getParameter("square")));
			city.setParentID(Integer.valueOf(request.getParameter("parent_id")));
	
			logger.info("City properties: ");
	
			LoggerUtils.info(logger, "Name:", request.getParameter("name"));
			LoggerUtils.info(logger, "Population:", request.getParameter("population"));
			LoggerUtils.info(logger, "Square:", request.getParameter("square"));
			LoggerUtils.info(logger, "ParentID:", request.getParameter("parent_id"));
	
			GatewayResolver.getGateway().add(city);
	
			logger.info("City was successfully added");			
			logger.info("Send forward to showAllCity page");

			return "action?code=showAllCityInRegion&parent_id=" + request.getParameter("parent_id");
		}
		catch (Exception e) {
			logger.error("Error occured in AddCity action", e);
			throw new ActionException(e);
		}
	}
}