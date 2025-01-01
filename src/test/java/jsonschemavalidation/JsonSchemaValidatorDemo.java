package jsonschemavalidation;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utility.JsonManager;

public class JsonSchemaValidatorDemo {

	@Test
	public void testSchema1() {
		// https://www.liquid-technologies.com/online-json-to-schema-converter
		String url = "https://reqres.in/api/users?page=2";

		Response resp = given().get(url);
		assertEquals(resp.getStatusCode(), 200, "Issue with get call");

		resp.then().assertThat().body(JsonSchemaValidator
				.matchesJsonSchemaInClasspath("jsonschemavalidation/JsonSchemaOfGetCall.json"));

	}

	@Test
	public void testSchema2() throws IOException {
		String reqJson = JsonManager.getJsonRequestBodyAsAString("jsonschemavalidation/jsonschema.json", "jsonschema1");

		String str = "https://reqres.in/api/users?page=2&id=3";

		Response resp = given().log().all().get(str);

		boolean flag = JsonSchemaValidator.matchesJsonSchema(reqJson).matches(resp.asString());
		System.out.println("schema is matched " + flag);

		boolean flag2 = JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonschemavalidation/jsonschema.json")
				.matches(resp.asString());

		System.out.println("schema is matched " + flag2);
	}

}