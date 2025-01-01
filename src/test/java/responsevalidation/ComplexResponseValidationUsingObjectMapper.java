package responsevalidation;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class ComplexResponseValidationUsingObjectMapper {

	@Test
	public void responseValidationWhenThereIsListUsingObjectMapper() throws JsonMappingException, JsonProcessingException {
		//Note: sample response stored in the file getcallsampleresponse.json
		String url = "https://reqres.in/api/users?page=2";

		Response resp = given().log().all().get(url).then().log().all().extract().response();

		assertEquals(resp.getStatusCode(), 200, "Issue with get call");

		ObjectMapper mapper = new ObjectMapper();

		JsonNode node = mapper.readTree(resp.asPrettyString());

		JsonNode dataNode = node.get("data");

		int size = dataNode.size();

		for (int i = 0; i < size; i++) {

			JsonNode idNode = dataNode.get(i).get("id");
			JsonNode firstName = dataNode.get(i).get("first_name");

			// print id when id number = 11
			if (idNode.toPrettyString().equals("11")) {
				System.out.println("idnode data when id = " + idNode + " & first_name is " + firstName);

			}
			// print id when id number < 8
			if (idNode.asInt() < 8) {
				System.out.println("idnode data when id < " + idNode + " & first_name is " + firstName);

			}
		}

	}

}