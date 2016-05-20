package com.config;

import java.util.HashMap;

import android.os.Environment;

import com.appstore.main.MyApplication;
import com.model.Model_App;
import com.model.Model_AppInfo;

public class GlobalVar {
     public static HashMap<String,Model_AppInfo> Appinfo_All=new HashMap<String,Model_AppInfo>();
     public static String DownPath(){
//    	 return MyApplication.myAppl1ication.getApplicationContext().getFilesDir().getAbsolutePath()+"/";
    	 return Environment.getExternalStorageDirectory() +"/DownLoad/";
    	 //
     } 
     
}
