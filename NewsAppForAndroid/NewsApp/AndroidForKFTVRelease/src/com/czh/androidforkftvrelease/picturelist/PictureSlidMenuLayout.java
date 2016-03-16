package com.czh.androidforkftvrelease.picturelist;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.fill_listview.MyAdapter;
import com.czh.androidforkftvrelease.fill_listview.MyAsyncTask;
import com.czh.androidforkftvrelease.fill_listview.MyOnItemListener;
import com.czh.androidforkftvrelease.listview.MyListView;
import com.czh.androidforkftvrelease.listview.MyListView.OnRefreshListener;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;

public class PictureSlidMenuLayout {
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
	public PictureSlidMenuLayout(LayoutInflater inflater,View view,FragmentManager fragmentManager2,String url) {
		super();
		
		this.inflater=inflater;
		this.fragmentManager=fragmentManager2;
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
		final PictureAdapter adapter=new PictureAdapter(activity, fragmentManager);
		final PictureTask task=new PictureTask(adapter);
		try {
			task.execute(new URL(url));
		} catch (MalformedURLException e) {
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
				PictureTask task1=new PictureTask(adapter);
				try {
					task1.setState(1);
					task1.execute(new URL(url));
					myListView.onLoadeComplete();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		myListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh()  {
				// TODO Auto-generated method stub
				PictureTask task2=new PictureTask(adapter);
				try {
					task2.setState(0);
					task2.execute(new URL(url));
					myListView.onRefreshComplete();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		myListView.setAdapter(adapter);
		myListView.setOnItemClickListener(new PictureItemListener(activity));
		llc.addView(myListView);
	}


}
