package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import io.restassured.response.Response;

public class JsonManager {
	private static GsonBuilder builder = new GsonBuilder();

	private static Gson gson = builder.setPrettyPrinting().create();

	// Receives file path, object name and returns Map of the data
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static LinkedTreeMap<String, String> getMapfromJson(String filePath, String testDataName) {
		Reader reader = new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath), StandardCharsets.UTF_8);

		Type type = new TypeToken<Map<String, ?>>() {
		}.getType();

		Map<String, ?> fromJson = gson.fromJson(reader, type);

		return (LinkedTreeMap) fromJson.get(testDataName);

	}

	public static String getStringfromJson(String filePath, String testDataName) {
		Reader reader = new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath), StandardCharsets.UTF_8);

		JsonObject fromJson = gson.fromJson(reader, JsonObject.class);

		return fromJson.get(testDataName).toString();

	}

	public static String getJsonRequestBodyAsAString(String filePath, String jsobObjectName) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		InputStream inputstream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		 if (inputstream == null) {
		        throw new FileNotFoundException("Resource not found: " + filePath);
		    }
		JsonNode body = mapper.readTree(inputstream).get(jsobObjectName);
		return body.toString();

	}

	public static String updateRequestBodyDataUsingJsonPath(String requestJson, String path, String fieldNameToUpdate,
			String updatedFieldValue) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode requestBody = objectMapper.readTree(requestJson);

		// Traverse the JSON structure to reach the desired path
		JsonNode nodeToUpdate = requestBody.at(path);

		// Update the field in the existing object
		if (nodeToUpdate.isObject()) {
			((ObjectNode) nodeToUpdate).put(fieldNameToUpdate, updatedFieldValue);
		} else {
			throw new IllegalArgumentException("Path does not point to an object node.");
		}

		return requestBody.toString();
	}

	public static String deleteRequestBodydataUsingJsonPath(String requestJson, String path,
			String jsonFieldNameToRemove) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode requestBody = objectMapper.readTree(requestJson);

		// Traverse the JSON structure to reach the desired path
		JsonNode nodeToRemove = requestBody.at(path);

		// Update the field in the existing object
		if (nodeToRemove.isObject()) {
			((ObjectNode) nodeToRemove).remove(jsonFieldNameToRemove);
		} else {
			throw new IllegalArgumentException("Path does not point to an object node.");
		}

		return requestBody.toString();
	}

	public static String deleteArrayElementsFromJsonArray(String requestJson, String path, String arrayName,
			List<String> elementListToRemove) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode requestBody = objectMapper.readTree(requestJson);

		// Traverse the JSON structure to reach the desired path
		JsonNode requestBodyNode = requestBody.at(path);

		// Update the field in the existing object
		if (requestBodyNode.isObject()) {

			if (requestBodyNode.has(arrayName) && requestBodyNode.get(arrayName).isArray()) {
				ArrayNode aNode = (ArrayNode) requestBodyNode.get(arrayName);
				for (int i = 0; i < aNode.size(); i++) {
					JsonNode jNode = aNode.get(i);
					for (String arrayElementNeedToRemove : elementListToRemove) {
						if (jNode.asText().equalsIgnoreCase(arrayElementNeedToRemove)) {
							aNode.remove(i);
							i--;
						}
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Path does not point to an Json Object node.");
		}
		return requestBody.toPrettyString();

	}

	public static String addArrayElementInsideJsonArray(String requestJson, String path, String jsonArrayName,
			List<String> elementListToAdd) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode requestBody = objectMapper.readTree(requestJson);

		// Traverse the JSON structure to reach the desired path
		JsonNode requestBodyNode = requestBody.at(path);

		// Update the field in the existing object
		if (requestBodyNode.isObject()) {

			if (requestBodyNode.has(jsonArrayName) && requestBodyNode.get(jsonArrayName).isArray()) {
				ArrayNode aNode = (ArrayNode) requestBodyNode.get(jsonArrayName);
				for (String element : elementListToAdd) {
					aNode.add(element);
				}

			}
		} else {
			throw new IllegalArgumentException("Path does not point to an Json Object node.");
		}
		return requestBody.toPrettyString();

	}

	public static Object[] ValidateFieldsInsideJsonArrayResponseBody(Response response) {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode responseBody = objectMapper.readTree(response.getBody().asString());

			// Find size of Json Objects inside Json Response Array
			int length = responseBody.size();
			Object[] array = { length, responseBody };
			return array;

		} catch (JsonProcessingException e) {
			System.out.println("error in parsing json");
			e.getMessage();
			return new Object[] { 0, null };
		}

	}

}
