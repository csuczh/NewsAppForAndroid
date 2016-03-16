package com.czh.androidforkftvrelease.fill_listview;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.multiplethreads.GetJsonStringThread;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyAsyncTask extends AsyncTask<URL,Integer, List<Map<String, Object>>>{

	private MyAdapter myAdapter;
	//0:表示加载，1：表示刷新
	private int State;
	//网络访问的参数
	List<Map<String, String>> param=new ArrayList<Map<String,String>>();
	List<String> result=new ArrayList<String>();
	
	public MyAsyncTask(MyAdapter myAdapter,int State)
	{
		this.myAdapter=myAdapter;
		this.State=State;
		
	}
	public MyAsyncTask(MyAdapter myAdapter)
	{
		this.myAdapter=myAdapter;
		State=0;
		
	}
	public void setState(int state){
		State=state;
	}
	@Override
	protected List<Map<String, Object>> doInBackground(URL... params) {
		// TODO Auto-generated method stub
		Map<String, String> map1=new HashMap<String, String>();
		map1.put("action_flag", "news");
		map1.put("value", "");
		param.add(map1);
		result=ThreadExecutor.getResult(DataUrl.DATAURL,param);
		String json=result.get(0);
		System.out.println(params[0].toString()+" url");
		System.out.println("json:"+json);
		List<Map<String, Object>> list=GsonTools.GetListMap(json, Object.class);
		
//		if(State==0)
//		{
//			
//			myAdapter.refreshData(list);
//			
//			
//		}else 
//		{
//			myAdapter.addData(list);
//			
//		}
//		myAdapter.notifyDataSetChanged();
		return list;
	}

	@Override
	protected void onPostExecute(List<Map<String, Object>> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		myAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
	
}
