package rest;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utility.JsonManager;

public class TestTrial {

	@Test
	public void createBooking() {
		Map<String, String> map = new HashMap<>();
		map.put("name1", "value1");
		map.put("name1", "value1");

		String url = "https://reqres.in/api/users?page=2";
		Response resp = given().get(url).then().extract().response();

		Cookies cookieName = resp.detailedCookies();

	}

	@Test
	public void createBooking2() throws IOException {

		String postBody = JsonManager.getJsonRequestBodyAsAString("testdata/testdata.json", "post");

		Response response = given().baseUri("https://restful-booker.herokuapp.com").basePath("/booking")
				.contentType(ContentType.JSON).body(postBody).when().post().then().extract().response();

		Cookies allCookies = response.getDetailedCookies();
	}

}
