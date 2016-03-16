package com.czh.androidforkftvrelease.fill_listview;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.fragments.MainFragment;
//import com.zhf.frameworkdemo02.fragments.newsactivity;

import com.czh.androidforkftvrelease.listview.MyListView;
import com.czh.androidforkftvrelease.listview.MyListView.OnRefreshListener;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.ImageCache;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;
//import TestLoadImage.DownLoadImage;
//import TestLoadImage.ImageUrl;
import android.R.color;
import android.R.integer;
import android.R.raw;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
public class SlideMenuLayout {
  private ArrayList<TextView> menuList=null;
  private Context activity;
  private TextView textView=null;
  public static int count=0;
  private LayoutInflater inflater;
  private View view;
  
  private String url;
  private ImageFetcher imageFetcher;
  //private static final String IMAGE_CACHE_DIR="images";
  private FragmentManager fragmentManager;
public SlideMenuLayout(LayoutInflater inflater,View view,FragmentManager fragmentManager,String url) {
	super();
	
	this.inflater=inflater;
	this.fragmentManager=fragmentManager;
    this.view=view;
    this.url=url;
	activity=inflater.getContext();
	menuList=new ArrayList<TextView>();
    //ImageCache.ImageCacheParams cacheParams=new ImageCache.ImageCacheParams(activity, IMAGE_CACHE_DIR);
	//cacheParams.setMemCacheSizePercent(0.25f);
	
	//imageFetcher=new ImageFetcher(activity, 500);

	//imageFetcher.addImageCache(fragmentManager, cacheParams);
	//imageFetcher.setImageFadeIn(false);
}
public View getSlidMenuLinnerLayout(String[] menuTextViews,int layoutWidth)
{
	LinearLayout menuLinerLayout=new LinearLayout(activity);
	menuLinerLayout.setOrientation(LinearLayout.HORIZONTAL);
	LinearLayout.LayoutParams menuLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
	
	menuLayoutParams.gravity=Gravity.CENTER_HORIZONTAL;
	
	for(int i=0;i<menuTextViews.length;i++)
	{
		TextView tvMenu=new TextView(activity);
		tvMenu.setTag(menuTextViews[i]);
		tvMenu.setLayoutParams(new LayoutParams(layoutWidth/4,30));
		tvMenu.setPadding(30, 14, 30, 10);
		tvMenu.setText(menuTextViews[i]);
		tvMenu.setTextColor(Color.YELLOW);
		tvMenu.setGravity(Gravity.CENTER_HORIZONTAL);
		tvMenu.setOnClickListener(slideMenuOnClickListener);
	
	        count++;
	if(count==1)
	{
		tvMenu.setBackgroundResource(R.drawable.menu_bg);
	}
	
	menuLinerLayout.addView(tvMenu,menuLayoutParams);
	menuList.add(tvMenu);
	}
	return menuLinerLayout;
}
OnClickListener slideMenuOnClickListener =new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String menuTag=v.getTag().toString();
		if(v.isClickable())
		{
			textView=(TextView)v;
			textView.setBackgroundResource(R.drawable.menu_bg);
			for(int i=0;i<menuList.size();i++)
			{
				if(!menuTag.equals(menuList.get(i).getText()))
				{
					menuList.get(i).setBackgroundDrawable(null);
				}
			}
		}
	 slideMenuOnChange(menuTag);
	}
};
//根据相应的菜单项从服务器下载相应的新闻。
public void slideMenuOnChange(String menuTage)  
{
	ViewGroup llc=(ViewGroup)view.findViewById(R.id.linearLayoutContent);
	llc.removeAllViews();
	final MyAdapter adapter=new MyAdapter(activity, fragmentManager);
	final MyAsyncTask task=new MyAsyncTask(adapter);
	try {
		task.execute(new URL(url));
		adapter.refreshData(task.get());
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	final MyListView myListView=new MyListView(activity);
	myListView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
	myListView.setBackgroundResource(R.drawable.fragment_bottom_normal);
	
	myListView.setCacheColorHint(Color.TRANSPARENT);
	
	
	myListView.setOnLoadeMoreListener(new MyListView.OnLoadeMoreListener() {
		
		@Override
		public void onLoade() {
			// TODO Auto-generated method stub
			MyAsyncTask task1=new MyAsyncTask(adapter);
			try {
				task1.setState(1);
				task1.execute(new URL(url));
				adapter.addData(task1.get());
				myListView.onLoadeComplete();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	myListView.setOnRefreshListener(new OnRefreshListener() {
		
		@Override
		public void onRefresh()  {
			// TODO Auto-generated method stub
			MyAsyncTask task2=new MyAsyncTask(adapter);
			try {
				task2.setState(0);
				task2.execute(new URL(url));
			    adapter.refreshData(task2.get());
				myListView.onRefreshComplete();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	myListView.setAdapter(adapter);
	myListView.setOnItemClickListener(new MyOnItemListener(activity));
	llc.addView(myListView);
}


}
