package action;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActionFactory {
	
	private static final Logger logger = Logger.getLogger(ActionFactory.class);	
	private static ActionFactory instance = new ActionFactory();

	private ActionFactory() {}
	
	public static ActionFactory getInstance() {
		logger.info("Getting ActionFactory");
		return instance;
	}
	
	public HttpAction build(String actionName) throws WrongCommandException {
		try {
			logger.info("Building HttpAction");

			ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
			return (HttpAction) context.getBean(actionName);
		}
		catch (Exception e) {
			logger.error("Error in ActionFactory: wrong command", e);
			throw new WrongCommandException(e);
		}
	}
}