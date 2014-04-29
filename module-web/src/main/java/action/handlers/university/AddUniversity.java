package action.handlers.university;

import logger.*;
import action.*;
import hibernate.dao.*;
import hibernate.logic.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.*;
import org.springframework.util.*;

public class AddUniversity implements HttpAction {

	private static final Logger logger = Logger.getLogger(AddUniversity.class);	
	
	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to add university");

			University university = new University();
			university.setName(request.getParameter("name"));
			university.setDepartamentsCount(Integer.valueOf(request.getParameter("departs_count")));
			university.setWWW(request.getParameter("www"));
			university.setParentID(Integer.valueOf(request.getParameter("parent_id")));
		
			logger.info("University properties: ");
		
			LoggerUtils.info(logger, "Name:", request.getParameter("name"));
			LoggerUtils.info(logger, "DepartamentsCount:", request.getParameter("departs_count"));
			LoggerUtils.info(logger, "WWW:", request.getParameter("www"));
			LoggerUtils.info(logger, "ParentID:", request.getParameter("parent_id"));
	
			GatewayResolver.getGateway().add(university);
		
			logger.info("University was successfully added");			
			logger.info("Send redirect to showAllUniversity page");

			return "action?code=showAllUniversityInCity&parent_id=" + request.getParameter("parent_id");
		}
		catch (Exception e) {
			logger.error("Error occured in AddUniversity action", e);
			throw new ActionException(e);
		}
	}
}