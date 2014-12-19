package com.melhc.model;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

public class City extends DataSupport {
	private int id;
	private String city_name;
	private String city_code;
	private Province province;
	
	private List<County> counties = new ArrayList<County>();

	public List<County> getCounties() {

		return counties;
	}

	public void setCounties(List<County> counties) {
		this.counties = counties;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
