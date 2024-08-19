package rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

import org.testng.annotations.Test;

import io.restassured.config.RestAssuredConfig;

public class BlackListHeaderTest {

	@Test
	public void blackListHeaderTest() {

		given().log().all()
				.config(RestAssuredConfig.config()
						.logConfig(logConfig().blacklistHeader("Accept").blacklistHeader("Content-Encoding")))
				.log().all().get("https://reqres.in/api/users?page=2");

		given().log().all()
				.config(RestAssuredConfig.config().logConfig(logConfig().blacklistHeader("Content-Encoding", "Accept")))
				.log().all().get("https://reqres.in/api/users?page=2");

	}

}
