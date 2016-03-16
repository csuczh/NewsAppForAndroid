package com.czh.androidforkftvrelease.vediolist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.ImageCache;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;

public class MusicAdapter extends BaseAdapter {

    private static List<Map<String, Object>> list;
    private Context context;
    private LayoutInflater inflater;
   
    private FragmentManager fragmentManager;
    private ImageFetcher imageFetcher;
    private static final String IMAGE_CACHE_DIR="images";
    public MusicAdapter(Context cntext,FragmentManager fragmentManager)
    {
    	this.context=cntext;
    	inflater=LayoutInflater.from(cntext);
    	this.fragmentManager=fragmentManager;
    	list=new ArrayList<Map<String,Object>>();
    	
    }
    //判断list是否为空
    public boolean isempty()
    {
    	if(list==null||list.size()==0)
    	{
    		return true;
    	}
    	return false;
    }
   //往list中加入数据
    public void addData(List<Map<String, Object>> addlist)
    {
    	for(int i=0;i<addlist.size();i++)
    	{
    		list.add(addlist.get(i));
    	}
    }
    //刷新list
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
		
		View view=inflater.inflate(R.layout.music_item, null);
		
		TextView musicname=(TextView)view.findViewById(R.id.musicname);
		TextView musicauthor=(TextView)view.findViewById(R.id.musicauthor);
	    TextView musiclrc=(TextView)view.findViewById(R.id.musiclrc);
	    TextView musicfile=(TextView)view.findViewById(R.id.musicfile);
		musicname.setText(list.get(arg0).get("musicname").toString());
		musicauthor.setText(list.get(arg0).get("musicauthor").toString());
		musiclrc.setText(list.get(arg0).get("musiclrc").toString());
		musicfile.setText(list.get(arg0).get("musicfile").toString());
		System.out.println("list+music"+list.get(arg0).get("musicname").toString());
		return view;
	}
	public List<Map<String, Object>> getlist()
	{
		return list;
		
	}

	

}


