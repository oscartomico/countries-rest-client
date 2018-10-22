package com.restclient.service;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class CountryRequest {
	private Long id;

	@NotNull(message = "Country name is required")
	private String name;

	@DecimalMin(value = "1000000", message = "Population is required and it must be at least 1000000")
	private int population;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}
