package com.czh.androidforkftvrelease.comment;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.czh.androidforkftvrelease.domins.NewsComment;
import com.czh.androidforkftvrelease.domins.NewsDetalist;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;

import android.R.color;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Xml.Encoding;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

public class CreateCommentView {
	private View view;

	private Context context;
	private FragmentManager fragmentManager;
	// 新闻标题，从界面传递
	private final String newstitle;
	// 新闻标题，从服务器获得
	private TextView newstitletextView;
	// 新闻的来源
	private TextView newssource;
	// 新闻的时间
	private TextView newstime;
	// 新闻评论的数量
	private TextView commentcount;
	// 最热新闻评论大标题
	private TextView commentlately;

	// 评论和新闻详细json字符串
	private String commentjson;
	private String detalistjson;
	private ExecutorService exec;
	// 所有评论的listview
	private CommentListView allCommentsListView;
	// allcommentsListView的adapter
	private CommentAdapter alladapter;

	private String newfrom;
	private String newsdate;
	private String newscommentcount;
	public CreateCommentView(Context context, FragmentManager fragmentManager,
			final String newstitle,String newfrom,String newsdate,String newscommentcount) {

		this.context = context;
		this.fragmentManager = fragmentManager;
		this.newstitle = newstitle;
		
		this.newfrom=newfrom;
		this.newsdate=newsdate;
		this.newscommentcount=newscommentcount;
		
		allCommentsListView = new CommentListView(context);
		allCommentsListView.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT));
		allCommentsListView.setCacheColorHint(Color.TRANSPARENT);
		allCommentsListView.setBackgroundColor(color.white);
		String json = setJson("allcomments", newstitle);
		List<Map<String, Object>> list = GsonTools.GetListMap(json,
				NewsComment.class);
		alladapter = new CommentAdapter(context, fragmentManager);
		alladapter.refreshData(list);

		allCommentsListView.setAdapter(alladapter);

	}

	// 获得评论的列表
	public CommentListView getAllCommentsListView() {
		return allCommentsListView;
	}

	public void refresh() {

		String json = setJson("allcomments", newstitle);
		List<Map<String, Object>> list = GsonTools.GetListMap(json,
				NewsComment.class);
		alladapter = new CommentAdapter(context, fragmentManager);
		alladapter.refreshData(list);
		alladapter.notifyDataSetChanged();

	}

	public View getView(LayoutInflater inflater) {

		ListView listView;
		view = inflater.inflate(R.layout.comments_items, null);
		
		
	
		
		newstitletextView = (TextView) view.findViewById(R.id.comment_title);
		newssource = (TextView) view.findViewById(R.id.commentsource);
		newstime = (TextView) view.findViewById(R.id.commenttime);
		commentcount = (TextView) view.findViewById(R.id.commentcount);
		commentlately = (TextView) view.findViewById(R.id.newsupdate);
		
		
		newstitletextView.setText(newstitle);
		newstime.setText(newsdate);
		newssource.setText(newfrom);
		commentcount.setText(newscommentcount);
		
		
		
		
		listView = (ListView) view.findViewById(R.id.commentlist);
		listView.setCacheColorHint(color.transparent);
		final CommentAdapter adapter = new CommentAdapter(context,
				fragmentManager);
		commentjson = setJson("comments", newstitle);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = getData(commentjson);
		commentlately.setText("最热评论(" + list.size() + ")");
		adapter.refreshData(getData(commentjson));
		listView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView);
		CommentOnItemClick click = new CommentOnItemClick(context);
		listView.setOnItemClickListener(click);
		allCommentsListView.setOnItemClickListener(click);

		ViewGroup viewGroup = (ViewGroup) view
				.findViewById(R.id.allcommentsLinearLayout);
		viewGroup.removeAllViews();
		viewGroup.addView(allCommentsListView);

		allCommentsListView
				.setOnLoadeMoreListener(new CommentListView.OnLoadeMoreListener() {

					@Override
					public void onLoade() {
						// TODO Auto-generated method stub
						String json = setJson("allcomments", newstitle);
						List<Map<String, Object>> list = GsonTools.GetListMap(
								json, NewsComment.class);

						alladapter.addData(list);
						alladapter.notifyDataSetChanged();
						allCommentsListView.onLoadeComplete();
					}
				});
		return view;
	}

	public String getcommentcount() {
		return commentcount.getText().toString();
	}

	// 设置所有新闻评论的列表
	public void setAllComments() {

	}

	// 获得json字符串
	public String setJson(String type, String value) {
		List<Map<String, String>> params = new ArrayList<Map<String, String>>();
		List<String> result = new ArrayList<String>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("action_flag", type);
		map1.put("value", value);
		params.add(map1);
		result = ThreadExecutor.getResult(DataUrl.DATAURL, params);
		return result.get(0);
	}

	// 获得相应新闻评论的数据

	private List<Map<String, Object>> getData(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = GsonTools.GetListMap(json, NewsComment.class);

		return list;
	}

	// 获取相应新闻的标题，时间，摘要等信息的类
	public NewsDetalist getDetalist(String json) {
		NewsDetalist detalist = new NewsDetalist();

		detalist = GsonTools.GetSingle(json, NewsDetalist.class);

		return detalist;
	}

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
