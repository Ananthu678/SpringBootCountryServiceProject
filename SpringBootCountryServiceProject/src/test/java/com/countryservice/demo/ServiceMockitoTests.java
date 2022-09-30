package com.countryservice.demo;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {ServiceMockitoTests.class})
public class ServiceMockitoTests {
	
	@Mock
	CountryRepository countryrep;
	
	@InjectMocks
	CountryService countryservice;
	
	@Test
	@Order(1)
	public void test_getAllCountries()
	{
		when(countryrep.findAll()).thenReturn(Stream
				.of(new Country(1, "India", "Delhi"), new Country(2, "UK", "London")).collect(Collectors.toList()));
		assertEquals(2, countryservice.getAllCountries().size());
	}
	
	@Test 
	@Order(2)
	public void test_getCountryById()
	{
		int countryid=1;
		when(countryrep.findAll()).thenReturn(Stream
				.of(new Country(1, "India", "Delhi"), new Country(2, "UK", "London")).collect(Collectors.toList()));
		
		assertEquals(1, countryservice.getCountryById(countryid).getId());
	}
	
	@Test @Order(3)
	public void test_getCountryByName()
	{
		String countryname="India";
		when(countryrep.findAll()).thenReturn(Stream
				.of(new Country(1, "India", "Delhi"), new Country(2, "UK", "London")).collect(Collectors.toList()));
		
		assertEquals(countryname, countryservice.getCountrybyName(countryname).getCountryName());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		Country country = new Country(3,"Italy","Rome");
		when(countryrep.save(country)).thenReturn(country);
		
		assertEquals(country, countryservice.addCountry(country));
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		Country country = new Country(3,"Italy","Rome");
		when(countryrep.save(country)).thenReturn(country);
		
		assertEquals(country, countryservice.updateCountry(country));
	}
	
	
	public void test_deleteCountry() {
		Country country = new Country(3,"Italy","Rome");
		countryservice.deleteCountry(country);
		verify(countryrep, times(1)).delete(country);
		
	}
	

}
