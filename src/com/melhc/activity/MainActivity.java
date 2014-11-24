package com.melhc.activity;

import org.litepal.tablemanager.Connector;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SQLiteDatabase database = Connector.getDatabase();
	}
}
