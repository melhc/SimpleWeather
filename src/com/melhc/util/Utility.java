package com.melhc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import com.google.gson.Gson;
import com.melhc.db.WeatherDB;
import com.melhc.model.City;
import com.melhc.model.County;
import com.melhc.model.Province;
import com.melhc.model.Weather;
import com.melhc.model.Weatherinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class Utility {
	/**
	 * 处理服务端返回的省级json数据
	 */
	public synchronized static boolean handleProvicesResponse(
			WeatherDB weatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvices = response.split(",");
			if (allProvices != null && allProvices.length > 0) {
				for (String p : allProvices) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvince_code(array[0]);
					
					province.setProvince_name(array[1]);
				
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
	public synchronized static boolean handleCitiesResponse(
			WeatherDB weatherDB, String response, Province province) {

		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String c : allCities) {
					String[] array = c.split("\\|");
					City city = new City();
					city.setCity_code(array[0]);
					city.setCity_name(array[1]);
					city.setProvince(province);
					weatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 处理服务端返回的县级json数据
	 */
	public synchronized static boolean handleCountiesResponse(
			WeatherDB weatherDB, String response, City city) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for (String c : allCounties) {
					String[] array = c.split("\\|");
					County county = new County();
					county.setCounty_code(array[0]);
					county.setCounty_name(array[1]);
					county.setCity(city);
					weatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 使用Gson解析服务器返回的JSON数据，并将解析的数据存储到本地
	 */
	public static void handleWeatherResponse(Context context, String response) {
		try {
			LogUtil.i("UTILITY", "----------------->" + response.toString());
			Gson gson = new Gson();
			Weather weather = gson.fromJson(response, Weather.class);
			LogUtil.i("UTILITY", "----------------->" + weather.toString());
			Weatherinfo info = weather.getWeatherinfo();
			LogUtil.i("UTILITY", "----------------->" + info.toString());
			saveWeatherInfo(context, info);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 将服务器返回的天气信息存储到共享参数中
	 */
	public static void saveWeatherInfo(Context context, Weatherinfo info) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", info.getCity());
		editor.putString("weather_code", info.getCityid());
		editor.putString("temp1", info.getTemp1());
		editor.putString("temp2", info.getTemp2());
		editor.putString("weather_desp", info.getWeather());
		editor.putString("publish_time", info.getPtime());
		LogUtil.i("UTILITY", "----------------->" +  sdf.format(new Date()));
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}
}
