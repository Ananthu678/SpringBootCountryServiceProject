package com.countryservice.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countryservice.demo.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

	List<Country> findAll();

}
