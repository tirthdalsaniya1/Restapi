package rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import utility.JsonManager;

public class UpdateJsonRequestBody4 {

	public static void main(String[] args) throws Exception {
		// Example JSON
		String jsonString = JsonManager.getStringfromJson("rest/trial.json", "complexJson");

		// Update the JSON elements
		String updatedJson = updateJsonElement(jsonString, "/address", "5432100000000", "zipcode");
		updatedJson = updateJsonElement(updatedJson, "/children/0", "6000000", "age");
		updatedJson = updateJsonElement(updatedJson, "/spouse/contacts/0", "home00000000", "type");
		updatedJson = updateJsonElement(updatedJson, "/pets/0", "Kitten0000000", "type");
		updatedJson = updateJsonElement(updatedJson, "/address/coordinates", "-74.006100000000", "longitude");
		updatedJson = updateJsonElement(updatedJson, "/spouse", "Jane Smith00000000", "name");
		updatedJson = updateJsonElement(updatedJson, "/spouse/contacts/1", "987-654-321000000000", "number");
		updatedJson = updateJsonElement(updatedJson, "/pets/1", "Labrador000000000", "type");
		updatedJson = updateJsonElement(updatedJson, "/emails/1", "newemail@example.com000000", "");
		updatedJson = updateJsonElement(updatedJson, "/children/0/hobbies/1", "Gaming0000000", "");

		// Print the final updated JSON
		System.out.println(prettyPrintJson(updatedJson));
	}

	private static String updateJsonElement(String json, String path, String value, String fieldName) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode node = rootNode.at(path);

        if (!node.isMissingNode()) {
            if (!fieldName.isEmpty()) {
                ((ObjectNode) node).put(fieldName, value);
            } else {
                if (node.isArray()) {
                    ArrayNode arrayNode = (ArrayNode) node;
                    arrayNode.add(value);
                }
            }
        }

        return mapper.writeValueAsString(rootNode);
    }
	
	private static String prettyPrintJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode jsonNode = mapper.readTree(json);
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonNode);
    }
}
