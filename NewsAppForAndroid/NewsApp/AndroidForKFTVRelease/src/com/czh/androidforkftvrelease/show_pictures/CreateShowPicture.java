package com.czh.androidforkftvrelease.show_pictures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.domins.NewsDetalist;
import com.czh.androidforkftvrelease.domins.NewsPictures;
import com.czh.androidforkftvrelease.domins.NewsRelated;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.ImageCache;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;
import com.czh.androidforkftvrelease.show_singlenew.SingleNewsActivity;

public class CreateShowPicture {
	private View view;
	private Context context;
	private FragmentManager fragmentManager;// FragmentManager是Fragment管理器
	private ImageFetcher imageFetcher;
	private static final String IMAGE_CACHE_DIR = "images";
	private ImageView pictureImageView;
	private TextView pictureTextView;
	private String picturetitle;
	private String picturedescription;
	public CreateShowPicture(Context context, FragmentManager fragmentManager,
			String picturetitle,String picturedescription) {
		this.context = context;
		this.fragmentManager = fragmentManager;
		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
				context, IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f);
		imageFetcher = new ImageFetcher(context, 30);
		imageFetcher.addImageCache(fragmentManager, cacheParams);
		imageFetcher.setImageFadeIn(false);
		this.picturetitle=picturetitle;
		this.picturedescription=picturedescription;

	}

	

	// 获得新闻展示的view
	public View getView() {
		View view = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.picture_showpicture, null);
		pictureImageView = (ImageView) view.findViewById(R.id.picture_show);
		pictureTextView=(TextView)view.findViewById(R.id.picture_text);
		imageFetcher
		.loadImage(
				DataUrl.PIC
						+ picturetitle, pictureImageView);
		pictureTextView.setText(picturedescription);
		return view;
	}

}
