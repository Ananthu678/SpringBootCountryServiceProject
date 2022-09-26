package com.countryservice.demo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country")
public class Country {
   
	 @Id
	 @Column(name="id")
	 int id;
	 
	 @Column(name="country_name")
	 String countryName;
	 
	 @Column(name="capital")
	 String countrycapital;
	 
	 public Country() {
		 
	 }
	 
	 public Country(int id, String countryName, String countrycapital) {
		
		this.id = id;
		this.countryName = countryName;
		this.countrycapital = countrycapital;
	}
	
	 
	 public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountrycapital() {
		return countrycapital;
	}
	public void setCountrycapital(String countrycapital) {
		this.countrycapital = countrycapital;
	}
	
	 
}
