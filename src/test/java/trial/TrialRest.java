package trial;

import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TrialRest {

	@Test
	public void test123() throws IOException {

		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("./testdata/testdata.json");

		ObjectMapper mapper = new ObjectMapper();

		JsonNode node = mapper.readTree(stream);

		JsonNode postNode = node.get("post");

		ObjectNode pobj = (ObjectNode) postNode;

		JsonNode bObj = postNode.get("bookingdates");

		ObjectNode obj = (ObjectNode) bObj;

		obj.put("k1", "v1");
		pobj.remove("totalprice");
		System.out.println(postNode.toPrettyString());

	}

}