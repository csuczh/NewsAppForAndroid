package com.czh.androidforkftvrelease.life;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.czh.androidforkftvrelease.R;
public class LifeGuide {
	//view容器
		private View mycontent;
		//头部菜单的数组
		private String[][] menus = {{"附近搜索","路线搜索","地点查找"}};
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
		private LifeSlideMenuLayout menu;

		public LifeGuide(View mycontent, int screenWidth, LayoutInflater inflater,
				FragmentManager fragmentManager) {
			super();
			this.mycontent = mycontent;
			this.screenWidth = screenWidth;
			this.inflater = inflater;
			this.fragmentManager = fragmentManager;
			
			
			

		}
	 
		public View SetGuide() {

			main = inflater.inflate(R.layout.top_guide, null);
			menuViews = new ArrayList<View>();
			menu = new LifeSlideMenuLayout(inflater, main, fragmentManager);
			for (int i = 0; i <1; i++) {
				menuViews.add(menu.getSlidMenuLinnerLayout(menus[i], screenWidth));

			}
			imagePrevious = (ImageView) main.findViewById(R.id.ivPreviousButton);
			imageNext = (ImageView) main.findViewById(R.id.ivNextButton);
			imagePrevious.setOnClickListener(new ImagePreviousOnclickListener());
			imageNext.setOnClickListener(new ImageNextOnclickListener());

			if (menuViews.size() > 1) {
				imageNext.setVisibility(View.VISIBLE);
			}
		
			menu.slideMenuOnChange("附近搜索");

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
