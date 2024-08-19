package rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.matchesPattern;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class HamcrestAssertionDemo {

	@Test
	public void hamcrestAssertion1() {

		given().baseUri("https://reqres.in").basePath("/api/users").queryParam("page", 4).log().all().get().then()
				.time(lessThan(2000L)).body("page", equalTo(4)).statusCode(200)
				.body("support.url", equalTo("https://reqres.in/#support-heading")).log().all();

	}

	@Test
	public void hamcrestAssertion2() {

		given().baseUri("https://reqres.in").basePath("/api/users").queryParam("page", 4).log().all().get().then()
				.body("data", notNullValue()) // checks that the data field is not null
				.body("data", hasSize(0)) // checks that the data array is empty
				.body("missing_field", nullValue()) // checks that the missing_field is null (doesn't exist)
				.body("data", empty()) // Ensure the data array is empty
				.body("total_pages", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(4)))
				.body("support.text", containsString("contributions"))
				.body("support.url", matchesPattern("https://reqres.in/#support-heading"))
				.body("total", equalTo(12), "total_pages", equalTo(2)).log().all();

	}

	@Test
	public void hamcrestAssertion3() {
		given().baseUri("https://reqres.in/api/users").queryParam("page", 1).when().get().then().statusCode(200)
				.body("data.email", everyItem(matchesPattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")))
				.log().all();

	}

	@Test
	public void hamcrestAssertion4() {
		Response resp = given().baseUri("https://reqres.in/api/users").queryParam("page", 1).when().get().then().log()
				.all().and().extract().response();

		List<String> list = resp.jsonPath().get("data.findAll{it.id>4}.email");
		System.out.println("email list "+list);
		
		System.out.println("last name is "+resp.jsonPath().getString("data[0].last_name"));

	}

}