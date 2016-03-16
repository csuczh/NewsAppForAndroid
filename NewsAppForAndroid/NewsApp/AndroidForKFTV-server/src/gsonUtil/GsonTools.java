package gsonUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.validator.xml.GetterType;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import domin.NewsD;
import com.google.gson.reflect.TypeToken;
public class GsonTools {
   //��һ������ת����string
	public static String createJsonString(Object object)
	{
		Gson gson=new Gson();
		String str=gson.toJson(object);
		return str;
	}
	//��һ��listת����string
	public static String createjsonString2(List<?> list)
	{
		Gson gson=new Gson();
		String str=gson.toJson(list,new TypeToken<List<?>>(){}.getType() );
		return str;
	}
	//��õ�������
	public static <T> T GetSingle(String json,Class<T> cls)
	 {
		 T t=null;
		 try {
			Gson gson=new Gson();
			t=gson.fromJson(json, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return t;
	 }
	public static  List<NewsD> getbeans(String jsonstring)
	{
		List<NewsD> list=new ArrayList<NewsD>();
		try {
			Gson gson=new Gson();
			list=gson.fromJson(jsonstring, new TypeToken<List<NewsD>>(){}.getType());
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	
	
	

}
