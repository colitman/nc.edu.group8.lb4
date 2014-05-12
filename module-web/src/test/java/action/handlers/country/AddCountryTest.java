package action.handlers.country;

import static org.junit.Assert.*;

import org.junit.Test;

import action.ActionException;
import action.GatewayResolver;

import hibernate.logic.Country;

import util.Utils;
import util.ServletRequestSkeleton;
import util.ServletResponseSkeleton;
import util.gateway.ListGateway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCountryTest {

	@Test(expected = IllegalArgumentException.class)
	public void nullTest() throws Exception {
		Utils.perform("addCountry", null, null);
	}

	@Test(expected = ActionException.class)
	public void actionExceptionTest() throws Exception {
		ListGateway<Country> gateway = new ListGateway<Country>();
	
		GatewayResolver.setGateway(gateway);

		HttpServletRequest request = new ServletRequestSkeleton() {
			@Override
			public String getParameter(String name) {
				switch (name) {
					case "name" : return "skeleton";
					case "capital" : return "ZZZ";
					case "language" : return "zzz";
					case "population" : return "zzz";
					case "timezone" : return "zzz";
					case "parent_id" : return "zaza";
				}
				return null;
			}
		};
		HttpServletResponse response = new ServletResponseSkeleton();

		Utils.perform("addCountry", request, response);
	}

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
					case "parent_id" : return "1";
				}
				return null;
			}
		};
		HttpServletResponse response = new ServletResponseSkeleton();

		assertEquals(0, gateway.size());

		Utils.perform("addCountry", request, response);

		assertEquals(1, gateway.size());

		Utils.perform("addCountry", request, response);

		assertEquals(2, gateway.size());
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
					case "parent_id" : return "1";
				}
				return null;
			}
		};
		
		String url = Utils.perform("addCountry", request, new ServletResponseSkeleton());
		
		if (!url.contains("showAllCountry")) {
			fail("forward must contains <showAllCountry>"); 
		}
	}
}