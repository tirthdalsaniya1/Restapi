package responsevalidation;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class FindDataInsideComplexResponseWhichInTheFormOfList {

	@Test
	public void findDiffDataFromRespUsingObjectMapper() throws JsonMappingException, JsonProcessingException {
		/*
		 * Refer json file samplegetcallresponse.json incase if
		 * https://api.restful-api.dev/objects url is not working
		 */

		// Step 1: Send a GET request to the specified endpoint and store the response
		Response resp = given().get("https://api.restful-api.dev/objects");

		// Step 2: Create an ObjectMapper instance to parse the JSON response
		ObjectMapper mapper = new ObjectMapper();

		// Step 3: Parse the response body into a JsonNode object for easy traversal
		JsonNode respBody = mapper.readTree(resp.asString());
		System.out.println("Parsed Response Body:\n" + respBody.toPrettyString());

		// Step 4: Iterate through the array of objects in the JSON response
		for (JsonNode fields : respBody) {
			// Extract the "id" field from the current JSON object
			int id = fields.get("id").asInt();

			// Extract the "name" field from the current JSON object
			String name = fields.get("name").asText();

			// Initialize "capacity" to "Unknown" in case the field is missing or null
			String capacity = "Unknown";

			// Extract the "data" object if it exists
			JsonNode dataNode = fields.get("data");

			// Step 5: Check if the "data" object exists and extract the "Capacity" field
			if (dataNode != null) {
				// Look for "Capacity" key (case-sensitive)
				if (dataNode.has("Capacity")) {
					capacity = dataNode.get("Capacity").asText();
				}
				// Handle variations like "Capacity GB"
				else if (dataNode.has("Capacity GB")) {
					capacity = dataNode.get("Capacity GB").asText();
				}
			}

			// Step 6: Filter objects where "id" is greater than 11 and print their details
			if (id > 11) {
				System.out.println("id is " + id + " for name " + name + " and its capacity is " + capacity);
			}
		}
	}
}