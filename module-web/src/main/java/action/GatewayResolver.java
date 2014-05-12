package action;

import hibernate.dao.Gateway;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.Logger;

public class GatewayResolver {
	
	private static Gateway gateway;
	private static final Logger logger = Logger.getLogger(GatewayResolver.class);

	public static void setGateway(Gateway g) {
		gateway = g;
	}

	public static void unsetGateway() {
		gateway = null;
	}

	public static Gateway getGateway() {
		logger.info("Getting gateway");
		if (gateway != null) {
			return gateway;
		}
		
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		return (Gateway) context.getBean("oracleGateway");
	}
}