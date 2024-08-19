package rest;

import java.util.HashMap;

import org.testng.annotations.Test;

public class NestedJsonObjectAsAReq {

	@Test
	public void testReq() {

		HashMap<String, Object> mainObject = new HashMap<>();
		HashMap<String, String> subObject = new HashMap<>();

		subObject.put("name", "Qa");
		subObject.put("id", "110");
		
		mainObject.put("WorkerDetails", subObject);
		
		System.out.println(mainObject.toString());

	}

}
