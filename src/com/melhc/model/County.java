package com.melhc.model;

import org.litepal.crud.DataSupport;

public class County extends DataSupport {
	private int id;
	private String county_name;
	private String county_code;
	private City city;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCounty_name() {
		return county_name;
	}

	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}

	public String getCounty_code() {
		return county_code;
	}

	public void setCounty_code(String county_code) {
		this.county_code = county_code;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
