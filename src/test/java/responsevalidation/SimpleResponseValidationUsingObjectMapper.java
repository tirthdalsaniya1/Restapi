package responsevalidation;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class SimpleResponseValidationUsingObjectMapper {

	@Test
	public void getApiResponseFieldValidationUsingObjectMapper() throws JsonMappingException, JsonProcessingException {
		// Note: sample response stored in the file getcallsampleresponse.json
		String url = "https://reqres.in/api/users?page=2";

		Response resp = given().log().all().get(url).then().log().all().extract().response();

		assertEquals(resp.getStatusCode(), 200, "Issue with get call");

		ObjectMapper mapper = new ObjectMapper();

		JsonNode node = mapper.readTree(resp.asPrettyString());

		String supportUrl = node.path("support").path("url").asText();
		String supportText = node.at("/support/text").asText();

		System.out.println(supportUrl);
		System.out.println(supportText);

	}

}