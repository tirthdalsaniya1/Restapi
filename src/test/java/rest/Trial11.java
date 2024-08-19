package rest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class Trial11 {

	@Test
	public void test() {

		String str = "https://reqres.in/api/users?page=2";

		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users";

		given().queryParam("page", 4).log().all().get().then().log().all();
	}

	@Test
	public void test1() {

		String str = "https://reqres.in/api/users?page=2";

		given().log().all().get(str).then().log().all();
	}

	@Test
	public void test2() {

		given().baseUri("https://reqres.in").basePath("/api/users").queryParam("page", 4).log().all().get().then()
				.time(lessThan(2000L)).body("page", equalTo(4)).statusCode(200)
				.body("support.url", equalTo("https://reqres.in/#support-heading")).log().all();

	}

	@Test
	public void test3() throws IOException {
		
		//	String xmlResponse = Files.readString(Path.of("src\\test\\resources\\xmlresponse.xml"));
		String xmlResponse = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\xmlresponse.xml")));

		XmlPath xmlpath = new XmlPath(xmlResponse);

		assertEquals(xmlpath.get("response.status"), "success", "Issue in status validation");
		assertEquals(xmlpath.get("response.data.user[0].id"), "123", "Issue in id 1 validation");
		assertEquals(xmlpath.get("response.data.user[1].id"), "456", "Issue in id 2 validation");
		assertEquals(xmlpath.get("response.data.user[0].roles.role[0]"), "admin", "Issue in id 2 validation");
		assertEquals(xmlpath.get("response.data.user[0].roles.role[1]"), "user", "Issue in id 2 validation");
		assertEquals(xmlpath.get("response.data.user[1].roles.role[0]"), "Guest", "Issue in id 2 validation");

	}

}