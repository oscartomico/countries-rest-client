package com.restclient.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.restclient.io.ReadWrite;
import com.restclient.service.CallService;
import com.restclient.service.CountryRequest;

/**
 * Rest Service Endpoints
 * @author otm
 *
 */

@RestController
@ComponentScan({"com.restclient.controller"})
public class AppController {
	@Autowired
	CallService service;
	
	@Autowired
	ReadWrite io;

	/**
	 * This service allows get all countries from database
	 * @return all countries
	 */
	@RequestMapping(value = "/countries")
	public ResponseEntity getAllCountriesDataBase() {
		try {
			return ResponseEntity.ok(service.getAllCountries());
		}catch (HttpClientErrorException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"data base is empty\"}");
		}
	}
	
	/**
	 * This service allows get a country from database
	 * @param id of the country you are finding
	 * @return one country
	 */
	@RequestMapping(value = "/countries/{id}")
	public ResponseEntity getCountryDataBase(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(service.getCountry(id));
		} catch (HttpClientErrorException hcee) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"country not founded\"}");
		}
	}
	
	/**
	 * This service allows export all database countries to a file
	 * @param appType of the file
	 * @return true or false
	 */
	@RequestMapping(value = "/countriestofile/{appType}", method = RequestMethod.POST)
	public ResponseEntity countriesToFiles(@PathVariable String appType) {
		// Recover all countries
		if (!io.setAppType(appType)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"App type not exists\"}");
		}
		try {
		String countries = service.getAllCountries();
		return ResponseEntity.ok(io.exportToFile(countries));
		}catch (HttpClientErrorException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"data base is empty\"}");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error io, may file can't be created\"");
		}		
	}
	
	/**
	 * This service allows add a new country by the request
	 * @param countryRequest that contains the country
	 * @return country inserted
	 */
	@RequestMapping(value = "/countries/{appType}", method = RequestMethod.POST)
	public ResponseEntity addNewCountry(@RequestBody @Valid CountryRequest countryRequest) {
		return ResponseEntity.ok(service.insertCountry(countryRequest));
	}
	
	/**
	 * This service allows copy all file countries to database
	 * @param appType file
	 * @return all countries inserted
	 */
	@RequestMapping(value = "/countriestodatabase/{appType}", method = RequestMethod.POST)
	public ResponseEntity countriesToDataBase(@PathVariable String appType) {
		if (!io.setAppType(appType)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"App type not exists\"}");
		}
		// Read from file
		try {
			String countriesFile = io.readFileWithoutSeparator(io.readFile());
			// Send to data base all countries
			return ResponseEntity.ok(service.insertCountries(countriesFile));
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error io, may file not exists\"");
		}
	}
}
