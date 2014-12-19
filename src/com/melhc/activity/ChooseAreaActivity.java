package com.melhc.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.melhc.db.WeatherDB;
import com.melhc.model.City;
import com.melhc.model.County;
import com.melhc.model.Province;
import com.melhc.simpleweather.R;
import com.melhc.util.LogUtil;
import com.melhc.util.Utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAreaActivity extends BaseActivity {
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	private ProgressDialog progressDialog;
	private TextView textView;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private WeatherDB weatherDB;
	private List<String> dataList = new ArrayList<String>();

	private List<Province> proviceList;

	private List<City> cityList;

	private List<County> countyList;

	private Province selectedProvince;

	private City selectedCity;

	private int currentLevel;

	/**
	 * 判断是否从WeatherActivity跳转过来
	 */
	private boolean isFromWeatherActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isFromWeatherActivity = getIntent().getBooleanExtra(
				"from_weather_activity", false);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (prefs.getBoolean("city_selected", false) && !isFromWeatherActivity) {
			Intent intent = new Intent(this, WeatherActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		listView = (ListView) findViewById(R.id.list_view);
		textView = (TextView) findViewById(R.id.title_text);
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.choose_area_item, R.id.textView1, dataList);
		listView.setAdapter(adapter);
		weatherDB = WeatherDB.getInstance();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (currentLevel == LEVEL_PROVINCE) {
					selectedProvince = proviceList.get(position);
					queryCities();
				} else if (currentLevel == LEVEL_CITY) {
					selectedCity = cityList.get(position);
					queryCounties();
				} else if (currentLevel == LEVEL_COUNTY) {
					String countyCode = countyList.get(position)
							.getCounty_code();
					Intent intent = new Intent(ChooseAreaActivity.this,
							WeatherActivity.class);
					intent.putExtra("county_code", countyCode);
					startActivity(intent);
					finish();
				}
			}

		});
		queryProvinces();
	}

	/**
	 * 查询全国的省，优先从数据库查询，如果没有查询再到服务器上查询
	 */
	private void queryProvinces() {
		proviceList = weatherDB.loadProvices();
		if (proviceList.size() > 0) {
			dataList.clear();
			for (Province province : proviceList) {
				dataList.add(province.getProvince_name());
			}
			LogUtil.i("ChooseActivity",
					"--------------->" + dataList.toString());
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			textView.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			queryFromServer(null, "province");
		}
	}

	/**
	 * 查询某省所有的市，优先从数据库查询，如果没有查询再到服务器上查询
	 */
	private void queryCities() {
		cityList = weatherDB.loadCities(selectedProvince.getId());
		LogUtil.i("ChooseActivity", "--------------->" + cityList.toString());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City city : cityList) {
				dataList.add(city.getCity_name());
			}
			LogUtil.i("ChooseActivity",
					"--------------->" + dataList.toString());
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			textView.setText(selectedProvince.getProvince_name());
			currentLevel = LEVEL_CITY;
		} else {
			queryFromServer(selectedProvince.getProvince_code(), "city");
		}
	}

	/**
	 * 查询某市所有的县，优先从数据库查询，如果没有查询再到服务器上查询
	 */
	private void queryCounties() {
		countyList = weatherDB.loadCounties(selectedCity.getId());
		if (countyList.size() > 0) {
			dataList.clear();
			for (County county : countyList) {
				dataList.add(county.getCounty_name());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			textView.setText(selectedCity.getCity_name());
			currentLevel = LEVEL_COUNTY;
		} else {
			// 从服务器查询County
			queryFromServer(selectedCity.getCity_code(), "county");
		}
	}

	/**
	 * 根据传入的代号和数据查询省县市数据
	 * 
	 */
	private void queryFromServer(final String code, final String type) {
		String address;
		if (!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city" + code
					+ ".xml";
		} else {
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		showProgressDialog();
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		StringRequest stringRequest = new StringRequest(address,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						LogUtil.i("TAG", "---------------->"+response);
						boolean result = false;
						if ("province".equals(type)) {
							result = Utility.handleProvicesResponse(weatherDB,
									response);
						} else if ("city".equals(type)) {
							result = Utility.handleCitiesResponse(weatherDB,
									response, selectedProvince);
						} else if ("county".equals(type)) {
							result = Utility.handleCountiesResponse(weatherDB,
									response, selectedCity);
						}
						if (result) {
							// 通过runonUiMainThread方法返回主线程处理逻辑
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									closeProgressDialog();
									if ("province".equals(type)) {
										queryProvinces();
									} else if ("city".equals(type)) {
										queryCities();
									} else if ("county".equals(type)) {
										queryCounties();
									}
								}
							});
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						LogUtil.i("TAG", "-------------------->" + error);
						runOnUiThread(new Runnable() {
							@Override
							public void run() { // TODO Auto-generated method
												// stub //
								closeProgressDialog();
								Toast.makeText(getApplicationContext(),
										"加载数据失败！", Toast.LENGTH_SHORT).show();
							}
						});
					}
				});
		mQueue.add(stringRequest);
	}

	/**
	 * 显示进度对话框
	 */
	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("天气云");
			progressDialog.setMessage("正在加载中....");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (currentLevel == LEVEL_COUNTY) {
			LogUtil.i("CHOOSEAREAACTIVITY", "------------->1");
			queryCities();
			LogUtil.i("CHOOSEAREAACTIVITY", "------------->2");
		} else if (currentLevel == LEVEL_CITY) {
			queryProvinces();
		} else {
			if (isFromWeatherActivity) {
				Intent intent = new Intent(this, WeatherActivity.class);
				startActivity(intent);
			}
			finish();
			LogUtil.i("CHOOSEAREAACTIVITY", "------------->3");
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// weatherDB.destroyDB();
	}
}
