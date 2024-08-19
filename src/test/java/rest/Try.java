package rest;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Try {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://reqres.in/api/users?page=2";

		Response resp = given().log().all().get(baseURI);
		System.out.println(resp.asPrettyString());

		when().get(baseURI).then().statusCode(200).and().contentType("application/json");

		String str = given().get(baseURI).then().statusCode(200).extract().jsonPath().getString("total");

	//	System.out.println(str);
		
		String body = "{\r\n"
				+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
				+ "    \"password\": \"cityslicka\"\r\n"
				+ "}";
		
		String str2= given().body(body).post("https://reqres.in/api/login").then().statusCode(400).extract().asPrettyString();
		System.out.println(str2);
		
	}

}
