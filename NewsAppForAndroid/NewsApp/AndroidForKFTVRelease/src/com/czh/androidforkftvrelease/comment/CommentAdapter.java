package com.czh.androidforkftvrelease.comment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.czh.androidforkftvrelease.R;

import com.czh.androidforkftvrelease.domins.User;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.ImageCache;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{

	private List<Map<String, Object>> list;
	
    private Context context;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    private ImageFetcher imageFetcher;
    private static final String IMAGE_CACHE_DIR="images";
    public CommentAdapter(Context cntext,FragmentManager fragmentManager)
    {
    	this.context=cntext;
    	inflater=LayoutInflater.from(cntext);
    	this.fragmentManager=fragmentManager;
    	list=new ArrayList<Map<String,Object>>();
    	
    	 ImageCache.ImageCacheParams cacheParams=new ImageCache.ImageCacheParams(cntext, IMAGE_CACHE_DIR);
    	cacheParams.setMemCacheSizePercent(0.25f);
    	
    	imageFetcher=new ImageFetcher(cntext, 30);

    	imageFetcher.addImageCache(fragmentManager, cacheParams);
    	imageFetcher.setImageFadeIn(false);
    }
    public boolean isempty()
    {
    	if(list==null||list.size()==0)
    	{
    		return true;
    	}
    	return false;
    }
   
    public void addData(List<Map<String, Object>> addlist)
    {
    	for(int i=0;i<addlist.size();i++)
    	{
    		list.add(addlist.get(i));
    	}
    }
    public void refreshData(List<Map<String, Object>> reList)
    {
    	if(list!=null)
    	      list.clear();
    	list=reList;
    	
    }
   
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.comment_listview, null);
		TextView name=(TextView)view.findViewById(R.id.commentName);
		TextView address=(TextView)view.findViewById(R.id.commentAddress);
		TextView time=(TextView)view.findViewById(R.id.commentTime);
		TextView content=(TextView)view.findViewById(R.id.commentContent);
		final ImageView pic=(ImageView)view.findViewById(R.id.commentImg);
		
		name.setText(list.get(arg0).get("username").toString());
		address.setText(list.get(arg0).get("address").toString());
		content.setText(list.get(arg0).get("commentcontent").toString());
		time.setText(list.get(arg0).get("commentdate").toString());
		
		imageFetcher.loadImage(DataUrl.PIC+list.get(arg0).get("headpicture").toString(), pic);
		System.out.println("pic:"+DataUrl.PIC+list.get(arg0).get("headpicture").toString());
		
		
		return view;
	}

}
