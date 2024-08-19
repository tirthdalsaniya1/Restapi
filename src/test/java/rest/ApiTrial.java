package rest;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiTrial {
	private static final Logger logger = LoggerFactory.getLogger(ApiTrial.class);

	@Test
	public void testTrial() {

		String url = "https://reqres.in/api/users?page=2";
		// https://reqres.in/api/users/{id}

		Response response = given().get(url).then().log().all().extract().response();

		// Extracting first names of all users with id < 10
		String str = response.jsonPath().getString("data.find{it.id==12}.email");

		System.out.println("email id is " + str);

		logger.info("This is an info message"); // This will not be logged
		logger.warn("This is a warning message"); // This will not be logged
		logger.error("This is an error message"); // This will be logged to the console
	}

	@Test
	public void deleteTrial() {

		String url = "https://reqres.in/api/users/2";

		Response response = given().log().all().delete(url).then().log().all().extract().response();

	}

	@Test
	public void testUserResponse() {
		// Setting the base URI
		RestAssured.baseURI = "https://reqres.in/api";

		// Sending the request and getting the response
		RequestSpecification request = RestAssured.given();
		Response response = request.get("/users?page=2");
		System.out.println(response.asPrettyString());

		// Assertion 1: Status Code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Correct status code returned");

		// Assertion 2: Content Type
		String contentType = response.getContentType();
		Assert.assertTrue(contentType.contains("application/json"), "Correct content type returned");

		// Assertion 3: Total Count
		int total = response.jsonPath().getInt("total");
		Assert.assertEquals(total, 12, "Correct total number of users");

		// Extracting first and last names
		List<String> firstNames = response.jsonPath().getList("data.findAll { it.id < 10 }.first_name");
		List<String> lastNames = response.jsonPath().getList("data.findAll { it.id > 7 && it.id < 10 }.last_name");

		// Assertion 4: Presence of Specific User
		List<String> emails = response.jsonPath().getList("data.email");
		Assert.assertTrue(emails.contains("tobias.funke@reqres.in"), "Specific user exists in the response");

		// Assertion 5: Non-empty Fields
		for (String firstName : firstNames) {
			Assert.assertFalse(firstName.isEmpty(), "First name is not empty");
		}
		for (String lastName : lastNames) {
			Assert.assertFalse(lastName.isEmpty(), "Last name is not empty");
		}

		// Assertion 6: Range of IDs
		List<Integer> ids = response.jsonPath().getList("data.id");
		for (Integer id : ids) {
			Assert.assertTrue(id >= 7 && id <= 12, "User ID is within the expected range");
		}

		// Printing the result
		System.out.println("First names of users with id < 10: " + firstNames);
		System.out.println("Last names of users with id between 8 and 10: " + lastNames);

		// Extracting the first user with id > 7
		Map<String, ?> firstUser = response.jsonPath().getMap("data.find { it.id > 7 }");
		System.out.println("First user with id > 7: " + firstUser);

		// Assertion: Check if the first user's id is 8
		Assert.assertEquals(firstUser.get("id"), 8);

		// Extracting all users with id > 7
		List<Map<String, ?>> users = response.jsonPath().getList("data.findAll { it.id > 7 }");
		System.out.println("All users with id > 7: " + users);

		// Assertion: Check the size of users with id > 7
		Assert.assertEquals(users.size(), 5);

		// Collecting all first names of users with id > 7
		List<String> firstNames2 = response.jsonPath().getList("data.findAll { it.id > 7 }.collect { it.first_name }");
		System.out.println("First names of users with id > 7: " + firstNames2);

		// Sum of all user ids
		int sumOfIds = response.jsonPath().getInt("data.sum { it.id }");
		System.out.println("Sum of all user ids: " + sumOfIds);

		// Assertion: Verify the sum of all user ids
		Assert.assertEquals(sumOfIds, 57);

		// Getting the size of the data array
		int dataSize = response.jsonPath().getInt("data.size()");
		System.out.println("Size of data array: " + dataSize);

		// Assertion: Verify the size of the data array
		Assert.assertEquals(dataSize, 6);
		
	}

}
