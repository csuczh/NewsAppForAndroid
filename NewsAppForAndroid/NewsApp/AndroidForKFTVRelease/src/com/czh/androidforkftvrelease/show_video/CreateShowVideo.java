package com.czh.androidforkftvrelease.show_video;

import io.vov.vitamio.demo.VideoViewDemo;

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
import com.czh.androidforkftvrelease.domins.VideoItem;
import com.czh.androidforkftvrelease.domins.VideoRelated;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.ImageCache;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;
import com.czh.androidforkftvrelease.show_singlenew.SingleNewsActivity;

public class CreateShowVideo {
	private View view;
	private Context context;
	private FragmentManager fragmentManager;// FragmentManager��Fragment������
	private ImageFetcher imageFetcher;
	private static final String IMAGE_CACHE_DIR = "images";
	private ImageView imageView;
	private TextView title;
	private TextView date;
	private TextView source;
	private TextView content;
	private TextView commentcount;
	private String newstitle;

	// ��ʾ���ŵ�ͼƬ
	private Button pre;
	private Button next;
	private int index;
	// ͼƬ�͵������ŵ�json�ַ���
	private String picturejson;
	private String detalistjson;

	// ���۵�����
	private static String count;
	// ������ŵ�listview
	private ListView relatedlListView;
    //��Ƶ���ŵ���ϸ��Ϣ
	private String videotitle;
	public CreateShowVideo(Context context, FragmentManager fragmentManager,String videotitle) {
		this.context = context;
		this.fragmentManager = fragmentManager;
		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
				context, IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f);
		imageFetcher = new ImageFetcher(context, 30);
		imageFetcher.addImageCache(fragmentManager, cacheParams);
		imageFetcher.setImageFadeIn(false);
		this.videotitle = videotitle;
		index = 0;

	}

	// ���json�ַ���
	public String setJson(String type, String value) {
		List<Map<String, String>> params=new ArrayList<Map<String,String>>();
		List<String> result=new ArrayList<String>();
		Map<String, String> map1=new HashMap<String, String>();
		map1.put("action_flag", type);
		map1.put("value",value);
		params.add(map1);
		result=ThreadExecutor.getResult(DataUrl.VIDEO,params);
		return result.get(0);
		
	}

	// �������չʾ��view
	public View getView() {
		View view = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.video_detalist, null);
		imageView = (ImageView) view.findViewById(R.id.showvideoimage);
		
		title = (TextView) view.findViewById(R.id.video_title);
		date = (TextView) view.findViewById(R.id.video_time);
		source = (TextView) view.findViewById(R.id.video_source);
		content = (TextView) view.findViewById(R.id.video_content);

		commentcount = (TextView) view.findViewById(R.id.video_comments);
		
		title.setText(videotitle);
		detalistjson = setJson("singlevideo", videotitle);
		final VideoItem videoDetalist = GsonTools.GetSingle(detalistjson,VideoItem.class);
		
		System.out.println("video picture ++++++++++++++++++++++"+videoDetalist.getVideopicture());
		imageFetcher.loadImage(DataUrl.PIC
				+ videoDetalist.getVideopicture(), imageView);
		
		date.setText(videoDetalist.getVideodate());
		source.setText(videoDetalist.getVideosource());
		content.setText(videoDetalist.getVideocontent());
    	commentcount.setText(videoDetalist.getVideocommentcount().toString());
		count =videoDetalist.getVideocommentcount();
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, VideoViewDemo.class);
				intent.putExtra("videopath", videoDetalist.getVideopath());
				context.startActivity(intent);
			}
		});
		

		relatedlListView = (ListView) view.findViewById(R.id.videorelatednews);
		
		String related = setJson("videorelate", videoDetalist.getVideotitle());
		List<Map<String, Object>> reList = GsonTools.GetListMap(related,
				VideoRelated.class);
		List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < reList.size(); i++) {
			list2.add(reList.get(i).get("videorelatetitle").toString());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.news_relatednews_listitem_xml, R.id.relatedtitle,
				list2);
		relatedlListView.setAdapter(adapter);
		relatedlListView.setCacheColorHint(color.transparent);
		relatedlListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(context, ShowVideo.class);
						String title = ((TextView) arg1
								.findViewById(R.id.relatedtitle)).getText()
								.toString();
						detalistjson = setJson("singlevideo", title);
						 VideoItem videoDetalist1 = GsonTools.GetSingle(detalistjson,VideoItem.class);
						intent.putExtra("videotitle", title);
						intent.putExtra("videotime", videoDetalist1.getVideodate());
						intent.putExtra("videocount", videoDetalist1.getVideocommentcount());
						intent.putExtra("videofrom", videoDetalist1.getVideosource());
						context.startActivity(intent);
					}
				});
		setListViewHeightBasedOnChildren(relatedlListView);
		return view;
	}


	
	// ����listview�ĸ߶ȡ�
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// ��ȡListView��Ӧ��Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()�������������Ŀ
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // ��������View �Ŀ��
			totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
		// params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�

		listView.setLayoutParams(params);

	}

}
