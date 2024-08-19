package rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utility.JsonManager;

public class CombinationKeyValue {

	@Test
	public void hitEndPointWithUniqueKeyAndValue() throws IOException {
		String reqJson = JsonManager.getJsonRequestBodyAsAString("rest/testdata.json", "keyValuesObject");
		JsonObject jsonObject = JsonParser.parseString(reqJson).getAsJsonObject();
		jsonObject.addProperty("uniqueId", "id");

		HashMap<String, JsonElement> map = new Gson().fromJson(jsonObject, HashMap.class);

		for (Entry<String, JsonElement> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());

			String requestBody = "{" + "\"" + entry.getKey() + "\"" + ":" + "\"" + entry.getValue() + "\"" + "}";
			System.out.println(requestBody);
		}
	}
}
