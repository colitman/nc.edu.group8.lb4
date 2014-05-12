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

public class RemoveUniversity implements HttpAction {
	
	private static final Logger logger = Logger.getLogger(RemoveUniversity.class);	

	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		Assert.notNull(request, "Request must be not null");
		Assert.notNull(response, "Responce must be not null");
		
		try {
			logger.info("Prepare to remove university");
			
			LoggerUtils.info(logger, "Removing university id:", request.getParameter("id"));
			
			int parentID = Integer.valueOf(request.getParameter("parent_id"));
	
			LoggerUtils.info(logger, "Get parent id:", request.getParameter("parent_id"));
	
			int id = Integer.valueOf(request.getParameter("id"));
			Gateway gateway = GatewayResolver.getGateway();
			University university = (University) gateway.get(University.class, id);
			gateway.remove(university);
				
			logger.info("University was successfully removed");			
			logger.info("Send redirect to showAllUniversity page");

			return "action?code=showAllUniversityInCity&parent_id=" + parentID;
		}
		catch (Exception e) {
			logger.error("Error occured in RemoveUniversity action", e);
			throw new ActionException(e);
		}
	}
}