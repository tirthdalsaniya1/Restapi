package rest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import utility.JsonManager;

public class FileUploadDemo {

	@Test
	public void fileUploadTest() throws IOException {

		// Save file for upload at particular location
		String path = "src\\test\\java\\rest\\Chatgptguide.png";
		File file = new File(path);

		// Read Req body
		String petOrderBody = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "petorder");

		// Order a pet and get petId and store it in string
		Response response = given().body(petOrderBody).contentType("application/json").when()
				.post("https://petstore.swagger.io/v2/store/order").then().log().all().extract().response();

		String petId = response.jsonPath().getString("petId");

		String uploadBaseUlr = "https://petstore.swagger.io/v2/pet";

		// upload a image at url https://petstore.swagger.io/v2/pet/petId/uploadImage
		given().baseUri(uploadBaseUlr).basePath("/" + petId + "/" + "uploadImage").multiPart(file)
				.contentType("multipart/form-data").accept("application/json").when().post().then().log().all()
				.statusCode(200);

	}
	
	@Test
	public void fileUpload2() {

		String str = "https://petstore.swagger.io/v2/pet/1/uploadImage";

		File file = new File("src/test/java/rest/file.jpg");

		given().headers("Content-Type", "multipart/form-data", "accept", "application/json").multiPart(file)
				.post(str).then().statusCode(200).log().all();

	}

}
