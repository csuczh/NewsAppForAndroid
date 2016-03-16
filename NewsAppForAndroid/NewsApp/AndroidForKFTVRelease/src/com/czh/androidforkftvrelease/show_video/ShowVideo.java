package com.czh.androidforkftvrelease.show_video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.czh.androidforkftvrelease.BuildConfig;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.comment.CreateCommentView;
import com.czh.androidforkftvrelease.domins.SingleComment;
import com.czh.androidforkftvrelease.domins.VideoItem;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.Utils;
import com.czh.androidforkftvrelease.login_register.LoginAndRegister;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;
import com.czh.androidforkftvrelease.show_singlenew.CreateShowNew;

public class ShowVideo extends FragmentActivity {
	private ViewPager mPager;
	private String[] images;

	private View newsView;
	private View commentView;
	private List<View> list;

	// ��������
	private Button comment;
	private EditText editText;
	private Context context;
	// ���ۿ����Ƿ�����������
	private boolean isempty;
	// ��������۵�ֵ
	String commenttext = "";
	// ���۵�����
	private String commentcount;
   //������Ƶ���ŵ���Ϣ
	private String videotitle;
	private String videodate;
	private String videosummary;
	private String videocontent;
	private String videosource;
	private String videopicture;
	private String videopath;
	private String videocount;
	private TextView bigtitle;
	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) {
			Utils.enableStrictMode();
		}
		super.onCreate(savedInstanceState);

		setContentView(R.layout.news_singlenew_main_xml);
		
		bigtitle = (TextView) this.findViewById(R.id.main_title_TextView);
		bigtitle.setText("��Ƶ");
		videotitle=getIntent().getExtras().get("videotitle").toString();
		videodate=getIntent().getExtras().get("videotime").toString();
		videocount=getIntent().getExtras().get("videocount").toString();
		videosource=getIntent().getExtras().get("videofrom").toString();
		
		isempty = true;
		// ���õ�������չʾ������view��mpager��
		mPager = (ViewPager) findViewById(R.id.newsviewpager);
		final CreateShowVideo createShowVideo = new CreateShowVideo(this,
				getSupportFragmentManager(),videotitle);
		
		CreateCommentView createCommentView = new CreateCommentView(this,
				getSupportFragmentManager(), videotitle,videosource,videodate,videocount);
		list = new ArrayList<View>();
		View showView = createShowVideo.getView();
		View commentView = createCommentView.getView(getLayoutInflater());
		list.add(showView);
		list.add(commentView);

		
		commentcount = createCommentView.getcommentcount();
		// mpager����������
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
		// ����mpager�������¼�
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (ViewPager.SCROLL_STATE_SETTLING == arg0) {
					if (mPager.getCurrentItem() == mPager.getAdapter()
							.getCount() - 1) {
						comment.setText("ԭ��");
					} else {
						comment.setText(commentcount);
					}

				}

			}
		});
		// ���۵İ�ť�������ת��ע��͵�¼��activity��
		comment = (Button) findViewById(R.id.comment_button);
		editText = (EditText) findViewById(R.id.comment_news);
		comment.setText(commentcount+"ssss");
		commentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editText.getText().toString().equals("")) {
					editText.setText("˵����תһ��");
					isempty = true;
				}
			}
		});
		// ����showView�ĵ����¼�
		showView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editText.getText().toString().equals("")) {
					editText.setText("˵����תһ��");
					isempty = true;
				}
			}
		});
		// ����edittext�ĵ���¼���������ǰ�Ĭ�ϵ�����ȥ��
		editText.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// ������ʱ������edittextΪ��
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (isempty) {
						editText.setText("");
					}

				}

				return false;
			}
		});
		context = this;
		// ������۵İ�ť��clicklistner������
		comment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!comment.getText().toString().equals("ԭ��")) {
					// ��ô�ŵ��û���������
					SharedPreferences sharedPreferences = getSharedPreferences(
							"lr", MODE_PRIVATE);
					String user = sharedPreferences.getString("user", "");
					String pwd = sharedPreferences.getString("pwd", "");

					if (user.equals("")) {

						commenttext = editText.getText().toString();
						if (commenttext.equals("")
								|| commenttext.equals("˵����תһ��")) {
							mPager.setCurrentItem(mPager.getChildCount() - 1,
									true);
							comment.setText("ԭ��");
						} else {
							Intent intent = new Intent();
							intent.setClass(context, LoginAndRegister.class);
							context.startActivity(intent);

						}

					} else {
						// ������۵�ʱ��
						String now = Time.getCurrentTimezone().toString();
						String newstitle = videotitle;
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
								Toast.makeText(context, "���۳ɹ�",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(context, "����ʧ��",
										Toast.LENGTH_LONG).show();
							}

						} else {
							String back = setJson("insertcomment", json);
							if (back.equals("ok")) {
								Toast.makeText(context, "���۳ɹ�",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(context, "����ʧ��",
										Toast.LENGTH_LONG).show();
							}

						}

						mPager.setCurrentItem(mPager.getChildCount() - 1, true);
						editText.setText("˵����תһ��");

					}
				} else {
					mPager.setCurrentItem(0, true);
					comment.setText(commentcount);
				}

			}
		});

	}

	// ִ������ִ���̲߳��һ�÷��ص�ֵ��
	public String setJson(String type, String username1) {
		List<Map<String, String>> params = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("action_flag", type);
		map.put("value", username1);
		params.add(map);
		return ThreadExecutor.getResult(DataUrl.DATAURL, params).get(0);

	}

	// ����ע��͵�¼���淵����ʱ������
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		SharedPreferences sharedPreferences = getSharedPreferences("lr",
				MODE_PRIVATE);
		String user = sharedPreferences.getString("user", "");
		String pwd = sharedPreferences.getString("pwd", "");
		if (!user.equals("")) {
			String now = String.valueOf(Time.YEAR) + String.valueOf(Time.MONTH)
					+ String.valueOf(Time.MONTH_DAY)
					+ String.valueOf(Time.MINUTE);
			String newstitle = videotitle;
			String commentcontent = editText.getText().toString();
			String cuser = user;
			SingleComment singleComment = new SingleComment(cuser, newstitle,
					now, commentcontent);
			String json = GsonTools.createJsonString(singleComment);

			String existcomment = setJson("searchcomment", cuser + "_"
					+ newstitle);
			if (existcomment.equals("faile")) {
				String back = setJson("updatecomment", json);
				if (back.equals("ok")) {
					Toast.makeText(context, "���۳ɹ�", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG).show();
				}

			} else {
				String back = setJson("insertcomment", json);
				if (back.equals("ok")) {
					Toast.makeText(context, "���۳ɹ�", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG).show();
				}

			}

			mPager.setCurrentItem(mPager.getChildCount() - 1, true);
			editText.setText("˵����תһ��");
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
