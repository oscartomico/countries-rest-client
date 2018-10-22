package com.restclient.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Rest service calls
 * @author otm
 *
 */
@Component
public class CallService {
	RestTemplate template = new RestTemplate();
	
	/**
	 * This method allows get all countries from database
	 * @return JSON with all countries
	 */
	public String getAllCountries() {		
        return template.getForObject("http://localhost:8081/countries", String.class);
	}
	
	/**
	 * This method allows get one country from database
	 * @param id country you are finding
	 * @return JSON with the country
	 */
	public String getCountry(Long id) {
		return template.getForObject("http://localhost:8081/countries/" + id, String.class);
	}
	
	/**
	 * This method allows insert a new country from the request
	 * @param countryRequest
	 * @return JSON with the country inserted
	 */
	public String insertCountry(CountryRequest countryRequest) {		
		return template.postForObject("http://localhost:8081/countries", countryRequest, String.class);
	}
	
	/**
	 * This method allows insert multiples countries in the database
	 * @param countries that you want to insert
	 * @return JSON with all inserted countries
	 */
	public String insertCountries(String countries) {
		String result = "";
		String [] countriesArray = countries.split("\n");
		for (String country : countriesArray) {
			CountryRequest requestAux = new CountryRequest();
			requestAux.setName(country.split("\\|")[1]);
			requestAux.setPopulation(Integer.parseInt(country.split("\\|")[2]));
			result += insertCountry(requestAux);
		}
		return result;
	}
}
