package action.handlers.country;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;

import action.ActionException;
import action.GatewayResolver;

import hibernate.logic.Country;

import util.Utils;
import util.ServletRequestSkeleton;
import util.ServletResponseSkeleton;
import util.gateway.ListGateway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAllCountryTest {

	@Test(expected = IllegalArgumentException.class)
	public void nullTest() throws Exception {
		Utils.perform("showAllCountry", null, null);
	}
	
	@Ignore
	@Test(expected = ActionException.class)
	public void actionExceptionTest() throws Exception {
		ListGateway<Country> gateway = new ListGateway<Country>();
	
		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "id" : return "bla";
					case "parent_id" : return "zaza";
				}
				return null;
			}
		};

		Utils.perform("showAllCountry", request, new ServletResponseSkeleton());
	}

	@Ignore
	@Test
	public void logicTest() throws Exception {
		ListGateway<Country> gateway = new ListGateway<Country>();

		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "name" : return "skeleton";
					case "capital" : return "ZZZ";
					case "language" : return "zzz";
					case "population" : return "100";
					case "timezone" : return "1";
					case "id" : return "6";
					case "parent_id" : return "3";
				}
				return null;
			}
		};
		HttpServletResponse response = new ServletResponseSkeleton();

		assertEquals(0, gateway.size());

		Utils.perform("addCountry", request, response);
		Utils.perform("addCountry", request, response);
		Utils.perform("addCountry", request, response);

		assertEquals(3, gateway.size());

		Utils.perform("showAllCountry", request, response);
	}

	@Test
	public void forwardTest() throws Exception {
		ListGateway<Country> gateway = new ListGateway<Country>();

		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "name" : return "skeleton";
					case "capital" : return "ZZZ";
					case "language" : return "zzz";
					case "population" : return "100";
					case "timezone" : return "1";
					case "id" : return "1";
					case "parent_id" : return "3";
				}
				return null;
			}
		};
		
		HttpServletResponse response = new ServletResponseSkeleton();

		Utils.perform("addCountry", request, response);	

		String url = Utils.perform("showAllCountry", request, response);
		
		if (!url.contains("showAll")) {
			fail("forward must contains <showAll>"); 
		}
	}
}