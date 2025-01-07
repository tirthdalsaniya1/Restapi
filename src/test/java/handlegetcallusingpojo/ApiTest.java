package handlegetcallusingpojo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class ApiTest {
	public static void main(String[] args) {
		// GET API Call
		Response response = RestAssured.get("https://reqres.in/api/users?page=2");

		// Validate Status Code
		if (response.getStatusCode() == 200) {
			System.out.println("Status Code: 200 OK");
		} else {
			System.out.println("API call failed with status code: " + response.getStatusCode());
			return;
		}

		// Deserialize JSON to POJO
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ApiResponse apiResponse = objectMapper.readValue(response.getBody().asString(), ApiResponse.class);

			// Validate Fields
			System.out.println("Page: " + apiResponse.getPage());
			System.out.println("Total Users: " + apiResponse.getTotal());

			// Print User Data
			List<Data> users = apiResponse.getData();
			for (Data user : users) {
				System.out.println("ID: " + user.getId());
				System.out.println("Name: " + user.getFirst_name() + " " + user.getLast_name());
				System.out.println("Email: " + user.getEmail());
				System.out.println("Avatar: " + user.getAvatar());
				System.out.println("-----------");
			}

			// Validate Support Details
			Support support = apiResponse.getSupport();
			System.out.println("Support URL: " + support.getUrl());
			System.out.println("Support Text: " + support.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
