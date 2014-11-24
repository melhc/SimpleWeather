package com.melhc.activity;

import com.melhc.util.ActivityCollector;
import com.melhc.util.LogUtil;

import android.app.Activity;
import android.os.Bundle;


public class BaseActivity extends Activity {
	/*
	 * 自动管理活动的创建销毁
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtil.d("BaseActivity","----------->"+getClass().getSimpleName());
		ActivityCollector.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
