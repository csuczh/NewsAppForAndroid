package com.czh.androidforkftvrelease.multiplethreads;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.R.bool;

public class ThreadExecutor {
	public ThreadExecutor(String url) {
		super();
	}

	
	//获得服务器的json值
	public static List<String> getResult(String url,List<Map<String, String>> list)
	{
		ExecutorService exec=Executors.newCachedThreadPool();
		List<String> result=new ArrayList<String>();
		ArrayList<Future<String>> listFuture=new ArrayList<Future<String>>();
		for(int i=0;i<list.size();i++)
		{
			listFuture.add(exec.submit(new GetJsonStringThread(url,list.get(i).get("action_flag"),list.get(i).get("value"))));
		}
		for(Future<String> fs:listFuture)
		{
			try {
				result.add(fs.get());
				System.out.println(fs.get()+"threadexecutor");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				exec.shutdown();
			}
		}
		
		return result;
	}

	
}
