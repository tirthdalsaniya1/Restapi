package rest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class FilterExample {

	@Test
	public void test1() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

		Filter simpleLoggingFilter = new Filter() {
			@Override
			public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res,
					FilterContext ctx) {
				System.out.println("Sending request to URI: " + req.getURI());
				Response response = ctx.next(req, res);
				System.out.println("Received response with Status Code: " + response.getStatusCode());
				return response;
			}
		};

		// Add the filter to a request and execute the request
		RestAssured.given().filter(simpleLoggingFilter).when().get("/posts/1").then().log().all();
	}

	@Test
	public void test2() {
		// Set the base URI for all requests
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

		// Create and define the simple logging filter
		Filter simpleLoggingFilter = new Filter() {
			@Override
			public Response filter(FilterableRequestSpecification request, FilterableResponseSpecification response,
					FilterContext context) {
				// Log the method and URL of the outgoing request
				System.out.println("Sending " + request.getMethod() + " request to: " + request.getURI());

				// Execute the request and obtain a response
				Response res = context.next(request, response);

				// Log the status code of the incoming response
				System.out.println("Received response with status code: " + res.getStatusCode());

				// Return the response to complete the filter chain
				return res;
			}
		};

		// Add the custom filter to the request, execute a GET request, and log
		RestAssured.given().filter(simpleLoggingFilter) // Attach the custom filter
				.when().get("/posts/1").then().statusCode(200).log().all();
	}
}