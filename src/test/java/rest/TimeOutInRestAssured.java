package rest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;

public class TimeOutInRestAssured {
	
	@Test
	public void timeOutTest() {

		/*
		 * You can configure timeouts in RestAssured using the HttpClientConfig class.
		 * This allows you to specify the maximum time that the client will wait for a
		 * response from the server. Setting timeouts is crucial in testing scenarios to
		 * prevent indefinite waiting periods for a server response, ensuring that
		 * integration tests complete in a timely manner. Two important variables for
		 * setting timeouts are `http.socket.timeout` for read timeouts and
		 * `http.connection.timeout` for connection timeouts.
		 */

		// Setting configuration for RestAssured
		// Timeout in milliseconds for establishing the connection
		// Timeout in milliseconds for waiting for data
		RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("http.connection.timeout", 1000).setParam("http.socket.timeout", 1000));

		// Executing a GET request using the configured timeouts
		String url = "https://reqres.in/api/users?page=2";
		// This performs the HTTP GET request and logs the response
		RestAssured.given().get(url).then().log().all();

	}

}
