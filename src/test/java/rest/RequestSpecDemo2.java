package rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utility.JsonManager;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import java.io.IOException;

public class RequestSpecDemo2 {
	@Test
	public void test123() throws IOException {

		String authBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "auth");
		String baseUri = "https://restful-booker.herokuapp.com";

		RequestSpecification baseReqSpec = new RequestSpecBuilder().setBaseUri(baseUri)
				.setContentType("application/json").build();

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).build();

		ResponseSpecification resSpec1 = new ResponseSpecBuilder().expectContentType("application/json")
				.expectStatusLine("HTTP/1.1 200 OK").build();

		Response response = given().log().all().spec(baseReqSpec).spec(baseReqSpec).body(authBody).post("/auth").then()
				.log().all().spec(resSpec).spec(resSpec1).extract().response();
	}
}