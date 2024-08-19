package rest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class LoggingFilterExample {

	@Test
	public void test3() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

		RestAssured.filters(new Filter() {
			@Override
			public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res,
					FilterContext ctx) {
				// Log details of the request
				System.out.println("Request URI: " + req.getURI());
				System.out.println("Request Method: " + req.getMethod());
				System.out.println("Request Headers: " + req.getHeaders());
				System.out.println("Request Body: " + (req.getBody() != null ? req.getBody() : "No body content"));

				// Proceed with the request
				Response response = ctx.next(req, res);

				// Log details of the response
				System.out.println("Response Status Code: " + response.getStatusCode());
				System.out.println("Response Headers: " + response.getHeaders());
				System.out.println("Response Body: " + response.getBody().asString());
				return response;
			}
		});

		// Execute a GET request
		RestAssured.given().log().all().when().get("/posts/1").then().log().all();
	}
}