package responsevalidation;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FindDataInsideListResponseUsingJsonPath {

	@Test
	public void findDiffDataFromRespUsingJsonPathITAndFindFunction() {
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
		System.out.println(getApiResponse.asPrettyString());
		JsonPath path = new JsonPath(getApiResponse.asString());

		String nameAtIndex2 = path.getString("find { it.id == '3' }.name");

		assertEquals(nameAtIndex2, "Apple iPhone 12 Pro Max",
				"Validation failed: Expected name is not found at the specified index");
		System.out.println("Validation successful: " + nameAtIndex2);

	}

	@Test
	public void findDiffDataFromRespUsingJsonPathViaIteratingList() {
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
		System.out.println(getApiResponse.asPrettyString());
		JsonPath path = new JsonPath(getApiResponse.asString());

		List<Map<Object, Object>> list = path.getList("$");

		// Iterate through the list
		for (int i = 0; i < list.size(); i++) {
			// Extract fields
			String name = (String) list.get(i).get("name");
			String id = (String) list.get(i).get("id");
			Map<Object, Object> data = (Map<Object, Object>) list.get(i).get("data");

			// Print details for debugging
			System.out.println("name: " + name + " at id " + id);

			// Perform validation only at the specified index
			if (i == 2) {
				assertEquals(name, "Apple iPhone 12 Pro Max",
						"Validation failed: Expected name is not found at the specified index");
				System.out.println("Data at index 3: " + data);
			}
		}
	}
}