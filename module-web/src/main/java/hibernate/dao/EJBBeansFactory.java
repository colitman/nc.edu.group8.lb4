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
	private Map<Class, String> map = init();
	private Context context = createContext();
	
	
	private EJBBeansFactory() {} 
	
	public EJBBeansFactory getInstance() {
		if (instance == null) {
			instance = new EJBBeansFactory();
		}
		return instance;
	}
	
	public Object getBean(Class someClass) {
		return context.lookup(map.get(someClass));
	}
	
	private Map<Class, String> init() {
		Map<Class, String> map = new HashMap<Class, String>();
		
		map.put(University.class, 	"java:global.project.module-ejb-1.0.UniversityBean!beans.uni.UniversityHome");
		map.put(Country.class, 		"java:global.project.module-ejb-1.0.CountryBean!beans.country.CountryHome");
		map.put(Region.class, 		"java:global.project.module-ejb-1.0.RegionBean!beans.region.RegionHome");
		map.put(City.class, 		"java:global.project.module-ejb-1.0.CityBean!beans.city.CityHome");
		
		return map;
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