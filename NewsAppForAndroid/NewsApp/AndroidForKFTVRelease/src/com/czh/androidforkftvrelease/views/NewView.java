package com.czh.androidforkftvrelease.views;
import com.czh.androidforkftvrelease.fill_listview.AddGuide;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.czh.androidforkftvrelease.R;

import com.czh.androidforkftvrelease.fragments.GeneralFragment;
import com.czh.androidforkftvrelease.fragments.MainFragment;
import com.czh.androidforkftvrelease.httputil.DataUrl;

/**
 * 主页面
 * @author ZHF
 *
 */
public class NewView extends GeneralFragment {

	private static int index=1;
	private static double x=0;
	private TextView[] textViews;
	private FragmentManager fragmentManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setTitle("新闻");
		textViews=new TextView[3];
		fragmentManager=getFragmentManager();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View  view=inflater.inflate(R.layout.news, container, false);
		ViewGroup mGroup=(ViewGroup)view.findViewById(R.id.guide);
		int resource=R.layout.news_index_list_items;
		String url=DataUrl.DATAURL;
		AddGuide  addGuide=new AddGuide(view, MainFragment.screenWidth, inflater,fragmentManager,"types",3,url);
		mGroup.addView(addGuide.SetGuide());
		return view;
	}

	
	
	
	
}
