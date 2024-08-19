package rest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.response.Response;

public class CloseIdleConnectionTest {

	@Test
	public void testCloseIdleConnection() {

		RestAssured.config = RestAssured.config()
				.connectionConfig(new ConnectionConfig().closeIdleConnectionsAfterEachResponse());
		Response resp = RestAssured.given().log().all().get("https://reqres.in/api/users?page=2");
		System.out.println(resp.asPrettyString());

	}

}
