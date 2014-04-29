package action;

import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public interface HttpAction {
	
	public String perform(HttpServletRequest request, HttpServletResponse response) throws ActionException;
}