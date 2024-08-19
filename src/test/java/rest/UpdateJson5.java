package rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import utility.JsonManager;

public class UpdateJson5 {

	public static String updateRequestBodyDataUsingJsonPath(String requestJson, String path, String fieldNameToUpdate,
			Object updatedFieldValue) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode requestBody = objectMapper.readTree(requestJson);

		JsonNode nodeToUpdate = requestBody.at(path);

		if (nodeToUpdate.isObject()) {
			ObjectNode objectNode = (ObjectNode) nodeToUpdate;
			if (updatedFieldValue instanceof String) {
				objectNode.put(fieldNameToUpdate, (String) updatedFieldValue);
			} else if (updatedFieldValue instanceof Integer) {
				objectNode.put(fieldNameToUpdate, (Integer) updatedFieldValue);
			} else if (updatedFieldValue instanceof Boolean) {
				objectNode.put(fieldNameToUpdate, (Boolean) updatedFieldValue);
			} else if (updatedFieldValue instanceof JsonNode) {
				objectNode.set(fieldNameToUpdate, (JsonNode) updatedFieldValue);
			} else {
				throw new IllegalArgumentException("Unsupported data type for field value.");
			}
		} else if (nodeToUpdate.isArray()) {
			ArrayNode arrayNode = (ArrayNode) nodeToUpdate;
			arrayNode.add(updatedFieldValue.toString());
			throw new IllegalArgumentException("Provided path points to an array. Specify an object path to update.");
		} else {
			throw new IllegalArgumentException("Path does not point to an object node.");
		}

		return objectMapper.writeValueAsString(requestBody);
	}

	public static void main(String[] args) throws IOException {
		String jsonInput = JsonManager.getJsonRequestBodyAsAString("rest/complexJson.json", "complexjson");

		try {

			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "", "name", "Ajit Kumar");

			// Update direct element
			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "/schoolAttributes", "schoolID", "newSchool123");

			// Update Boolean
			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "/studentPreferences", "libraryAccess", false);

			// Update document type within studentDocumentDetails
			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "/studentPreferences/studentDocumentDetails",
					"documentType", "REGISTRATION_FORM");

			// Update zipCode within the address
			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "/guardianDetails/address", "zipCode", "62705");
			// Update complex object
			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "/guardianDetails/address", "city",
					"New Springfield");

			// Update nested element within a complex object
			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "/guardianDetails/emergencyContact",
					"primaryPhone", "1112223333");

			// Attempt to update an array index (complex element within an array)
			jsonInput = updateRequestBodyDataUsingJsonPath(jsonInput, "/academicRecordInfo/grades/0", "grade", "A+");

			System.out.println(jsonInput);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}
