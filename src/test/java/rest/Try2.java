package rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

public class Try2 {

	@Test
	public void hitEndPoint() throws IOException {

		given().when().get("https://api.agify.io/?name=michael").then().body("count", equalTo(298219))
				.body("name", notNullValue()).body("name", equalTo("michael"));
	}

}