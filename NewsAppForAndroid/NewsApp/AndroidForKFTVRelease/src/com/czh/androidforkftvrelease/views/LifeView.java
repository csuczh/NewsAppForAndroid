package com.czh.androidforkftvrelease.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.fragments.GeneralFragment;
import com.czh.androidforkftvrelease.fragments.MainFragment;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.life.LifeGuide;
import com.czh.androidforkftvrelease.vediolist.VideoGuide;

/**
 * 公告页面
 * @author ZHF
 *
 */
public class LifeView extends GeneralFragment{

	private FragmentManager fragmentManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setTitle("生活助手");
		fragmentManager=getFragmentManager();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View  view=inflater.inflate(R.layout.video, container, false);
		ViewGroup mGroup=(ViewGroup)view.findViewById(R.id.videoguide);
		LifeGuide  addGuide=new LifeGuide(view, MainFragment.screenWidth, inflater,fragmentManager);
		mGroup.addView(addGuide.SetGuide());
		return view;
	}
}
