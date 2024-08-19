package soap;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import utility.XMLManager;

public class SoapDemo {

	@Test
	public void testSoap() {
		// https://www.crcind.com/csp/samples/SOAP.Demo.cls
		Response response = RestAssured.given()
				.get("https://www.crcind.com/csp/samples/SOAP.Demo.cls?soap_method=FindPerson&id=1");

		// Ensure we have a 200 OK response
		response.then().statusCode(200);
		System.out.println(response.asPrettyString());

		// Using XmlPath to parse the SOAP XML response
		XmlPath xmlPath = new XmlPath(response.asString());

		// Correctly specifying XML path taking into account the possible namespaces and
		// structure
		String name = xmlPath.getString("Envelope.Body.FindPersonResponse.FindPersonResult.Name");
		String ssn = xmlPath.getString("Envelope.Body.FindPersonResponse.FindPersonResult.SSN");
		String dob = xmlPath.getString("Envelope.Body.FindPersonResponse.FindPersonResult.DOB");
		String street = xmlPath.getString("Envelope.Body.FindPersonResponse.FindPersonResult.Home.Street");
		String city = xmlPath.getString("Envelope.Body.FindPersonResponse.FindPersonResult.Home.City");
		String state = xmlPath.getString("Envelope.Body.FindPersonResponse.FindPersonResult.Home.State");
		int zip = xmlPath.getInt("Envelope.Body.FindPersonResponse.FindPersonResult.Home.Zip");
		int age = xmlPath.getInt("Envelope.Body.FindPersonResponse.FindPersonResult.Age");
		String favoriteColor = xmlPath
				.getString("Envelope.Body.FindPersonResponse.FindPersonResult.FavoriteColors.FavoriteColorsItem");

		// Printing out extracted data for verification
		System.out.println("Name: " + name);
		System.out.println("SSN: " + ssn);
		System.out.println("DOB: " + dob);
		System.out.println("Address: " + street + ", " + city + ", " + state + ", " + zip);
		System.out.println("Favorite Color: " + favoriteColor);
		System.out.println("Age: " + age);

		// More detailed assertions could be done as below
		response.then().body("Envelope.Body.FindPersonResponse.FindPersonResult.Name", equalTo("Newton,Dave R."));
		assertEquals(xmlPath.getString("Envelope.Body.FindPersonResponse.FindPersonResult.Name"), "Newton,Dave R.",
				"Issue in FindPersonResult name validation");

	}

	@Test
	public void testSoap1() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		String url = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso";

		Document demoXml = XMLManager.getXmlDocument("soap/demo.xml");
		demoXml.getElementsByTagName("dNum").item(0).setTextContent("100.00");

		String inputXML = XMLManager.docToStr(demoXml);
		Response response = RestAssured.given().log().all().body(inputXML).contentType("text/xml; charset=utf-8")
				.post(url).then().log().everything().extract().response();

		// Ensure we have a 200 OK response
		assertEquals(response.statusCode(), 200, "Incorrect status code");

		// Using XmlPath to parse the SOAP XML response and verify it
		XmlPath xmlPath = new XmlPath(response.asString());
		assertEquals(xmlPath.getString("Envelope.Body.NumberToDollarsResponse.NumberToDollarsResult"),
				"one hundred dollars", "Issue in number to dollar validation");

	}

	@Test
	public void testSoap2() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		String url = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso";

		Document demoXml = XMLManager.getXmlDocument("soap/demo.xml");
		demoXml.getElementsByTagName("dNum").item(0).setTextContent("100.00");

		String inputXML = XMLManager.docToStr(demoXml);
		Response response = RestAssured.given().log().all().body(inputXML).contentType("text/xml; charset=utf-8")
				.post(url).then().log().everything().extract().response();

		// Ensure we have a 200 OK response
		assertEquals(response.statusCode(), 200, "Incorrect status code");

		// Get String data from Response using xml tag name
		Document doc = XMLManager.strToDoc(response.asString());
		String responseTagData = doc.getElementsByTagName("m:NumberToDollarsResult").item(0).getTextContent();

		System.out.println("number to dollar result is::--- " + responseTagData);
		assertEquals(responseTagData, "one hundred dollars", "Issue in number to dollar validation");

	}

}