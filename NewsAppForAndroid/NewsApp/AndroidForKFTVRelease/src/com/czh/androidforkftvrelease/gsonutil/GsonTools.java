package com.czh.androidforkftvrelease.gsonutil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonTools {

	// 把一个对象转化成string
	public static String createJsonString(Object object) {
		Gson gson = new Gson();
		String str = gson.toJson(object);
		return str;
	}

	public static <T> T GetSingle(String json, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	public static <T> List<Map<String, Object>> GetListMap(String json,
			Class<T> cls) {
		List<Map<String, Object>> listmap = new LinkedList<Map<String, Object>>();
		Type listmapType = new TypeToken<List<Map<String, Object>>>() {
		}.getType();
		try {
			Gson gson = new Gson();
			listmap = gson.fromJson(json, listmapType);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return listmap;
	}
}
