package com.czh.androidforkftvrelease.picturelist;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.show_pictures.ShowPicture;
import com.czh.androidforkftvrelease.show_singlenew.SingleNewsActivity;

public class PictureItemListener implements OnItemClickListener{
	  public Context context;
	  
	  public PictureItemListener(Context context)
	  {
		  this.context=context;
		 
	  }
	  public void setContext(Context context){
		  this.context=context;
	  }
	 
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(context, ShowPicture.class);
			String title=((TextView)arg1.findViewById(R.id.pnewtitle)).getText().toString();
			String countString=((TextView)arg1.findViewById(R.id.picturecount)).getText().toString();
			String picturedate=((TextView)arg1.findViewById(R.id.picturedate)).getText().toString();
			String picturesource=((TextView)arg1.findViewById(R.id.picturesource)).getText().toString();
			String picturecommentcount=((TextView)arg1.findViewById(R.id.picturecommentcount)).getText().toString();
			intent.putExtra("pnewtitle", title);
			intent.putExtra("index", 0);
			intent.putExtra("picturecount", countString);
			intent.putExtra("picturedate", picturedate);
			intent.putExtra("picturesource", picturesource);
			intent.putExtra("picturecommentcount", picturecommentcount);
			context.startActivity(intent);
			
		}


}
