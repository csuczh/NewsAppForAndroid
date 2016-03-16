package com.czh.androidforkftvrelease.multiplethreads;

import java.util.concurrent.Callable;

import org.apache.http.client.methods.HttpUriRequest;

import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.httputil.HttpUtil;

public class GetJsonStringThread implements Callable<String>{

	private String type;//获得新闻的类型
	private String value;//获得类型的值。
	private String url;
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		String result=HttpUtil.getJsonContent(url,type,value);
		return result;
	}
	public GetJsonStringThread(String url,String type, String value) {
		super();
		this.type = type;
		this.value = value;
		this.url=url;
	}

	
}
