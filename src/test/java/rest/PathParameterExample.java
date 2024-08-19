package rest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utility.JsonManager;

public class PathParameterExample {

	@Test
	public void createUpdateBooking() throws IOException {

		String postBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "post");
		String authBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "auth");
		String putBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "put");

		// Generate token via this end point https://restful-booker.herokuapp.com/auth
		String token = given().log().all().header("Content-Type", "application/json").body(authBody).when()
				.post("https://restful-booker.herokuapp.com/auth").then().log().all().extract().jsonPath().get("token");

		// Create booking and save booking id via this end point
		// https://restful-booker.herokuapp.com/booking
		int bookingId = given().log().all().baseUri("https://restful-booker.herokuapp.com").basePath("/booking")
				.contentType(ContentType.JSON).body(postBody).when().post().then().log().all().extract().jsonPath()
				.get("bookingid");

		// Update booking id via this end point
		// https://restful-booker.herokuapp.com/booking/{bookingId}
		Response resp = given().log().all().baseUri("https://restful-booker.herokuapp.com")
				.basePath("/booking/{bookingId}").pathParam("bookingId", bookingId)
				.header("Content-Type", "application/json").header("Accept", "application/json").cookie("token", token)
				.body(putBody).when().put().then().extract().response();

		assertEquals(resp.jsonPath().get("firstname"), "James", "firstname assertion failed in update response");
	}

	@Test
	public void createUpdateBookingPathPara2() throws IOException {

		String postBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "post");
		String authBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "auth");
		String putBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "put");

		// Generate token via this end point https://restful-booker.herokuapp.com/auth
		String token = given().log().all().header("Content-Type", "application/json").body(authBody).when()
				.post("https://restful-booker.herokuapp.com/auth").then().log().all().extract().jsonPath().get("token");

		// Create booking and save booking id via this end point
		// https://restful-booker.herokuapp.com/booking
		int bookingId = given().log().all().baseUri("https://restful-booker.herokuapp.com").basePath("/booking")
				.contentType(ContentType.JSON).body(postBody).when().post().then().log().all().extract().jsonPath()
				.get("bookingid");

		// Update booking id via this end point
		// https://restful-booker.herokuapp.com/booking/{bookingId}
		Response resp = given().log().all().pathParam("bookingId", bookingId).header("Content-Type", "application/json")
				.header("Accept", "application/json").cookie("token", token).body(putBody).when()
				.put("https://restful-booker.herokuapp.com/booking/{bookingId}").then().extract().response();

		assertEquals(resp.jsonPath().get("firstname"), "James", "firstname assertion failed in update response");
	}

	@Test
	public void createUpdateBookingPathPara3() throws IOException {

		String postBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "post");
		String authBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "auth");
		String putBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "put");

		// Generate token via this end point https://restful-booker.herokuapp.com/auth
		String token = given().log().all().header("Content-Type", "application/json").body(authBody).when()
				.post("https://restful-booker.herokuapp.com/auth").then().log().all().extract().jsonPath().get("token");

		// Create booking and save booking id via this end point
		// https://restful-booker.herokuapp.com/booking
		int bookingId = given().log().all().baseUri("https://restful-booker.herokuapp.com").basePath("/booking")
				.contentType(ContentType.JSON).body(postBody).when().post().then().log().all().extract().jsonPath()
				.get("bookingid");

		// Update booking id via this end point
		// https://restful-booker.herokuapp.com/booking/{bookingId}
		Response resp = given().log().all().baseUri("https://restful-booker.herokuapp.com").
				header("Content-Type", "application/json").header("Accept", "application/json").cookie("token", token)
				.body(putBody).when().put("/booking/" + bookingId).then().extract().response();

		assertEquals(resp.jsonPath().get("firstname"), "James", "firstname assertion failed in update response");
	}

}