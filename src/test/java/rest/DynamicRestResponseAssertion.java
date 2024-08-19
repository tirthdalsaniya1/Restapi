package rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.matchesPattern;
import java.time.LocalDate;

import org.testng.annotations.Test;

public class DynamicRestResponseAssertion {

	@Test
	public void testUserAge() {
		/*
		 * Basic Validation of Dynamic Numeric Value Let's say we have an endpoint that
		 * returns user information including a dynamic age field.
		 */

		// Assuming age should not be > than 100 and age should not be < than 0
		given().when().get("https://api.agify.io/?name=michael").then().statusCode(200)
				.body("age", lessThanOrEqualTo(100)).body("age", greaterThanOrEqualTo(0));
	}

	@Test
	public void testEmailFormat() {
		/*
		 * Example 2: Dynamic String Validation Using Regex Suppose the response
		 * includes a dynamic email field, and we want to validate its format
		 */
		given().baseUri("https://api.example.com").when().get("/api/users/123").then().statusCode(200).body("email",
				matchesPattern("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}"));
	}

	@Test
	public void testUserRoles() {
		/*
		 * Example 3: Validating List of Values Assuming the API returns a list of user
		 * roles and we want to ensure each role is a valid expected value.
		 */

		// Validate every role in the list
		given().baseUri("https://api.example.com").when().get("/api/users/123").then().statusCode(200).body("roles",
				everyItem(isOneOf("admin", "user", "guest")));
	}

	@Test
	public void testImportantFieldNotNull() {
		/*
		 * Example 4: Edge Case Testing for Null or Missing Values Sometimes it's
		 * critical to check if important fields are neither null nor missing.
		 */

		// Check if 'importantField' is not null
		given().get("https://api.agify.io/?name=michael").then().statusCode(200).body("age", notNullValue());
	}

	@Test
	public void testGraduationDate() {
		/*
		 * Example 5: Range and Date Checks Validating a date field to be within a
		 * certain range.
		 */

		// Checking that graduationDate is within the last 10 years
		LocalDate now = LocalDate.now();
		LocalDate tenYearsAgo = now.minusYears(10);
		given().baseUri("https://api.example.com").when().get("/api/users/123").then().statusCode(200).body(
				"graduationDate",
				allOf(greaterThanOrEqualTo(tenYearsAgo.toString()), lessThanOrEqualTo(now.toString())));
	}

}