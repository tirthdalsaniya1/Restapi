package rest;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class ApiBasePathBaseURIDemo {

	@Test
	public void testTrial() {

		String url = "https://reqres.in/api/users";

		RestAssured.basePath = "/api/users";
		RestAssured.baseURI = "https://reqres.in";

		given().log().all().get().then().log().all();

	}
	
	@Test
	public void testTrial2() {

		String url = "https://reqres.in/api/users";

		String basePath = "/api/users";
		String baseURI = "https://reqres.in";

		given().baseUri(baseURI).basePath(basePath).log().all().get().then().log().all();

	}


}
