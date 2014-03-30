package hibernate.dao;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.stereotype.Service;

@Service
public class EJBBeansGateway<T> implements Gateway<T> {

	@Override
	public void add(T entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(T entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T get(Class<T> className, int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<T> getAll(Class className) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<T> getAllBy(Class className, int parentID)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(T entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}