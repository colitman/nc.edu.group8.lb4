package util.gateway;

import java.util.*;
import java.sql.*;
import hibernate.dao.*;
import hibernate.logic.*;

import java.lang.reflect.ParameterizedType;

public class ListGateway<T extends Entity> implements Gateway<T> {
 
	private static int index = 1;
	private List<T> list = new ArrayList<T>();

	public void add(T entity) throws SQLException {
		entity.setID(index++);
		System.out.println(">>>>>>>>>>>>ID>>>>>>>>>>>>>>>" + entity.getID());
		list.add(entity);
	}

	public void modify(T entity) throws SQLException {
		int id = entity.getID();
		if (get(null, id) != null) {
			remove(get(null, id));
			add(entity);
		} else {
			add(entity);
		}
	}	

	public T get(Class<T> className, int id) throws SQLException {
		for (int i = 0;  i < size(); i++) {
			if (list.get(i).getID() == id) {
				return list.get(i);
			}
		}
		return null;
	}

	public Collection<T> getAll(Class className) throws SQLException {
		return list;
	}

	public Collection<T> getAllBy(Class className, int parentID) throws SQLException {
		Collection<T> collection = new ArrayList<T>();
		for (int i = 0; i < size(); i++) {
			if (list.get(i).getParentID() == parentID) {
				collection.add(list.get(i));
			}
		}
		return collection;
	}

	public void remove(T entity) throws SQLException {
		list.remove(entity);
	}

	public int size() {
		return list.size();
	}
}