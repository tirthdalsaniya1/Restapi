package responsevalidation;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;

public class ComplexResponseValidationUsingJsonPath {

	@Test
	public void responseValidationWhenThereIsListUsingObjectMapper()
			throws JsonMappingException, JsonProcessingException {
		// Note: sample response stored in the file getcallsampleresponse.json
		String url = "https://reqres.in/api/users?page=2";

		Response resp = given().log().all().get(url).then().log().all().extract().response();

		assertEquals(resp.getStatusCode(), 200, "Issue with get call");

		// Extract the 'data' node from the response using JsonPath
		List<Integer> ids = resp.jsonPath().getList("data.id");
		List<String> firstNames = resp.jsonPath().getList("data.first_name");

		// Iterate over the IDs and apply the conditions
		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			String firstName = firstNames.get(i);

			// Print id and first name when id = 11
			if (id == 11) {
				System.out.println("id = " + id + ", first_name = " + firstName);
			}

			// Print id and first name when id < 8
			if (id < 8) {
				System.out.println("id < 8: id = " + id + ", first_name = " + firstName);
			}
		}
	}
}