package utility;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.Response;



public class RestEndPoints {
	
	public Response soapCall(String endpoint, String xmlfile) throws FileNotFoundException, IOException {

		String soapSpsBasePath = "ws/services/";
		return RestAssured.given().header("Content-Type", "text/xml").body(xmlfile).log()
				.everything().when().post(endpoint).then().log().all().extract().response();
		
	}

}
