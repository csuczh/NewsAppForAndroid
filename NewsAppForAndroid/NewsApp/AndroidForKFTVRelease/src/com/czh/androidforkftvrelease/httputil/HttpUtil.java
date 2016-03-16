package com.czh.androidforkftvrelease.httputil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	public static String getJsonContent(String url,String type,String value) 
	{
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("action_flag", type);
		params.put("value", value);
		
		try {
			InputStream inputStream=sendPOSTRequest(url, params, "utf-8");
			return ChangeInputStream(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	public static InputStream sendPOSTRequest(String url,
			   Map<String, String> params, String encoding) throws Exception {
			  StringBuilder data = new StringBuilder();
			  if (params != null && !params.isEmpty()) {
			   for (Map.Entry<String, String> entry : params.entrySet()) {
			    data.append(entry.getKey()).append("=");
			    data.append(URLEncoder.encode(entry.getValue(), encoding));
			    data.append("&");
			   }
			   data.deleteCharAt(data.length() - 1);
			  }
			  byte[] entity = data.toString().getBytes();// 生成实体数据
			  HttpURLConnection conn = (HttpURLConnection) new URL(url)
			    .openConnection();
			  conn.setConnectTimeout(5000);
			  conn.setRequestMethod("POST");
			  conn.setDoOutput(true);// 允许对外输出数据
			  conn.setRequestProperty("Content-Type",
			    "application/x-www-form-urlencoded");
			  conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
			  OutputStream outStream = conn.getOutputStream();
			  outStream.write(entity);
			  if (conn.getResponseCode() == 200) {
			   return conn.getInputStream();
			  }
			  return null;
			 }
	private static String ChangeInputStream(InputStream inputStream) {
		// TODO Auto-generated method stub
		String jsonString=null;
		
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		byte[] data=new byte[1024];
		int len=0;
		try {
			while((len=inputStream.read(data))!=-1)
			{
				outputStream.write(data, 0, len);
				
				
			}
			System.out.println("size:"+outputStream.size());
			jsonString=new String(outputStream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	
}
