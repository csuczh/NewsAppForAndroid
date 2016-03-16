package com.czh.androidforkftvrelease.fill_listview;
import com.czh.androidforkftvrelease.show_singlenew.*;

import com.czh.androidforkftvrelease.R;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class MyOnItemListener implements OnItemClickListener{
  public Context context;
  
  public MyOnItemListener(Context context)
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
		intent.setClass(context, SingleNewsActivity.class);
		String title=((TextView)arg1.findViewById(R.id.item_news_title_id)).getText().toString();
		String newtime=((TextView)arg1.findViewById(R.id.item_news_time_id)).getText().toString();
		String newsource=((TextView)arg1.findViewById(R.id.item_news_source_id)).getText().toString();
		String newscount=((TextView)arg1.findViewById(R.id.item_news_count_id)).getText().toString();
		intent.putExtra("newstitle", title);
		intent.putExtra("newstime", newtime);
		intent.putExtra("newssource", newsource);
		intent.putExtra("newscount", newscount);
		intent.putExtra("index", 0);
		context.startActivity(intent);
		
	}

}
