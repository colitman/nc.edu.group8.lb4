package hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.io.File;

/**
* Tools for hibernate
*/

public class HibernateUtil {
	
	private static final Logger logger = Logger.getLogger(HibernateUtil.class);	
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		logger.info("Getting SessionFactory");
		if (sessionFactory == null) {
			sessionFactory = newSessionFactory();
		}
		return sessionFactory;
	}

	private static SessionFactory newSessionFactory() { 
		try {	
			logger.info("Initializing SessionFactory");

			Locale.setDefault(Locale.ENGLISH);
			Configuration conf = new Configuration().configure("hibernate.cfg.xml");
			ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(conf.getProperties());
			return conf.buildSessionFactory(builder.buildServiceRegistry());
		}
		catch (Exception e) {
			logger.error("Error occured in HibernateUtil", e);
		}
		return null;
	}
}