package rest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;
import org.testng.reporters.Files;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;
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
		
		given().get("https://petstore.swagger.io/v2/store/order/2").then().time(lessThan(5000l)).body("", equalTo(null));
		
		JsonPath path = resp.jsonPath();
		
		ResponseSpecification respSpec = new ResponseSpecBuilder().expectStatusCode(0).build();
		
		assertEquals(false, null);
		
        Path path1 = Paths.get("");
        
        

	}

}