package rest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utility.JsonManager;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.config.LogConfig.logConfig;

import files.ReUsableMethods;
import files.Payload;

import org.testng.Assert;

public class PostExample {

	public static void main(String[] args) {
		// validate if Add Place API is workimg as expected
		// Add place-> Update Place with New Address -> Get Place to validate if New
		// address is present in response

		// given - all input details
		// when - Submit the API -resource,http method
		// Then - validate the response
		String createaddJson = JsonManager.getStringfromJson("testdata/PostBody.json", "CreateAddress");

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given()
				.config(config()
						.logConfig(logConfig().blacklistHeader("Content-Type").blacklistHeader("Accept")))
				.log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.header("Accept", "*/*").body(createaddJson).when().post("maps/api/place/add/json").then().assertThat()
				.statusCode(200).extract().response().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response); // for parsing Json
		String placeId = js.getString("place_id");

		System.out.println(placeId);

		// Update Place
		String newAddress = "Summer Walk, Africa";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get Place

		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, "Summer Walk, Africa");
		// Cucumber Junit, Testng

	}

}