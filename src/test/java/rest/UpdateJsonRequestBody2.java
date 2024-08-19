package rest;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import utility.JsonManager;

public class UpdateJsonRequestBody2 {

	@Test
	public void updateJsonRequestBodyTest() throws JsonMappingException, JsonProcessingException {

		String reqBody = JsonManager.getStringfromJson("rest/trial.json", "Test");

		ObjectMapper mapper = new ObjectMapper();

		// read request body
		JsonNode requestJsonBody = mapper.readTree(reqBody);

		JsonNode nodeToUpdate = requestJsonBody.at("/Location/state");
		
		if(nodeToUpdate.isObject()) {
			ObjectNode node = (ObjectNode) nodeToUpdate;
			node.put("state", "JaneJane");
		}
		else {
			throw new IllegalArgumentException("incorrect path");
		}

		System.out.println(requestJsonBody.toPrettyString());
	}
	
	@Test
	public void updateJsonRequestBodyInTest() throws JsonMappingException, JsonProcessingException {
	    String reqBody = JsonManager.getStringfromJson("rest/trial.json", "Test");
	    ObjectMapper mapper = new ObjectMapper();

	    // read request body
	    JsonNode requestJsonBody = mapper.readTree(reqBody);

	    // get the Location array
	    JsonNode locations = requestJsonBody.get("Location");
	    if (locations.isArray()) {
	        for (int i = 0; i < locations.size(); i++) {
	            JsonNode location = locations.get(i);
	            if (location.isObject()) {
	                String city = location.get("city").asText();
	                if ("Surat".equals(city)) { // Assuming you want to update the state for city "Pune"
	                    ((ObjectNode) location).put("state", "NewState"); // Replace "NewState" with the desired state value
	                }
	            } else {
	                throw new IllegalArgumentException("Location element is not an object");
	            }
	        }
	    } else {
	        throw new IllegalArgumentException("Location is not an array");
	    }

	    System.out.println(requestJsonBody.toPrettyString());
	}
	

	 public static String updateJsonElement(String reqBody, String elementPath, String newValue, String fieldToUpdate ){
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            JsonNode requestJsonBody = mapper.readTree(reqBody);

	            // Get the element to update
	            JsonNode nodeToUpdate = requestJsonBody.at(elementPath);
	            if (!nodeToUpdate.isMissingNode()) {
	                // If the element exists, update its value
	                if (nodeToUpdate.isObject()) {
		                   ((ObjectNode) nodeToUpdate).put(fieldToUpdate, newValue);

	                } else {
	                    throw new IllegalArgumentException("Element is not an object");
	                }
	            } else {
	                throw new IllegalArgumentException("Element not found at path: " + elementPath);
	            }

	            return requestJsonBody.toPrettyString();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

		@Test
	    public void updateDemo1() {
		    String reqBody = JsonManager.getStringfromJson("rest/trial.json", "Test");

	        // Example usage to update the "state" of the second location
		    String updatedJson = updateJsonElement(reqBody, "/Location/1", "Gujarat000000000","city");
		    String updatedJson1 = updateJsonElement(updatedJson, "/Location/2", "Gujarat000000000","city");
		    String updatedJson2 = updateJsonElement(updatedJson1, "/Location/2", "Gujarat000000000","city");
		    String updatedJson3 = updateJsonElement(updatedJson2, "/author", "Gujarat000000000","lastname");
		    String updatedJson4 = updateJsonElement(updatedJson3, "", "Gujarat000000000","isbn");

	        System.out.println(updatedJson4);
	    }
		
		@Test
		public void updateDemo2() {
		    String reqBody = JsonManager.getStringfromJson("rest/trial.json", "complexJson");

		    // Update the zipcode in the address
		    String updatedJson = updateJsonElement(reqBody, "/address", "54321", "zipcode");

		    // Update the age of the first child
		    String updatedJson1 = updateJsonElement(updatedJson, "/children/0", "6", "age");

		    // Update the type of the first contact in the spouse's contacts
		    String updatedJson2 = updateJsonElement(updatedJson1, "/spouse/contacts/0", "home", "type");

		    // Update the type of the first pet
		    String updatedJson3 = updateJsonElement(updatedJson2, "/pets/0", "Kitten", "type");

		  
		    // Update the longitude in the coordinates
		    String updatedJson5 = updateJsonElement(updatedJson3, "/address/coordinates", "-74.0061", "longitude");

		    // Update the name of the spouse
		    String updatedJson6 = updateJsonElement(updatedJson5, "/spouse", "Jane Smith", "name");

		  
		    // Update the number of the second contact in the spouse's contacts
		    String updatedJson8 = updateJsonElement(updatedJson6, "/spouse/contacts/1", "987-654-3210", "number");

		    // Update the type of the second pet
		    String updatedJson9 = updateJsonElement(updatedJson8, "/pets/1", "Labrador", "type");
		    
		    // Add a new email to the emails array
			 //   String updatedJson4 = updateJsonElement(updatedJson3, "/emails", "newemail@example.com", "");

		    // Add a new hobby for the second child
			//	    String updatedJson7 = updateJsonElement(updatedJson6, "/children/1/hobbies", "Gaming", "");

		    // Print the final updated JSON
		    System.out.println(updatedJson9);
		}





}
