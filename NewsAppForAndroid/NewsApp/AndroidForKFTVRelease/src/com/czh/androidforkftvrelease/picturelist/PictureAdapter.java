package com.czh.androidforkftvrelease.picturelist;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.czh.androidforkftvrelease.R;
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

public class PictureAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private Context context;
	private LayoutInflater inflater;

	private FragmentManager fragmentManager;
	private ImageFetcher imageFetcher;
	private static final String IMAGE_CACHE_DIR = "images";

	public PictureAdapter(Context cntext, FragmentManager fragmentManager) {
		this.context = cntext;
		inflater = LayoutInflater.from(cntext);
		this.fragmentManager = fragmentManager;

		list = new LinkedList<Map<String, Object>>();

		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
				cntext, IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f);

		imageFetcher = new ImageFetcher(cntext, 400);

		imageFetcher.addImageCache(fragmentManager, cacheParams);
		imageFetcher.setImageFadeIn(false);
	}

	// 判断list是否为空
	public boolean isempty() {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	// 往list中加入数据
	public void addData(List<Map<String, Object>> addlist) {
		for (int i = 0; i < addlist.size(); i++) {
			list.add(addlist.get(i));
			System.out.println("zhen mo ke neng"+addlist.get(i));
		}
	}

	// 刷新list
	public void refreshData(List<Map<String, Object>> reList) {
		if (list != null)
			list.clear();
		list = reList;
		
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
		View view = inflater.inflate(R.layout.picture_list_items, null);
		TextView pnewtitle = (TextView) view.findViewById(R.id.pnewtitle);
		ImageView pic1 = (ImageView) view.findViewById(R.id.pic1);
		ImageView pic2 = (ImageView) view.findViewById(R.id.pic2);
		ImageView pic3 = (ImageView) view.findViewById(R.id.pic3);
		TextView picturesource = (TextView) view
				.findViewById(R.id.picturesource);
		TextView pictureDate = (TextView) view.findViewById(R.id.picturedate);
		TextView picturecount = (TextView) view.findViewById(R.id.picturecount);
		TextView picturecommentcount=(TextView)view.findViewById(R.id.picturecommentcount);
		pnewtitle.setText(list.get(arg0).get("pnewtitle").toString());
		picturesource.setText(list.get(arg0).get("picturesource").toString());
		pictureDate.setText(list.get(arg0).get("pnewdate").toString());
		
		if(list.get(arg0).get("picturecommentcount").toString().equals(""))
	       picturecommentcount.setText("0");
		else {
			picturecommentcount.setText(list.get(arg0).get("picturecommentcount").toString());
		}
		imageFetcher.loadImage(DataUrl.PIC
				+ list.get(arg0).get("pic1").toString(), pic1);
		imageFetcher.loadImage(DataUrl.PIC
				+ list.get(arg0).get("pic2").toString(), pic2);
		imageFetcher.loadImage(DataUrl.PIC
				+ list.get(arg0).get("pic3").toString(), pic3);
		picturecount.setText(list.get(arg0).get("picturecount").toString());
		
		return view;
	}
}
