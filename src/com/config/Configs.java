package com.config;

public class Configs {
	 public static String Server="http://appstoreapi.tvdfe.com:8086/";
//	public static String Server = "http://192.168.100.125:8010/";
//	public static String Server = "http://192.168.100.221:10000/";
    public static String PACKAGE="com.moon.appstore";
	public static class Url {
		public static String LeftMenu() {
			return Server + "Shop/LeftMenu";
		}
 
		public static String AppMenu() {
			return Server + "Shop/AppMenu";
		}

		public static String AppInfo() {
			return Server + "Shop/AppInfo";
		}

		public static String AppSearch() {
			return Server + "Shop/AppSearch";
		}
		public static String AppUp(){
			return Server + "Shop/AppUp";
		}
	}
}
