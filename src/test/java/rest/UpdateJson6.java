package rest;

import java.io.IOException;
import java.util.Arrays;

import org.testng.annotations.Test;

import utility.JsonManager;

public class UpdateJson6 {

	@Test
	public void test() throws IOException {
		String jsonInput = JsonManager.getJsonRequestBodyAsAString("rest/complexJson.json", "complexjson");

		String arrayElementsNeedtoAdd[] = { "Test1", "Test2" };
		String arrayElementsNeedtoRemove1[] = { "PDF", "DOC" };

		jsonInput = JsonManager.updateRequestBodyDataUsingJsonPath(jsonInput, "", "name", "Ajit Kumar");
		jsonInput = JsonManager.updateRequestBodyDataUsingJsonPath(jsonInput, "/schoolAttributes", "schoolID",
				"newSchool123");

		jsonInput = JsonManager.deleteRequestBodydataUsingJsonPath(jsonInput, "", "validChannels");
		jsonInput = JsonManager.deleteArrayElementsFromJsonArray(jsonInput,
				"/studentPreferences/studentDocumentDetails", "validDocumentFormats",
				Arrays.asList(arrayElementsNeedtoRemove1));

		jsonInput = JsonManager.addArrayElementInsideJsonArray(jsonInput, "/studentPreferences/studentDocumentDetails",
				"validDocumentFormats", Arrays.asList(arrayElementsNeedtoAdd));

		System.out.println(jsonInput);

	}
}
