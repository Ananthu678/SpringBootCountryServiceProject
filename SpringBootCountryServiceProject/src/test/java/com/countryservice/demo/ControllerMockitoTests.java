package com.countryservice.demo;


import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockitoTests {
	
	@Mock
	CountryService countryservice;
	
	@InjectMocks
	CountryController countrycontroller;
	
	@Test
	@Order(1)
	public void test_getAllcountries() {
		
		when(countryservice.getAllCountries()).thenReturn(Stream
				.of(new Country(1, "India", "Delhi"), new Country(2, "UK", "London")).collect(Collectors.toList()));
		ResponseEntity<List<Country>> res = countrycontroller.getCountries();
		
		assertEquals(2, res.getBody().size());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() {
		
		Country country= new Country(1,"USA","Washington");
		int countryid=1;
		
		when(countryservice.getCountryById(countryid)).thenReturn(country);
		ResponseEntity<Country> res = countrycontroller.getCountryById(countryid);
		
		assertEquals(countryid, res.getBody().getId());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		
		Country country= new Country(1,"USA","Washington");
		String countryname="USA";
		
		when(countryservice.getCountrybyName(countryname)).thenReturn(country);
		ResponseEntity<Country> res = countrycontroller.getCountryByName(countryname);
		
		assertEquals(countryname, res.getBody().getCountryName());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		
		Country country= new Country(4,"Japan","Tokyo");
		
		when(countryservice.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countrycontroller.addCountry(country);
		
		assertEquals(country, res.getBody());
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		
		Country country= new Country(4,"Japan","Tokyo");
		int countryid=4;
		
		when(countryservice.getCountryById(countryid)).thenReturn(country);
		when(countryservice.updateCountry(country)).thenReturn(country);
		
		ResponseEntity<Country> res = countrycontroller.updatecountry(countryid, country);
		
		assertEquals(countryid, res.getBody().getId());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		 
		 Country country = new Country(4, "Japan", "Tokyo");
		 int countryid=4;
		 
		 when(countryservice.getCountryById(countryid)).thenReturn(country);
		 
		 ResponseEntity<Country> res = countrycontroller.deleteCountry(countryid);
		 assertEquals(HttpStatus.OK, res.getStatusCode());
		 
	}

}
