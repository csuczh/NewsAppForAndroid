/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.czh.androidforkftvrelease.show_singlenew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.czh.androidforkftvrelease.comment.CreateCommentView;
import com.czh.androidforkftvrelease.domins.SingleComment;
import com.czh.androidforkftvrelease.BuildConfig;
import com.czh.androidforkftvrelease.load_images.ImageCache;
import com.czh.androidforkftvrelease.load_images.ImageFetcher;
import com.czh.androidforkftvrelease.load_images.Utils;
import com.czh.androidforkftvrelease.login_register.LoginAndRegister;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class SingleNewsActivity extends FragmentActivity {
	private ViewPager mPager;
	private String[] images;

	private View newsView;
	private View commentView;
	private String title;
	private List<View> list;

	// 评论新闻
	private Button comment;
	private EditText editText;
	private Context context;

	// 评论框中是否输入了评论
	private boolean isempty;
	// 输入的评论的值
	String commenttext = "";
    private String newsdate;
    private String newssource;
    private String newscount;
	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) {
			Utils.enableStrictMode();
		}
		super.onCreate(savedInstanceState);

		setContentView(R.layout.news_singlenew_main_xml);
		title = getIntent().getExtras().get("newstitle").toString();
		newsdate=getIntent().getExtras().getString("newstime");
		newssource=getIntent().getExtras().getString("newssource");
		newscount=getIntent().getExtras().getString("newscount");
		
		
		isempty = true;
		// 设置单个新闻展示和评论view的mpager。
		mPager = (ViewPager) findViewById(R.id.newsviewpager);
		final CreateShowNew createShowNew = new CreateShowNew(this,
				getSupportFragmentManager(), title);
		CreateCommentView createCommentView = new CreateCommentView(this,
				getSupportFragmentManager(), title,newssource,newsdate,newscount);
		list = new ArrayList<View>();
		View showView = createShowNew.getView();
		View commentView = createCommentView.getView(getLayoutInflater());
		list.add(showView);
		list.add(commentView);
		// mpager的适配器。
		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {

				return arg0 == arg1;
			}

			@Override
			public int getCount() {

				return list.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(list.get(position));

			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(list.get(position));
				return list.get(position);
			}

		};
		mPager.setAdapter(pagerAdapter);
		//设置mpager滑动的事件
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(arg0==mPager.getChildCount()-1)
				{
					comment.setText("原文");
				}
				else {
					comment.setText(createShowNew.getcount() + "");
				}
			
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			
			}
		});
		// 评论的按钮，点击是转到注册和登录的activity。
		comment = (Button) findViewById(R.id.comment_button);
		editText = (EditText) findViewById(R.id.comment_news);
		comment.setText(createShowNew.getcount() + "");
		commentView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editText.getText().toString().equals("")) {
					editText.setText("说两句转一下");
					isempty = true;
				}
			}
		});
		//设置showView的单击事件
		showView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editText.getText().toString().equals("")) {
					editText.setText("说两句转一下");
					isempty = true;
				}
			}
		});
		// 设置edittext的点击事件，当点击是把默认的文字去掉
		editText.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// 当按下时，设置edittext为空
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (isempty) {
						editText.setText("");
					}

				}

				return false;
			}
		});
		context = this;
		// 点击评论的按钮的clicklistner监听器
		comment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!comment.getText().toString().equals("原文")) {
					//获得存放的用户名和密码
					SharedPreferences sharedPreferences = getSharedPreferences(
							"lr", MODE_PRIVATE);
					String user = sharedPreferences.getString("user", "");
					String pwd = sharedPreferences.getString("pwd", "");

					if (user.equals("")) {

						commenttext = editText.getText().toString();
						if (commenttext.equals("")
								|| commenttext.equals("说两句转一下")) {
							mPager.setCurrentItem(mPager.getChildCount() - 1,
									true);
							comment.setText("原文");
						} else {
							Intent intent = new Intent();
							intent.setClass(context, LoginAndRegister.class);
							context.startActivity(intent);

						}

					} else {
						// 获得评论的时间
					   String now=Time.getCurrentTimezone().toString();
						String newstitle = title;
						String commentcontent = editText.getText().toString();
						String cuser = user;
						SingleComment singleComment = new SingleComment(cuser,
								newstitle, now, commentcontent);
						String json = GsonTools.createJsonString(singleComment);
						String existcomment = setJson("searchcomment", cuser
								+ "_" + newstitle);
						if (existcomment.equals("faile")) {
							String back = setJson("updatecomment", json);
							if (back.equals("ok")) {
								Toast.makeText(context, "评论成功",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(context, "评论失败",
										Toast.LENGTH_LONG).show();
							}

						} else {
							String back = setJson("insertcomment", json);
							if (back.equals("ok")) {
								Toast.makeText(context, "评论成功",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(context, "评论失败",
										Toast.LENGTH_LONG).show();
							}

						}

						mPager.setCurrentItem(mPager.getChildCount() - 1, true);
						editText.setText("说两句转一下");

					}
				} else {
					mPager.setCurrentItem(0, true);
					comment.setText(createShowNew.getcount() + "");
				}

			}
		});

	}

	// 执行器，执行线程并且获得返回的值。
	public String setJson(String type, String username1) {
		List<Map<String, String>> params=new ArrayList<Map<String,String>>();
		Map<String,String> map=new HashMap<String, String>();
		map.put("action_flag", type);
		map.put("value", username1);
		params.add(map);
		return ThreadExecutor.getResult(DataUrl.DATAURL,params).get(0);
		
	}

	// 当从注册和登录界面返回来时，调用
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		SharedPreferences sharedPreferences = getSharedPreferences("lr",
				MODE_PRIVATE);
		String user = sharedPreferences.getString("user", "");
		String pwd = sharedPreferences.getString("pwd", "");
		if (!user.equals("")) {
			String now = String.valueOf(Time.YEAR) + String.valueOf(Time.MONTH)
					+ String.valueOf(Time.MONTH_DAY)
					+ String.valueOf(Time.MINUTE);
			String newstitle = title;
			String commentcontent = editText.getText().toString();
			String cuser = user;
			SingleComment singleComment = new SingleComment(cuser, newstitle,
					now, commentcontent);
			String json = GsonTools.createJsonString(singleComment);
			
			String existcomment = setJson("searchcomment", cuser
					+ "_" + newstitle);
			if (existcomment.equals("faile")) {
				String back = setJson("updatecomment", json);
				if (back.equals("ok")) {
					Toast.makeText(context, "评论成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(context, "评论失败",
							Toast.LENGTH_LONG).show();
				}

			} else {
				String back = setJson("insertcomment", json);
				if (back.equals("ok")) {
					Toast.makeText(context, "评论成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(context, "评论失败",
							Toast.LENGTH_LONG).show();
				}

			}
			
			mPager.setCurrentItem(mPager.getChildCount() - 1, true);
            editText.setText("说两句转一下");
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

}
