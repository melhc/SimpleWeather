package com.melhc.util;

import android.util.Log;

public class LogUtil {
	public static final int VERBOSE = 1;

	public static final int DEBUG = 2;

	public static final int INFO = 3;

	public static final int WARN = 4;

	public static final int ERROR = 5;

	public static final int NOTHING = 6;

	public static final int LEVEAL = VERBOSE;

	public static void v(String tag, String msg) {
		if (LEVEAL <= VERBOSE) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LEVEAL <= DEBUG) {
			Log.v(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LEVEAL <= INFO) {
			Log.v(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LEVEAL <= WARN) {
			Log.v(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LEVEAL <= ERROR) {
			Log.v(tag, msg);
		}
	}
}
