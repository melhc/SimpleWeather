package com.melhc.model;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {
	private int id;
	private String province_name;
	private String province_code;

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	@SuppressWarnings("unused")
	private List<City> cities = new ArrayList<City>();

	public List<City> getCities() {
		return DataSupport.where("province_id = ?", String.valueOf(id)).find(
				City.class);
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

}
