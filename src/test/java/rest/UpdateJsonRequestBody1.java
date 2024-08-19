package rest;

import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utility.JsonManager;

public class UpdateJsonRequestBody1 {

	@Test
	public void updateJsonRequestBodyTest() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String reqBody = JsonManager.getStringfromJson("rest/trial.json", "Test");
		JsonObject jo = JsonParser.parseString(reqBody).getAsJsonObject();
		System.out.println("Json body before update");
		String str1 = gson.toJson(jo);
		System.out.println(str1);

		// update isbn field and add new data which are directly accessible
		jo.addProperty("isbn", "100");
		jo.addProperty("newDate", "2020-01-01");
		jo.addProperty("newName", "NewName");

		// Update the author's first name
		JsonObject author = jo.getAsJsonObject("author");
		author.addProperty("firstname", "VictoriaVictoria");

		// Update the city in the second location in the array/list
		JsonArray locations = jo.getAsJsonArray("Location");
		JsonObject secondLocation = locations.get(1).getAsJsonObject();
		secondLocation.addProperty("city", "USAUSAUSA");
		
		// Update the category array
        JsonArray categoryArray = jo.getAsJsonArray("category");
        categoryArray.remove(1);

		String str = gson.toJson(jo);
		System.out.println("Json body after update");
		System.out.println(str);
	}
	
	

	public static String getPreetyString(JsonObject jo) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String str = gson.toJson(jo);

		return str;

	}
	
	@Test
	public void updateReqBody12() throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("rest/trial.json");

		JsonNode node = mapper.readTree(stream).get("Test");
		String body = node.toPrettyString();
		System.out.println(body);
		
		JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
		jsonObject.addProperty("isbn", "123-456-2228888");
		JsonObject jo = jsonObject.getAsJsonObject("author");
		jo.addProperty("lastname", "111");
		JsonArray array = jsonObject.getAsJsonArray("Location");
		array.get(1).getAsJsonObject().addProperty("state", "uuuuuuuuuu");
		array.get(1).getAsJsonObject().addProperty("city", "uuuuuuuuuu");
		jsonObject.getAsJsonArray("category").remove(0);
		jsonObject.getAsJsonArray("category").add("kkkkkkkkkkkkkkk");
		System.out.println(jsonObject.toString());

	}

}
