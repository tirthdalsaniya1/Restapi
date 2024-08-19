package rest;

import static io.restassured.RestAssured.*;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

public class GetCookies {
	
	    @Test
	    public void fetchCookies() {
	        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

	        // Send a GET request
	        Response response = given()
	                                .when()
	                                .get("/posts/1");

	        // Fetch cookies from the response
	        Cookies cookies = (Cookies) response.getCookies();

	        // Print each cookie
	        for (Cookie cookie : cookies) {
	            System.out.println("Cookie Name: " + cookie.getName());
	            System.out.println("Cookie Value: " + cookie.getValue());
	        }
	    }
}
