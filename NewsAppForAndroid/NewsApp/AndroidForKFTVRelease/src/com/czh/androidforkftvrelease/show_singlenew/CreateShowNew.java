package com.czh.androidforkftvrelease.show_singlenew;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.czh.androidforkftvrelease.domins.NewsDetalist;
import com.czh.androidforkftvrelease.domins.NewsPictures;
import com.czh.androidforkftvrelease.domins.NewsRelated;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.ImageCache;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CreateShowNew {
	private View view;
	private Context context;
	private FragmentManager fragmentManager;// FragmentManager是Fragment管理器
	private ImageFetcher imageFetcher;
	private static final String IMAGE_CACHE_DIR = "images";
	private ImageView imageView;
	private TextView title;
	private TextView date;
	private TextView source;
	private TextView content;
	private TextView commentcount;
	private String newstitle;

	// 显示新闻的图片
	private Button pre;
	private Button next;
	private int index;
	// 图片和单个新闻的json字符串
	private String picturejson;
	private String detalistjson;

	// 评论的条数
	private static int count;
	// 相关新闻的listview
	private ListView relatedlListView;

	public CreateShowNew(Context context, FragmentManager fragmentManager,
			String newstitle) {
		this.context = context;
		this.fragmentManager = fragmentManager;
		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
				context, IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f);
		imageFetcher = new ImageFetcher(context, 30);
		imageFetcher.addImageCache(fragmentManager, cacheParams);
		imageFetcher.setImageFadeIn(false);
		this.newstitle = newstitle;

		index = 0;

	}

	// 获得json字符串
	public String setJson(String type, String value) {
		List<Map<String, String>> params=new ArrayList<Map<String,String>>();
		List<String> result=new ArrayList<String>();
		Map<String, String> map1=new HashMap<String, String>();
		map1.put("action_flag", type);
		map1.put("value",value);
		params.add(map1);
		result=ThreadExecutor.getResult(DataUrl.DATAURL,params);
		return result.get(0);
		
	}

	// 获得新闻展示的view
	public View getView() {
		View view = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.comment_detalist, null);
		imageView = (ImageView) view.findViewById(R.id.shownewsimage);
		pre = (Button) view.findViewById(R.id.previous);
		next = (Button) view.findViewById(R.id.next);
		title = (TextView) view.findViewById(R.id.single_title);
		date = (TextView) view.findViewById(R.id.single_time);
		source = (TextView) view.findViewById(R.id.single_source);
		content = (TextView) view.findViewById(R.id.single_content);

		commentcount = (TextView) view.findViewById(R.id.single_comments);
		title.setText(newstitle);
		picturejson = setJson("pictures", "海底世界");
		System.out.println("picturejson:"+picturejson);
		final List<Map<String, Object>> list = getpictures(picturejson);

		imageFetcher.loadImage(DataUrl.PIC
				+ list.get(index).get("newspicture").toString(), imageView);
		detalistjson = setJson("new", "海底世界");
		NewsDetalist newsDetalist = getDetalist(detalistjson);
		date.setText(newsDetalist.getNewsdate());
		source.setText(newsDetalist.getNewssource());
		content.setText(newsDetalist.getNewscontent());
		commentcount.setText(newsDetalist.getCommentcount().toString());
		count = Integer.parseInt(newsDetalist.getCommentcount().toString());
		pre.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (index > 0) {
					index--;
				} else {
					index = list.size() - 1;
				}
				imageFetcher
						.loadImage(
								DataUrl.PIC
										+ list.get(index).get("newspicture")
												.toString(), imageView);
			}
		});
		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (index < list.size() - 1) {
					index++;

				} else {
					index = 0;
				}
				imageFetcher
						.loadImage(
								DataUrl.PIC
										+ list.get(index).get("newspicture")
												.toString(), imageView);
			}
		});

		relatedlListView = (ListView) view.findViewById(R.id.relatednews);
		String related = setJson("relatednews", newstitle);

		List<Map<String, Object>> reList = GsonTools.GetListMap(related,
				NewsRelated.class);
		List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < reList.size(); i++) {
			list2.add(reList.get(i).get("newsrelated").toString());
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
						intent.setClass(context, SingleNewsActivity.class);
						String title = ((TextView) arg1
								.findViewById(R.id.relatedtitle)).getText()
								.toString();
						
						detalistjson = setJson("new", title);
						NewsDetalist newsDetalist1 = getDetalist(detalistjson);
						
						intent.putExtra("newstitle", title);
						intent.putExtra("newstime", newsDetalist1.getNewsdate());
						intent.putExtra("newssource", newsDetalist1.getNewssource());
						intent.putExtra("newscount", newsDetalist1.getCommentcount());
						intent.putExtra("index", 0);
						//intent.putExtra("newstitle", "海底世界");
						intent.putExtra("index", 0);
						context.startActivity(intent);
					}
				});
		setListViewHeightBasedOnChildren(relatedlListView);
		return view;
	}

	// 返回评论的条数
	public int getcount() {
		return count;
	}

	public List<Map<String, Object>> getpictures(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list = GsonTools.GetListMap(json, NewsPictures.class);

		return list;
	}

	// 由json字符串的到单个新闻的相关值如新闻标题，来源，日期等。
	public NewsDetalist getDetalist(String json) {
		NewsDetalist detalist = new NewsDetalist();
		detalist = GsonTools.GetSingle(json, NewsDetalist.class);
		return detalist;
	}

	// 设置listview的高度。
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度

		listView.setLayoutParams(params);

	};

}
