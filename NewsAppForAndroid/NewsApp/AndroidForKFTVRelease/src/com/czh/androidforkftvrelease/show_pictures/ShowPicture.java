package com.czh.androidforkftvrelease.show_pictures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.czh.androidforkftvrelease.BuildConfig;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.comment.CreateCommentView;
import com.czh.androidforkftvrelease.domins.PictureDetalist;
import com.czh.androidforkftvrelease.domins.SingleComment;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.load_images.Utils;
import com.czh.androidforkftvrelease.login_register.LoginAndRegister;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;
import com.czh.androidforkftvrelease.show_singlenew.CreateShowNew;

public class ShowPicture extends FragmentActivity {
	private ViewPager mPager;
	private String[] images;

	private View newsView;
	private View commentView;
	private String title;
	private List<View> list;

	// ��������
	private Button comment;
	private EditText editText;
	private Context context;

	// ���ۿ����Ƿ�����������
	private boolean isempty;
	// ��������۵�ֵ
	String commenttext = "";

	// ��ôӷ������õ���json�ַ���
	List<String> result;
	// ����ͼƬ���ŵ�ͼƬ����
	private int count;
	// ����
	private TextView textView;
   //ͷ������������
	private RelativeLayout relativeLayout;
  //�ײ����۵Ŀ�
	private RelativeLayout commentLayout;
	//���۵�������
	private String commentcount;
	
	private String picturesource;
	private String picturedate;
	private String picturecommentcount;
	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) {
			Utils.enableStrictMode();
		}
		super.onCreate(savedInstanceState);
		result = new ArrayList<String>();
		setContentView(R.layout.news_singlenew_main_xml);
		textView = (TextView) this.findViewById(R.id.main_title_TextView);
		textView.setText("ͼƬ");
		
		relativeLayout=(RelativeLayout)this.findViewById(R.id.main_title_RelativeLayout);
		commentLayout=(RelativeLayout)this.findViewById(R.id.commentlayout);
		// ͨ��intent��õ�title��count
		title = getIntent().getExtras().get("pnewtitle").toString();
		picturecommentcount=getIntent().getExtras().get("picturecommentcount").toString();
		picturedate = getIntent().getExtras().get("picturedate").toString();
		picturesource = getIntent().getExtras().get("picturesource").toString();
	    String pcount=getIntent().getExtras().get("picturecount").toString();
		count = (int) Double.parseDouble(pcount);
		isempty = true;
		// ���õ�������չʾ������view��mpager��
		mPager = (ViewPager) findViewById(R.id.newsviewpager);
		CreateCommentView createCommentView = new CreateCommentView(this,
				getSupportFragmentManager(),title,picturesource,picturedate,picturecommentcount);
		list = new ArrayList<View>();
		GetData(title);
		List<Map<String, Object>> list1 = GsonTools.GetListMap(result.get(0)
				.toString(), PictureDetalist.class);
		for (int i = 0; i < count; i++) {
			String picturename = list1.get(i).get("picturetitle").toString();
			String description = list1.get(i).get("picturedescription")
					.toString();
			CreateShowPicture cPicture = new CreateShowPicture(this,
					getSupportFragmentManager(), picturename, description);
			View view=cPicture.getView();
			view.setOnClickListener(new MyOnClick());
			list.add(view);
		}

		View commentView = createCommentView.getView(getLayoutInflater());
		list.add(commentView);
		commentcount=createCommentView.getcommentcount();
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
	
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

				if(ViewPager.SCROLL_STATE_SETTLING==arg0)
				{
					if (mPager.getCurrentItem() == mPager.getAdapter().getCount()-1) {
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
		comment.setText(commentcount);
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
							mPager.setCurrentItem(mPager.getAdapter().getCount()-1,
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

						mPager.setCurrentItem(mPager.getAdapter().getCount()-1, true);
						editText.setText("˵����תһ��");

					}
					
				} else {
					mPager.setCurrentItem(0, true);
					comment.setText(commentcount);
				}

			}
		});

	}

	public class MyOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (editText.getText().toString().equals("")) {
				editText.setText("˵����תһ��");
				isempty = true;
			}
			TextView text=(TextView)v.findViewById(R.id.picture_text);
			if(commentLayout.getVisibility()==View.VISIBLE)
			{
				commentLayout.setVisibility(View.INVISIBLE);
				relativeLayout.setVisibility(View.INVISIBLE);
				text.setVisibility(View.INVISIBLE);
				
			}
			else {
				commentLayout.setVisibility(View.VISIBLE);
				relativeLayout.setVisibility(View.VISIBLE);
				text.setVisibility(View.VISIBLE);
			}
			
		}

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

	// ���һ�����ŵ�����ͼƬ
	public void GetData(String value) {
		List<Map<String, String>> params = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("action_flag", "showpicture");
		map.put("value", value);
		params.add(map);
		result = ThreadExecutor.getResult(DataUrl.PICTURE, params);
	}

	// ����ע��͵�¼���淵����ʱ������
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
