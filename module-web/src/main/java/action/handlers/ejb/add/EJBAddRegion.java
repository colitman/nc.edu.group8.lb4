package action.handlers.ejb.add;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import action.EJBAction;
import action.ActionException;

import hibernate.logic.Country;
import hibernate.logic.Region;
import hibernate.logic.City;
import hibernate.logic.University;

import beans.country.CountryHome;
import beans.region.RegionHome;
import beans.city.CityHome;
import beans.uni.UniversityHome;

public class EJBAddRegion implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
			Object ref = context.lookup(REGION_BEAN);
			
			RegionHome home = (RegionHome) PortableRemoteObject.narrow(ref, RegionHome.class);
				
			Region object = (Region) entity;
			
			home.create(object.getParentID(),
						object.getName(),
						object.getPopulation(),
						object.getSquare());
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
		return null;
	}
}