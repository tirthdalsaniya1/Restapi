package rest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class ResponseTimeValidation {

	@Test
	public void responseTimeValidationTest() {

		String url = "https://reqres.in/api/users?page=2";

		Response response = given().get(url).then().log().all().extract().response();
		System.out.println("resp time is " + response.getTime());

		assertTrue(response.timeIn(TimeUnit.MILLISECONDS) <= 700, "exceed timeout");

	}

}
