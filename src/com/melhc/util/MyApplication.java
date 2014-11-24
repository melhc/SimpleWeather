package com.melhc.util;

import android.content.Context;

public class MyApplication extends org.litepal.LitePalApplication {
	/*
	 * 构建Application管理全局变量
	 */

	private static Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}

}
