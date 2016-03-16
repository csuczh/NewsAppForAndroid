package com.czh.androidforkftvrelease.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.fill_listview.AddGuide;
import com.czh.androidforkftvrelease.fragments.GeneralFragment;
import com.czh.androidforkftvrelease.fragments.MainFragment;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.picturelist.PictureGuide;

/**
 * ¸ü¶àÒ³Ãæ
 * @author ZHF
 *
 */
public class PictureView extends GeneralFragment{

	private static int index=1;
	private static double x=0;
	private FragmentManager fragmentManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setTitle("Í¼Æ¬");
		fragmentManager=getFragmentManager();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View  view=inflater.inflate(R.layout.picture, container, false);
		ViewGroup mGroup=(ViewGroup)view.findViewById(R.id.pictureguide);
		String url=DataUrl.PICTURE;
		PictureGuide  addGuide=new PictureGuide(view, MainFragment.screenWidth, inflater,fragmentManager,"picturetypes",4,url);
		mGroup.addView(addGuide.SetGuide());
		return view;
		//return inflater.inflate(R.layout.picture, container, false);
	}
}
