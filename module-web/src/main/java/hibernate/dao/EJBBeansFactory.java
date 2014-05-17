package hiberhate.dao;

import java.util.Map;
import java.util.HashMap;

public class EJBBeansFactory {

	private EJBBeansFactory instance;
	private Map<Class, String> map = init();
	
	private EJBBeansFactory() {} 
	
	public EJBBeansFactory getInstance() {
		if (instance == null) {
			instance = new EJBBeansFactory();
		}
		return instance;
	}
	
	public String getBean(Class someClass) {
		return map.get(someClass);
	}
	
	private Map<Class, String> init() {
		Map<Class, String> map = new HashMap<Class, String>();
		
		map.put(University.class, 	"java:global.project.module-ejb-1.0.UniversityBean!beans.uni.UniversityHome");
		map.put(Country.class, 		"java:global.project.module-ejb-1.0.CountryBean!beans.country.CountryHome");
		map.put(Region.class, 		"java:global.project.module-ejb-1.0.RegionBean!beans.region.RegionHome");
		map.put(City.class, 		"java:global.project.module-ejb-1.0.CityBean!beans.city.CityHome");
		
		return map;
	}
}