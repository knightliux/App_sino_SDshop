package com.model;

public class Model_AppUp {
	public String code;// 0有升级消息，1无升级消息\
	public String url;// 下载地址

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
