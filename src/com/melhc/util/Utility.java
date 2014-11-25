package com.melhc.util;

import com.melhc.db.WeatherDB;
import com.melhc.model.City;
import com.melhc.model.County;
import com.melhc.model.Province;

import android.text.TextUtils;


public class Utility {
	/**
	 * 处理服务端返回的省级json数据
	 */
	public synchronized static boolean handleProvicesResponse(WeatherDB weatherDB,
			String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvices = response.split(",");
			if (allProvices != null && allProvices.length > 0) {
				for (String p : allProvices) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvince_code(array[0]);
					LogUtil.i("UTILITY", "--------------------->"+array[0]);
					province.setProvince_name(array[1]);
					LogUtil.i("UTILITY", "--------------------->"+array[1]);
					weatherDB.saveProvice(province);
				}
				return true;
			}
		}

		return false;
	}

	/**
	 * 处理服务端返回的市级json数据
	 */
	public synchronized static boolean handleCitiesResponse(WeatherDB weatherDB,
			String response, Province province) {
		
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String c : allCities) {
					String[] array = c.split("\\|");
					City city = new City();
					city.setCity_code(array[0]);
					city.setCity_name(array[1]);
					weatherDB.saveCity(city);
					province.getCities().add(city);
//					province.updateAll("province_code = ? ",
//							String.valueOf(i));
					province.save();
					
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 处理服务端返回的县级json数据
	 */
	public synchronized static boolean handleCountiesResponse(WeatherDB weatherDB,
			String response, City city) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for (String c : allCounties) {
					String[] array = c.split("\\|");
					County county = new County();
					county.setCounty_code(array[0]);
					county.setCounty_name(array[1]);
					weatherDB.saveCounty(county);
					city.getCounties().add(county);
//					city.updateAll("city_code = ? ", city.getCity_code());
					city.save();
				}
				return true;
			}
		}
		return false;
	}

}
