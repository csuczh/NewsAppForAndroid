package com.czh.androidforkftvrelease.fragments;

import com.czh.androidforkftvrelease.R;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Window;
/**
 *MainView
 * @author ZHF
 *
 */
public class MainFragment extends FragmentActivity implements BottomFragment.Callbacks {
	
	public final static String Item = "item";
	public  static int screenWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
			
		setContentView(R.layout.main);
		//��ʼ��Ĭ�ϵ��ýӿ���itemѡ�з���
		onItemSelected(R.id.fragment_bottom_new);
		
       
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth=dm.widthPixels;
		
		//�����û���¼����Ϣ�����û��������롣
		SharedPreferences lr=this.getSharedPreferences("lr", MODE_PRIVATE);
		Editor editor=lr.edit();
		editor.putString("user", "");
		editor.putString("pwd", "");
		editor.commit();
		
		
	}

	@Override
	public void onItemSelected(int item) {
		Bundle arguments = new Bundle();
		arguments.putInt(Item, item); //��ѡ�еĵײ�radio��Id�Ž�ȥ
		GeneralFragment generalFragment = new GeneralFragment();
		generalFragment.setArguments(arguments); //���ò���ֵ
		//�������item��ID���н�����ת
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.main_detail_FrameLayout, generalFragment).commit();
	}
}
