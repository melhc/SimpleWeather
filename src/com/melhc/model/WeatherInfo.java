package com.melhc.model;



public class Weatherinfo {
   private String city;
   private String cityid;
   private String temp1;
   private String temp2;
   private String weather;
	private String ptime;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	@Override
	public String toString() {
		return "WeatherInfo [city=" + city + ", cityid=" + cityid + ", temp1="
				+ temp1 + ", temp2=" + temp2 + ", weather=" + weather
				+ ", ptime=" + ptime + "]";
	}

	
}
