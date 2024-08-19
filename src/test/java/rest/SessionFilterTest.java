package rest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;

public class SessionFilterTest {
	
	@Test(enabled = false)
	public void sessionFilterTest() {
		
		SessionFilter filter = new SessionFilter();
		
		
		RestAssured.given().filter(filter).get();
		
		RestAssured.given().filter(filter).cookie("name",filter.getSessionId()).get();
		
		RestAssured.config = RestAssured.config().connectionConfig(new ConnectionConfig().closeIdleConnectionsAfterEachResponse());
		Response resp = RestAssured.given().log().all().get("");
		System.out.println(resp.asPrettyString());

	}

}
