package rest;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PetOrderDeSerializeObjects {

	@Test
	public void createPetOrderJSONFromPetOrderPOJOClass() throws JsonProcessingException {

		String url = "https://petstore.swagger.io/v2/store/order/2";

		PetOrderResponsePojo peto = given().log().all().get(url).as(PetOrderResponsePojo.class);
		
		System.out.println(peto);

	}

}
