package com.appstore.util;

import java.io.File;

import com.appstore.main.MyApplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AppUtils {
	public static void startAPP(Context context,String appPackageName){
	    try{
	        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
	        context.startActivity(intent);
	    }catch(Exception e){
	    	
	    	Toast.makeText(context, "APK未安装", Toast.LENGTH_LONG).show();
	    }
	}
	public static boolean ApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
    public static void uninstall(Context context,String pkgname){
    	Uri packageURI = Uri.parse("package:"+pkgname);
        Log.d("pkg",pkgname);
    	Intent intent = new Intent(Intent.ACTION_DELETE,packageURI);

    	context.startActivity(intent);
    }
	public static void install(String Path, Context context) {

		// 核心是下面几句代码
		try {
			Log.d("path", Path);

			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(Path)),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
