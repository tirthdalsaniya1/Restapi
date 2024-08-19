package rest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.testng.annotations.Test;

import com.google.common.io.Files;

import io.restassured.RestAssured;

public class DownloadFileUsingRest {

	@Test
	public void downloadFile() throws IOException {
		byte[] dowloadedFile = RestAssured.given().when().get("https://reqres.in/img/faces/2-image.jpg").then()
				.extract().asByteArray();
		File targetFile = new File("src\\test\\java\\rest\\file.jpg");
		FileOutputStream outputStream = new FileOutputStream(targetFile);
		// Write the byte array to the file
		outputStream.write(dowloadedFile);

		System.out.println("Download File Size : " + dowloadedFile.length);
		outputStream.close();
	}
	
	@Test
	public void downloadFilee() throws IOException {

		byte[] arr = given().when().get("https://reqres.in/img/faces/2-image.jpg").then().extract().asByteArray();

		Files.write(arr, new File("src/test/java/rest/image.jpg"));

	}

}