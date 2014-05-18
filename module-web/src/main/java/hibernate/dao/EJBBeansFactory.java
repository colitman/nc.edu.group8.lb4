package hiberhate.dao;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class EJBBeansFactory {

	private static final Logger logger = Logger.getLogger(EJBBeansFactory.class);
	
	private EJBBeansFactory instance;
	private Context context = createContext();
	
	
	private EJBBeansFactory() {} 
	
	public EJBBeansFactory getInstance() {
		if (instance == null) {
			instance = new EJBBeansFactory();
		}
		return instance;
	}
	
	public Object getBean(Class someClass) {
		Annotation bean = someClass.getAnnotation(Bean.class);
		String path = null;
		if (bean != null) {
			path = bean.path(); 
		} else {
			throw new IllegalArgumentException("Annotation @Bean has not be found in " + someClass.getName());
		}
		return context.lookup(path);
	}
	
	private Context createContext() {
		try {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			p.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
			p.put(Context.SECURITY_PRINCIPAL, "admin");
			p.put(Context.SECURITY_CREDENTIALS, "admin33284");
			return new InitialContext(p);
		}
		catch (NamingException e) {
			logger.error("Connection problems. Please ensure that weblogic is already run and check"
					+ " you provider url, login and password", e);
		}
		return null;
	}
}