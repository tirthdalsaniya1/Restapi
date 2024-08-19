package rest;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class PetOrderSerializeObjects {

	@Test
	public void createPetOrderJSONFromPetOrderPOJOClass() throws JsonProcessingException {
		/*
		  String body = "{\r\n" + "  \"id\": 0,\r\n" + "  \"petId\": 0,\r\n" +
		  "  \"quantity\": 0,\r\n" +
		  "  \"shipDate\": \"2024-05-12T11:37:41.466Z\",\r\n" +
		  "  \"status\": \"placed\",\r\n" + "  \"complete\": true\r\n" + "}";
		 */

		// Just create an object of Pojo class and set value as per your wish
		PetOrder pet = new PetOrder();
		pet.setId(1);
		pet.setPetId(1);
		pet.setQuantity(1);
		pet.setShipDate("2024-05-12T11:37:41.466+0000");
		pet.setStatus("placed");
		pet.setComplete(true);

		// Converting a Java class object to a JSON payload as string
		ObjectMapper mapper = new ObjectMapper();

		String body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pet);
		System.out.println(body);

		Response response = given().body(body).contentType("application/json").when()
				.post("https://petstore.swagger.io/v2/store/order");
		System.out.println(response.asPrettyString());

	}

	@Test
	public void getPojoFromPetOrderObject() throws JsonProcessingException {

		PetOrder pet1 = new PetOrder();
		pet1.setId(0);
		pet1.setPetId(0);
		pet1.setQuantity(0);
		pet1.setShipDate("2024-05-12T11:37:41.466+0000");
		pet1.setStatus("placed");
		pet1.setComplete(true);

		// Converting a Java class object to a JSON payload as string
		ObjectMapper mapper = new ObjectMapper();

		String body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pet1);
		System.out.println(body);

		// Converting PerOrder json string to PerOrder class object
		PetOrder pet2 = mapper.readValue(body, PetOrder.class);
		System.out.println("id is " + pet2.getId());
		System.out.println("petId : " + pet2.getPetId());
		System.out.println("pet Quantity : " + pet2.getQuantity());
		System.out.println("ship date of pet : " + pet2.getShipDate());
		System.out.println("status of order : " + pet2.getStatus());

	}

}
