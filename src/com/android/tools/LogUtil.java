package com.android.tools;

import android.util.Log;

public class LogUtil {
	public static void i(String msg) {
		if (true) {
			Log.i("info", msg);
		}
	}

	public static void i(long msg) {
		i(String.valueOf(msg));
	}

	public static void i(int msg) {
		i(String.valueOf(msg));
	}

	public static void i(boolean msg) {
		i(String.valueOf(msg));
	}
}
