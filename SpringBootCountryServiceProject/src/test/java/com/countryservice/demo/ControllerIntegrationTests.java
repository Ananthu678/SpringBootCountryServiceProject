package com.countryservice.demo;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;

import com.countryservice.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {
	
	@Test
	@Order(1)
    public void getAllCountriesIntegrationTest() throws JSONException
    {
		  String expected = "[\r\n"
		  		+ "    {\r\n"
		  		+ "        \"id\": 1,\r\n"
		  		+ "        \"countryName\": \"India\",\r\n"
		  		+ "        \"countrycapital\": \"Delhi\"\r\n"
		  		+ "    },\r\n"
		  		+ "    {\r\n"
		  		+ "        \"id\": 2,\r\n"
		  		+ "        \"countryName\": \"Moscow\",\r\n"
		  		+ "        \"countrycapital\": \"Russia\"\r\n"
		  		+ "    }\r\n"
		  		+ "]";
		
		  TestRestTemplate restTemplate = new TestRestTemplate();
		  ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8082/getcountries", String.class);
		  System.out.println(response.getStatusCode());
		  System.out.println(response.getBody());
		  
		  JSONAssert.assertEquals(expected, response.getBody(), false);
    }
	
	@Test
	@Order(2)
    public void getCountryByIdIntegrationTest() throws JSONException
    {
		  String expected = "{\r\n"
		  		+ "    \"id\": 2,\r\n"
		  		+ "    \"countryName\": \"Moscow\",\r\n"
		  		+ "    \"countrycapital\": \"Russia\"\r\n"
		  		+ "}";
		
		  TestRestTemplate restTemplate = new TestRestTemplate();
		  ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8082/getcountries/2", String.class);
		  System.out.println(response.getStatusCode());
		  System.out.println(response.getBody());
		  
		  JSONAssert.assertEquals(expected, response.getBody(), false);
    }
	
	@Test
	@Order(3)
    public void getCountryByNameIntegrationTest() throws JSONException
    {
		  String expected = "{\r\n"
		  		+ "    \"id\": 1,\r\n"
		  		+ "    \"countryName\": \"India\",\r\n"
		  		+ "    \"countrycapital\": \"Delhi\"\r\n"
		  		+ "}";
		
		  TestRestTemplate restTemplate = new TestRestTemplate();
		  ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8082/getcountries/countryname?name=India", String.class);
		  System.out.println(response.getStatusCode());
		  System.out.println(response.getBody());
		  
		  JSONAssert.assertEquals(expected, response.getBody(), false);
    }
	
	@Test
	@Order(4)
	public void addCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(3, "Japan", "Tokyo");
		
		String expected="{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"Japan\",\r\n"
				+ "    \"countrycapital\": \"Tokyo\"\r\n"
				+ "}";
		
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8082/addcountry", request, String.class);
		System.out.println(response.getBody());
		System.out.println(response.getStatusCode());
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
	@Test
	@Order(5)
	public void updateCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(3, "Germany", "Berlin");
		
		String expected="{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"Germany\",\r\n"
				+ "    \"countrycapital\": \"Berlin\"\r\n"
				+ "}";
		
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/updatecountry/3", HttpMethod.PUT, request, String.class);
		System.out.println(response.getBody());
		System.out.println(response.getStatusCode());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}

	@Test
	@Order(6)
	public void deleteCountryIntegrationTest() throws JSONException {
		
		Country country = new Country(3, "Germany", "Berlin");
		
		String expected="{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"Germany\",\r\n"
				+ "    \"countrycapital\": \"Berlin\"\r\n"
				+ "}";
		
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/deleteCountry/3", HttpMethod.DELETE, request, String.class);
		System.out.println(response.getBody());
		System.out.println(response.getStatusCode());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}

}
