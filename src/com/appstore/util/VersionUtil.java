package com.appstore.util;

import com.config.Configs;

import android.content.Context;
import android.content.pm.PackageManager;



public class VersionUtil {

	public static int getVersionCode(Context paramContext) {
		try {
			int i = paramContext.getPackageManager().getPackageInfo(
					Configs.PACKAGE, 0).versionCode;
			return i;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return -1;
	}
	
	public static String getVersionName(Context paramContext) {
		try {
			String versionName = paramContext.getPackageManager().getPackageInfo(
					Configs.PACKAGE, 0).versionName;
			return versionName;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return "";
	}
}
