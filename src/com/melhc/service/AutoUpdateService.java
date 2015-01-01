package com.melhc.service;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.melhc.reciever.AutoUpdateReceiver;
import com.melhc.util.LogUtil;
import com.melhc.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// updateWeather();
			}
		}).start();
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 8 * 60 * 60 * 1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 更新天气
	 */
	public void updeteWeather() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String weather_code = prefs.getString("weather_code", "");
		String address = "http:www.weather.com.cn/data/cityinfo/"
				+ weather_code + ".html";
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		StringRequest stringRequest = new StringRequest(address,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Utility.handleWeatherResponse(getApplicationContext(),
								response);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						LogUtil.i("TAG", "-------------------->" + error);

					}
				});
		mQueue.add(stringRequest);
	}

}
