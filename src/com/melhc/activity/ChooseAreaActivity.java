package com.melhc.activity;

import java.util.ArrayList;
import java.util.List;

import com.melhc.model.City;
import com.melhc.model.County;
import com.melhc.model.Province;
import com.melhc.simpleweather.R;
import com.melhc.util.WeatherDB;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_area);
		listView = (ListView) findViewById(R.id.list_view);
		textView = (TextView) findViewById(R.id.title_text);
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, dataList);
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
				}
			}

		});
		queryProvinces();
	}

	/*
	 * 查询全国的省，优先从数据库查询，如果没有查询再到服务器上查询
	 */
	private void queryProvinces() {
		proviceList = weatherDB.loadProvices();
		if (proviceList.size() > 0) {
			dataList.clear();
			for (Province province : proviceList) {
				dataList.add(province.getProvince_name());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			textView.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			// 从服务器查询Province
		}
	}

	/*
	 * 查询某省所有的市，优先从数据库查询，如果没有查询再到服务器上查询
	 */
	private void queryCities() {
		cityList = weatherDB.loadCities(selectedProvince.getId());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City city : cityList) {
				dataList.add(city.getCity_name());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			textView.setText(selectedProvince.getProvince_name());
			currentLevel = LEVEL_CITY;
		} else {
			// 从服务器查询City
		}
	}

	/*
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
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		weatherDB.destroyDB();
	}
}
