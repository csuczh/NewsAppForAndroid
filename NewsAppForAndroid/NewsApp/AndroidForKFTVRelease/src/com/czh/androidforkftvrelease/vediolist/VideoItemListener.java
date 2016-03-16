package com.czh.androidforkftvrelease.vediolist;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.domins.VideoItem;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.show_pictures.ShowPicture;
import com.czh.androidforkftvrelease.show_video.ShowVideo;

public class VideoItemListener implements OnItemClickListener{
	  public Context context;
	  private List<Map<String, Object>> list;
	  public VideoItemListener(Context context,List<Map<String, Object>> list)
	  {
		  this.context=context;
		  this.list=list;
		  System.out.println("map:"+list);
	  }
	  public void setContext(Context context){
		  this.context=context;
	  }
	 
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(context, ShowVideo.class);
			String videotitle=((TextView)arg1.findViewById(R.id.item_video_title_id)).getText().toString();
			String videotime=((TextView)arg1.findViewById(R.id.item_video_time_id)).getText().toString();
			String videocount=((TextView)arg1.findViewById(R.id.item_video_count_id)).getText().toString();
			String videofrom=((TextView)arg1.findViewById(R.id.item_video_source_id)).getText().toString();
			intent.putExtra("videotitle", videotitle);
			intent.putExtra("videotime", videotime);
			intent.putExtra("videocount", videocount);
			intent.putExtra("videofrom", videofrom);
			context.startActivity(intent);
			
		}


}

