package com.countryservice.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.restservice.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { ControllerMockMvcTests.class})
public class ControllerMockMvcTests {

	
	@Autowired
	MockMvc mvc;
	
	@Mock
	CountryService countryservice;
	
	@InjectMocks
	CountryController countrycontroller;
	
	List<Country> mycountries;
	Country country;
	
	@BeforeEach
	public void setUp() {
		mvc= MockMvcBuilders.standaloneSetup(countrycontroller).build();
	}
	
	@Test
	@Order(1)
	public void test_getAllCountries() throws Exception {
		
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India","Delhi"));
		mycountries.add(new Country(2, "UK", "London"));
		
		
		when(countryservice.getAllCountries()).thenReturn(mycountries);
		
		     mvc.perform(MockMvcRequestBuilders
			 .get("/getcountries"))
		     .andExpect(MockMvcResultMatchers.status().isOk())
             .andDo(print()); 
		 
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() throws Exception {
	
		Country country = new Country(2, "USA", "Washington");
		int countryid=2;
		
		when(countryservice.getCountryById(countryid)).thenReturn(country);
		
		mvc.perform(MockMvcRequestBuilders.get("/getcountries/{id}", countryid))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countrycapital").value("Washington"))
		.andDo(print());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() throws Exception {
	
		Country country = new Country(2, "USA", "Washington");
		String countryName="USA";
		
		when(countryservice.getCountrybyName(countryName)).thenReturn(country);
		
		mvc.perform(MockMvcRequestBuilders.get("/getcountries/countryname").param("name", "USA"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countrycapital").value("Washington"))
		.andDo(print());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() throws Exception {
		
		Country country = new Country(4, "Japan", "Tokyo");
		
		when(countryservice.addCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsbody = mapper.writeValueAsString(country);
		
		mvc.perform(MockMvcRequestBuilders.post("/addcountry").content(jsbody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(print());
		
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() throws Exception {
		
		Country country = new Country(4, "Japan", "Rome");
		int countryid=4;
		
		when(countryservice.getCountryById(countryid)).thenReturn(country);
		when(countryservice.updateCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsbody = mapper.writeValueAsString(country);
		
		mvc.perform(MockMvcRequestBuilders.put("/updatecountry/{id}", countryid )
				.content(jsbody)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countrycapital").value("Rome"))
		.andDo(print());
		
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() throws Exception {
		
		Country country = new Country(4, "Japan", "Rome");
		int countryid=4;
		
		when(countryservice.getCountryById(countryid)).thenReturn(country);
		
		mvc.perform(MockMvcRequestBuilders.delete("/deleteCountry/{id}", countryid ))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countrycapital").value("Rome"))
		.andDo(print());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
