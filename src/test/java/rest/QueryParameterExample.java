package rest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class QueryParameterExample {

	@Test
	public void multipleQueryParam1() {
		// https://reqres.in/api/users?id=2&page=2

		given().log().all().baseUri("https://reqres.in").basePath("/api/users").queryParam("id", 2)
				.queryParam("page", 2).get().then().log().all();

	}

	@Test
	public void multipleQueryParam2() {
		// https://reqres.in/api/users?page=2&id=2

		given().log().all().baseUri("https://reqres.in").basePath("/api/users").queryParam("page", 2)
				.queryParam("id", 2).get().then().log().all();

	}

	@Test
	public void multipleQueryParam3() {

		given().log().all().baseUri("https://reqres.in/api").queryParam("id", "2").queryParam("page", "2").when()
				.get("/users").then().log().all();

	}

	@Test
	public void multipleQueryParam4() {

		Map<String, Integer> params = new HashMap<>();
		params.put("id", 2);
		params.put("page", 2);

		given().log().all().baseUri("https://reqres.in").basePath("/api/users").queryParams(params).when().get().then()
				.log().all();

	}

	@Test
	public void multipleQueryParam5() {
		given().log().all().when().get("https://reqres.in/api/users?id=2&page=2").then().log().all();
	}

	@Test
	public void multipleQueryParam05() {
		Response resp = given().get("https://reqres.in/api/users?id=2&page=2");

		System.out.println(resp.getStatusLine());
	}

}
