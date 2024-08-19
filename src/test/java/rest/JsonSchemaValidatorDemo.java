package rest;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utility.JsonManager;

public class JsonSchemaValidatorDemo {

	@Test
	public void testSchema1() {
		// https://www.liquid-technologies.com/online-json-to-schema-converter

		Response resp = given().get("https://petstore.swagger.io/v2/store/order/2");
		System.out.println(resp.asPrettyString());
		resp.then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("testdata/JsonSchemaOfPatStoreOrder.json"));

	}

	@Test
	public void testSchema2() throws IOException {
		String reqJson = JsonManager.getJsonRequestBodyAsAString("rest/jsonschema.json", "jsonschema1");

		String str = "https://reqres.in/api/users?page=2&id=3";

		Response resp = given().log().all().get(str);

		boolean flag = JsonSchemaValidator.matchesJsonSchema(reqJson).matches(resp.asString());
		System.out.println(flag);

		boolean flag2 = JsonSchemaValidator.matchesJsonSchemaInClasspath("rest/jsonschema.json")
				.matches(resp.asString());

		System.out.println(flag2);
	}

}