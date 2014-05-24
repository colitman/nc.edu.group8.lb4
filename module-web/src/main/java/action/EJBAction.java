package action;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;

import java.rmi.RemoteException;

public interface EJBAction {
	
	public static final String COUNTRY_BEAN = "java:global.project.module-ejb-1.0.CountryBean!beans.country.CountryHome";
	public static final String CITY_BEAN = "java:global.project.module-ejb-1.0.CityBean!beans.city.CityHome";
	public static final String REGION_BEAN = "java:global.project.module-ejb-1.0.RegionBean!beans.region.RegionHome";
	public static final String UNIVERSITY_BEAN = "java:global.project.module-ejb-1.0.UniversityBean!beans.uni.UniversityHome"; 
	
	public Object perform(Context context, Object entity, Object... args)
			throws NamingException, RemoteException, FinderException, ActionException;
}