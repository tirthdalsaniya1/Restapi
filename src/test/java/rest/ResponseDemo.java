package rest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ResponseDemo {

	@Test
	public void responseDemoTest() {

		Response response = RestAssured.get("https://reqres.in/api/users?page=2");

		// Print status code
		System.out.println("Status code: " + response.getStatusCode());

		// Print status line
		System.out.println("Status line: " + response.getStatusLine());

		// Print response body as string
		System.out.println("Response body: " + response.prettyPrint());

		// Print a specific header
		System.out.println("Content-Type: " + response.getHeader("Content-Type"));

		// Print all headers
		System.out.println("Headers: " + response.getHeaders());

		// Extract a value from the JSON response body using JsonPath
		String title = response.jsonPath().getString("support.url");
		System.out.println("Title: " + title);

		// Print response time in milliseconds
		System.out.println("Response time: " + response.time() + " ms");
	}

}
