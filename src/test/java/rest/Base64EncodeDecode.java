package rest;

import java.util.Base64;

import org.testng.annotations.Test;

public class Base64EncodeDecode {

	@Test
	public void testBase64EncodeDecode() {

		String username = "myUsername";
		String password = "myPassword";
		String credentials = username + ":" + password;
		String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

		// Use encodedCredentials in the Authorization header of your HTTP request
		System.out.println("Authorization: Basic " + encodedCredentials);

		byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
		String decodedString = new String(decodedBytes);
		System.out.println("Decoded string: " + decodedString);
	}
}
