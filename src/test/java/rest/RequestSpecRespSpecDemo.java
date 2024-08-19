package rest;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utility.JsonManager;

public class RequestSpecRespSpecDemo {

	@Test
	public void testReqRespDemo1() throws IOException {

		String authBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "auth");
		String postBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "post");

		String baseUri = "https://restful-booker.herokuapp.com";

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri(baseUri).setContentType("application/json")
				.build();

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType("application/json").expectStatusLine("HTTP/1.1 200 OK").build();

		Response response = given().spec(reqSpec).basePath("/auth").body(authBody).post().then().spec(resSpec).log()
				.all().extract().response();
		System.out.println(response);

		Response postResponse = RestAssured.given().spec(reqSpec).basePath("/booking").body(postBody).when().post()
				.then().spec(resSpec).log().all().extract().response();

		System.out.println(postResponse);

	}

	@Test
	public void testReqRespDemo() {
		// RestAssured.baseURI = "https://reqres.in/api";

		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://reqres.in/api")
				.setBasePath("users").addQueryParam("page=2").build();

		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectContentType("application/json")
				.expectStatusCode(200).expectHeader("Content-Type", "application/json; charset=utf-8").build();

		given().spec(requestSpec).when().get().then().spec(responseSpec);

	}

}