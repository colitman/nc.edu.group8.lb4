package action.handlers.ejb.get;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import java.util.Collection;
import java.util.ArrayList;

import action.EJBAction;
import action.ActionException;

import hibernate.logic.Country;
import hibernate.logic.Region;
import hibernate.logic.City;
import hibernate.logic.University;

import beans.country.CountryHome;
import beans.country.CountryRemote;
import beans.region.RegionHome;
import beans.region.RegionRemote;
import beans.city.CityHome;
import beans.city.CityRemote;
import beans.uni.UniversityHome;
import beans.uni.UniversityRemote;

public class EJBGetAllByUniversity implements EJBAction {
	
	public Object perform(Context context, Object entity, Object... args) throws ActionException {
		try {
			Object ref = context.lookup(UNIVERSITY_BEAN);
			
			UniversityHome home = (UniversityHome) PortableRemoteObject.narrow(ref, UniversityHome.class);
			
			Collection<University> objectList = new ArrayList<University>();
			
			Collection<UniversityRemote> remoteList = home.findAll();
			
			int parentID = (Integer) args[0];
			
			for (UniversityRemote remote : remoteList) {
				if (remote.getParentID() == parentID) {
					University object = new University();
					object.setID(remote.getID());
					object.setParentID(remote.getParentID());
					object.setName(remote.getName());
					object.setDepartamentsCount(remote.getDepartamentsCount());
					object.setWWW(remote.getWWW());
				
					objectList.add(object);
				}
			}
			
			return  objectList;
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}
}