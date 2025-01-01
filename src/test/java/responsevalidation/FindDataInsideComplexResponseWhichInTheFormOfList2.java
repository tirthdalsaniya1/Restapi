package responsevalidation;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FindDataInsideComplexResponseWhichInTheFormOfList2 {

	@Test
	public void findDiffDataFromRespUsingObjectMapper() throws JsonMappingException, JsonProcessingException {
		/*
		 * Refer json file samplegetcallresponse.json incase if
		 * https://api.restful-api.dev/objects url is not working
		 */
		/*
		 * You have to find that at index 3 you are getting name Apple iPhone 12 Pro Max
		 */
		Response getApiResponse = given().baseUri("https://api.restful-api.dev").basePath("/objects")
				.contentType(ContentType.JSON).get().then().extract().response();

		assertEquals(getApiResponse.getStatusCode(), 200, "Issue with get api call status code verification");

		ObjectMapper mapper = new ObjectMapper();

		JsonNode body = mapper.readTree(getApiResponse.asPrettyString());

		System.out.println(body.toPrettyString());

		String name = body.get(2).get("name").asText();

		assertEquals(name, "Apple iPhone 12 Pro Max", "Apple iPhone 12 Pro Max name assertion failed");

	}
}