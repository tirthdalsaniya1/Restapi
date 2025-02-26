package apitest;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ReqSpecAndResponseSpecDemo {

	static RequestSpecification reqSpec;
	static ResponseSpecification respSpec;

	@BeforeClass
	public void setUp() {
		reqSpec = given().baseUri("https://reqres.in").basePath("/api/users").contentType("application/json");

		respSpec = expect().statusCode(200).contentType("application/json");
	}

	@Test
	public void verifyRequestSpecAndResponseSpec() {

	given().spec(reqSpec).queryParam("?page=2").get().then().log().all().spec(respSpec);

	}

}
