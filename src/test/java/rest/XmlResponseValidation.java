package rest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;

public class XmlResponseValidation {

	@Test
	public void testXmlResponse() throws IOException {

		//Response responseInXml = RestAssured.given().get();

	//	String responseInXml = Files.readString(Path.of("src\\test\\resources\\xmlresponse.xml"));
		String responseInXml = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\xmlresponse.xml")));

		
		// Convert the response to XmlPath for easy validation
		XmlPath xmlPath = new XmlPath(responseInXml);

		// Test case 1: Validate the status element
		assertEquals(xmlPath.getString("response.status"), "success");

		// Test case 2: Validate the user count
		int userCount = xmlPath.getList("response.data.user").size();
		assertEquals(userCount, 2);

		// Test case 3: Validate the details of a specific user
		assertEquals(xmlPath.getString("response.data.user[0].id"), "123");
		assertEquals(xmlPath.getString("response.data.user[0].name"), "John Doe");
		assertEquals(xmlPath.getString("response.data.user[0].email"), "john.doe@example.com");

		// Test case 4: Validate the roles of users
		assertTrue(xmlPath.getList("response.data.user[0].roles.role").contains("admin"));
		assertTrue(xmlPath.getList("response.data.user[0].roles.role").contains("user"));
		assertTrue(xmlPath.getList("response.data.user[1].roles.role").contains("Guest"));
	}

}
