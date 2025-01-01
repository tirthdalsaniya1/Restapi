package responsevalidation;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SimpleResponseValidationUsingJsonPath {

	@Test
	public void getApiResponseFieldValidationUsingJsonPath() {
		// Note: sample response stored in the file getcallsampleresponse.json
		String url = "https://reqres.in/api/users?page=2";

		Response resp = given().log().all().get(url).then().log().all().extract().response();

		assertEquals(resp.getStatusCode(), 200, "Issue with get call");

		JsonPath jpath = new JsonPath(resp.asString());

		String supportUrl = jpath.getString("support.url");
		String supportText = jpath.getString("support.text");

		System.out.println(supportUrl);
		System.out.println(supportText);

	}

}