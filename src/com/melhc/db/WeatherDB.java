package com.melhc.db;

import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.melhc.model.City;
import com.melhc.model.County;
import com.melhc.model.Province;

import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	/**
	 * 一些基本的数据库方法封装
	 */
	private SQLiteDatabase db;

	private static WeatherDB weatherDB;

	public WeatherDB() {
		// TODO Auto-generated constructor stub
		db = Connector.getDatabase();//正式生成数据库
	}

	public synchronized static WeatherDB getInstance() {
		if (weatherDB == null) {
			weatherDB = new WeatherDB();
		}
		return weatherDB;
	}

	/**
	 * 将provice实例存储到数据库
	 */
	public void saveProvice(Province province) {
		if (province != null) {
			province.save();
		}
	}

	/**
	 * 从数据库读取全国所有的省份信息
	 */
	public List<Province> loadProvices() {
		List<Province> list = DataSupport.findAll(Province.class);
		return list;
	}

	/**
	 * 将city实例存储到数据库
	 */
	public void saveCity(City city) {
		if (city != null) {
			city.save();
		}
	}

	/**
	 * 从数据库读取某省下的所有的城市信息
	 */
	public List<City> loadCities(int provinceId) {
		Province provice = DataSupport.find(Province.class, provinceId,true);
		List<City> list = provice.getCities();

		return list;
	}

	/**
	 * 将county实例存储到数据库
	 */
	public void saveCounty(County county) {
		if (county != null) {
			county.save();
		}
	}

	/**
	 * 从数据库读取某城市下的所有的县信息
	 */
	public List<County> loadCounties(int cityId) {
		City city = DataSupport.find(City.class, cityId,true);
		List<County> list = city.getCounties();
		return list;
	}

	/**
	 * 关闭数据库
	 */
	public void destroyDB() {
		if (db != null) {
			db.close();
		}
	}

}
