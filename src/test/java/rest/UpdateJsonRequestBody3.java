package rest;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import utility.JsonManager;

public class UpdateJsonRequestBody3 {

	
	    public static String updateJsonElement(String reqBody, String elementPath, String newValue, String fieldToUpdate) {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            JsonNode requestJsonBody = mapper.readTree(reqBody);

	            // Split the element path to navigate through the JSON structure
	            String[] pathElements = elementPath.split("/");
	            JsonNode currentNode = requestJsonBody;

	            StringBuilder currentPath = new StringBuilder();
	            for (int i = 1; i < pathElements.length; i++) {
	                String key = pathElements[i];
	                currentPath.append('/').append(key);

	                if (key.matches("\\d+")) {
	                    // Handle array index
	                    int index = Integer.parseInt(key);
	                    if (currentNode.isArray()) {
	                        currentNode = currentNode.get(index);
	                    } else {
	                        throw new IllegalArgumentException("Expected array at " + currentPath);
	                    }
	                } else {
	                    // Handle object field
	                    currentNode = currentNode.path(key);
	                }

	                if (currentNode.isMissingNode()) {
	                    throw new IllegalArgumentException("Element not found at path: " + currentPath);
	                }
	            }

	            // Update the node based on its type
	            if (currentNode.isObject()) {
	                ((ObjectNode) currentNode).put(fieldToUpdate, newValue);
	            } else if (currentNode.isArray()) {
	                // Update all elements in the array if it's a terminal array node
	                ArrayNode arrayNode = (ArrayNode) currentNode;
	                for (int i = 0; i < arrayNode.size(); i++) {
	                    ((ObjectNode) arrayNode.get(i)).put(fieldToUpdate, newValue);
	                }
	            } else if (currentNode.isValueNode()) {
	                // Update a value node (e.g., string within an array)
	                JsonNode parent = requestJsonBody.at(elementPath.substring(0, elementPath.lastIndexOf('/')));
	                if (parent.isArray()) {
	                    int index = Integer.parseInt(pathElements[pathElements.length - 1]);
	                    ((ArrayNode) parent).set(index, TextNode.valueOf(newValue));
	                } else {
	                    throw new IllegalArgumentException("Parent is not an array");
	                }
	            } else {
	                throw new IllegalArgumentException("Element is not an object, array, or value node");
	            }

	            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestJsonBody);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }


	@Test
	public void updateDemo2() {
		String reqBody = JsonManager.getStringfromJson("rest/trial.json", "complexJson");

		// Update the zipcode in the address
		String updatedJson = updateJsonElement(reqBody, "/address", "5432100000000", "zipcode");

		// Update the age of the first child
		String updatedJson1 = updateJsonElement(updatedJson, "/children/0", "6000000", "age");

		// Update the type of the first contact in the spouse's contacts
		String updatedJson2 = updateJsonElement(updatedJson1, "/spouse/contacts/0", "home00000000", "type");

		// Update the type of the first pet
		String updatedJson3 = updateJsonElement(updatedJson2, "/pets/0", "Kitten0000000", "type");

		// Update the longitude in the coordinates
		String updatedJson4 = updateJsonElement(updatedJson3, "/address/coordinates", "-74.006100000000", "longitude");

		// Update the name of the spouse
		String updatedJson5 = updateJsonElement(updatedJson4, "/spouse", "Jane Smith00000000", "name");

		// Update the number of the second contact in the spouse's contacts
		String updatedJson6 = updateJsonElement(updatedJson5, "/spouse/contacts/1", "987-654-321000000000", "number");

		// Update the type of the second pet
		String updatedJson7 = updateJsonElement(updatedJson6, "/pets/1", "Labrador000000000", "type");

		// Add a new email to the emails array
		String updatedJson8 = updateJsonElement(updatedJson7, "/emails/1", "newemail@example.com000000", "");

		// Add a new hobby for the second child
		String updatedJson9 = updateJsonElement(updatedJson8, "/children/0/hobbies/1", "Gaming0000000", "");

		// Print the final updated JSON
		System.out.println(updatedJson9);
	}
	


}
