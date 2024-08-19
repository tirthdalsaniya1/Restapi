package rest;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class LoggerExample {

    private static final Logger logger = LogManager.getLogger(LoggerExample.class);

	@Test
	public void testTrial() {

		String url = "https://reqres.in/api/users?page=2";

		Response response = given().get(url).then().log().all().extract().response();

		// Extracting first names of all users with id < 10
		String str = response.jsonPath().getString("data.find{it.id==12}.email");

		System.out.println("email id is " + str);

		logger.info("This is an info message"); // This will not be logged
		logger.warn("This is a warning message"); // This will not be logged
		logger.error("This is an error message"); // This will be logged to the console
	}

}
