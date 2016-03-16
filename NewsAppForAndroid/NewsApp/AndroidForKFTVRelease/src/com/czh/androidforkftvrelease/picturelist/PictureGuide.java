package com.czh.androidforkftvrelease.picturelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.view.View.OnClickListener;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.domins.PictureType;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PictureGuide {

		//view容器
		private View mycontent;
		//头部菜单的数组
		private String[][] menus = new String[30][6];
		private int pagerIndex = 0;
		//每一项菜单对应的view
		private ArrayList<View> menuViews = null;

		private View main = null;
		private ViewPager viewPager = null;
		//头部箭头的imageiew
		private ImageView imagePrevious = null;
		private ImageView imageNext = null;

		private int screenWidth;
		private LayoutInflater inflater;
		private FragmentManager fragmentManager;
		private PictureSlidMenuLayout menu;

		private String jsonstr;
		private String type;
		private int rowcount;
		private int remcount;
		private int pagecount;
		//网络下载
		private String url;
		//连接服务器的参数
		private List<Map<String, String>> params=new ArrayList<Map<String,String>>();
		//获得服务器的值
		private List<String> result=new ArrayList<String>();

		public PictureGuide(View mycontent, int screenWidth, LayoutInflater inflater,
				FragmentManager fragmentManager, String type,int pagecount,String url) {
			super();
			this.mycontent = mycontent;
			this.screenWidth = screenWidth;
			this.inflater = inflater;
			this.fragmentManager = fragmentManager;
			this.type = type;
			this.pagecount=pagecount;
			this.url=url;
			params=new ArrayList<Map<String,String>>();
			result=new ArrayList<String>();
			GetData();		
			jsonstr=result.get(0);
			System.out.println("jsonstr-addguide"+jsonstr);
			List<Map<String, Object>> list = GsonTools.GetListMap(jsonstr,
				 PictureType.class);
			
			rowcount = ((list.size()) % pagecount) == 0 ? ((list.size()) / pagecount) : (((list
					.size()) /pagecount) + 1);
			remcount = (list.size()) % pagecount;
			System.out.println("rowcount" + rowcount + "    " + "remcount:"
					+ remcount);
			int k = 0;
			if (remcount == 0) {
				for (int i = 0; i < rowcount; i++) {
					for (int j = 0; j < pagecount; j++)
						menus[i][j] = (String) list.get(k++).get("picturetype");
				}
			} else {
				for (int i = 0; i < rowcount - 1; i++) {
					for (int j = 0; j < pagecount; j++)
						menus[i][j] = (String) list.get(k++).get("picturetype");
				}
				for (int m = 0; m < remcount; m++) {
					menus[rowcount - 1][m] = (String) list.get(k++).get("picturetype");
				}
			}

		}
	 //使用线程管理器获得从服务器返回的数据
		private void GetData()
		{
			Map<String, String> map1=new HashMap<String, String>();
			map1.put("action_flag", type);
			map1.put("value", "");
			params.add(map1);
			result=ThreadExecutor.getResult(DataUrl.PICTURE,params);
		}
		public View SetGuide() {

			main = inflater.inflate(R.layout.top_guide, null);
			menuViews = new ArrayList<View>();
			menu = new PictureSlidMenuLayout(inflater, main, fragmentManager,url);
			for (int i = 0; i < rowcount; i++) {
				menuViews.add(menu.getSlidMenuLinnerLayout(menus[i], screenWidth));

			}
			imagePrevious = (ImageView) main.findViewById(R.id.ivPreviousButton);
			imageNext = (ImageView) main.findViewById(R.id.ivNextButton);
			imagePrevious.setOnClickListener(new ImagePreviousOnclickListener());
			imageNext.setOnClickListener(new ImageNextOnclickListener());

			if (menuViews.size() > 1) {
				imageNext.setVisibility(View.VISIBLE);
			}
		//	ViewGroup llc = (ViewGroup) main.findViewById(R.id.linearLayoutContent);
			menu.slideMenuOnChange("");

			viewPager = (ViewPager) main.findViewById(R.id.slideMenu);
			viewPager.setAdapter(new SlideMenuAdapter());
			viewPager.setOnPageChangeListener(new slideMenuChangeListener());

			return main;

		}

		class SlideMenuAdapter extends PagerAdapter {

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				super.destroyItem(container, position, object);
				container.removeView(menuViews.get(position));
			}

			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				return super.getItemPosition(object);
			}

			@Override
			public Object instantiateItem(View container, int position) {
				// TODO Auto-generated method stub
				((ViewPager) container).addView(menuViews.get(position));
				return menuViews.get(position);
			}

			@Override
			public void notifyDataSetChanged() {
				// TODO Auto-generated method stub
				super.notifyDataSetChanged();
			}

			@Override
			public void restoreState(Parcelable state, ClassLoader loader) {
				// TODO Auto-generated method stub
				super.restoreState(state, loader);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return menuViews.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

		}

		class slideMenuChangeListener implements OnPageChangeListener {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				int pageCount = menuViews.size() - 1;
				pagerIndex = arg0;

				if (arg0 >= 0 && arg0 < pageCount) {
					imageNext.setVisibility(View.VISIBLE);
				} else {
					imageNext.setVisibility(View.INVISIBLE);
				}
				if (arg0 > 0 && arg0 <= pageCount) {
					imagePrevious.setVisibility(View.VISIBLE);
				} else {
					imagePrevious.setVisibility(View.INVISIBLE);
				}
			}
		}

		class ImageNextOnclickListener implements OnClickListener {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pagerIndex++;
				viewPager.setCurrentItem(pagerIndex);
				menu.slideMenuOnChange(menus[pagerIndex][0]);

			}

		}

		class ImagePreviousOnclickListener implements OnClickListener {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pagerIndex--;
				viewPager.setCurrentItem(pagerIndex);
				menu.slideMenuOnChange(menus[pagerIndex][0]);
			}

		}

	

}
