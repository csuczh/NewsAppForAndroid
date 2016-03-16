package com.czh.androidforkftvrelease.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.fill_listview.AddGuide;
import com.czh.androidforkftvrelease.fragments.GeneralFragment;
import com.czh.androidforkftvrelease.fragments.MainFragment;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.life.LifeGuide;
import com.czh.androidforkftvrelease.picturelist.PictureGuide;
import com.czh.androidforkftvrelease.vediolist.VideoGuide;

/**
 * ∂©µ•“≥√Ê
 * @author ZHF
 *
 */
public class VideoView extends GeneralFragment{

	private FragmentManager fragmentManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setTitle(" ”∆µ");
		fragmentManager=getFragmentManager();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View  view=inflater.inflate(R.layout.video, container, false);
		ViewGroup mGroup=(ViewGroup)view.findViewById(R.id.videoguide);
		String url=DataUrl.VIDEO;
		VideoGuide  addGuide=new VideoGuide(view, MainFragment.screenWidth, inflater,fragmentManager,"videotypes",4,url);
		mGroup.addView(addGuide.SetGuide());
		return view;
	}
}
