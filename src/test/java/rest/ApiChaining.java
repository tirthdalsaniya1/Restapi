package rest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utility.JsonManager;

public class ApiChaining {
	/*
	 * Refer request body and other api details from below website
	 * https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-
	 * CreateBooking Refer Q no 16 from website >>
	 * https://automationqahub.com/latest-rest-assured-interview-questions/
	 */
	@Test
	public void createBooking(ITestContext context) throws IOException {

		String postBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "post");
		String authBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "auth");

		// Generate token via end point https://restful-booker.herokuapp.com/auth
		String token = given().log().all().header("Content-Type", "application/json").body(authBody).when()
				.post("https://restful-booker.herokuapp.com/auth").then().log().all().extract().jsonPath().get("token");

		/*
		 * Create booking and save booking id via this end point
		 * https://restful-booker.herokuapp.com/booking
		 */
		int bookingId = given().log().all().baseUri("https://restful-booker.herokuapp.com").basePath("/booking")
				.contentType(ContentType.JSON).body(postBody).when().post().then().log().all().extract().jsonPath()
				.get("bookingid");

		// Storing bookingId and token in a context to use for other tests
		context.setAttribute("bookingId", bookingId);
		context.setAttribute("token", token);

	}

	@Test(dependsOnMethods = "createBooking")
	public void updateBooking(ITestContext context) throws IOException {
		String putBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "put");

		// Fetch booking id and token using ItestContext interface method getAttribute
		int bookingId = (Integer) context.getAttribute("bookingId");
		String token = context.getAttribute("token").toString();
		/*
		 * Update booking id via this end point //
		 * https://restful-booker.herokuapp.com/booking/{bookingId}
		 */
		Response resp = given().log().all().baseUri("https://restful-booker.herokuapp.com")
				.basePath("/booking/{bookingId}").pathParam("bookingId", bookingId)
				.header("Content-Type", "application/json").header("Accept", "application/json").cookie("token", token)
				.body(putBody).when().put().then().extract().response();

		assertEquals(resp.jsonPath().get("firstname"), "James", "firstname assertion failed in update response");
	}

}