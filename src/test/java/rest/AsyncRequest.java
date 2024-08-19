package rest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.testng.annotations.Test;

public class AsyncRequest {

	@Test
	public void asyncTest() throws IOException, InterruptedException, ExecutionException {
		// Use async-http-client dependency for this feature
		
		String url = "https://reqres.in/api/users?delay=15";
		
		Future<Response> futureResp = Dsl.asyncHttpClient().prepareGet(url).execute();
		Response resp = futureResp.get();
		System.out.println(resp.getResponseBody());
	}

}
