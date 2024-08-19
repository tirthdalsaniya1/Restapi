package rest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class Try3 {

	@Test
	public void test() {
		// RestAssured.baseURI = "https://reqres.in/api";
//		String url = "https://reqres.in/api/users?page=2";
//		String url = "https://petstore.swagger.io/v2/store/order/200";
//		String url = "https://petstore.swagger.io/v2/store/order/2";

		given().get("https://petstore.swagger.io/v2/store/order/2").then().log().all().and().extract().response();

		// String url = "https://reqres.in/api/users?page=2";

		// given().get("https://petstore.swagger.io/v2/store/order/200").then().log().ifError();
		
		Response resp = given().get("https://petstore.swagger.io/v2/store/order/2");

	}

}