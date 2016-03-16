package com.czh.androidforkftvrelease.life;

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
import com.czh.androidforkftvrelease.listview.MyListView;
import com.czh.androidforkftvrelease.listview.MyListView.OnRefreshListener;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;
import com.czh.androidforkftvrelease.vediolist.VideoAdapter;
import com.czh.androidforkftvrelease.vediolist.VideoItemListener;
import com.czh.androidforkftvrelease.vediolist.VideoTask;

public class LifeSlideMenuLayout {
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
	public LifeSlideMenuLayout(LayoutInflater inflater,View view,FragmentManager fragmentManager) {
		super();
		
		this.inflater=inflater;
		this.fragmentManager=fragmentManager;
	    this.view=view;
		activity=inflater.getContext();
		menuList=new ArrayList<TextView>();
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
		View view=null;
		if(menuTage.equals("路线搜索"))
		{
			view=new CreateTraffic(activity).GetView();
			llc.addView(view);
		}
		if(menuTage.equals("附近搜索"))
		{
			llc.removeAllViews();
			llc.addView(new CreateNearby(activity).getView());
			
		}
		if(menuTage.equals("地点查找"))
		{
			llc.removeAllViews();
			llc.addView(new CreateLocation(activity).getView());
			
		}
	}

}
