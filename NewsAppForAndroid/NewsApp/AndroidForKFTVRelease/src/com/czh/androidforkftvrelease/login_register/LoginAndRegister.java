package com.czh.androidforkftvrelease.login_register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.Inflater;

import com.czh.androidforkftvrelease.domins.User;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.gsonutil.GsonTools;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.multiplethreads.ThreadExecutor;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAndRegister extends Activity {

	private ViewPager viewPager;
	// 登录和取消的按钮
	private Button login;
	private Button logincancel;
	private EditText luser;
	private EditText lpwd;
	private LayoutInflater inflater;
	private ViewGroup viewGroup;

	// login和register的view
	private View loginView;
	private View registervView;
	// 获得本activity的上下文
	private Context context;
	// 点击注册按钮
	private Button clickRegister;
	// 注册界面的按钮获得
	private Button register;
	private Button registercancle;
	private EditText ruser;
	private EditText rpwd;
	private EditText rpwd1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_register_xml);
		// 设置exec
		
        context=this;
		clickRegister = (Button) findViewById(R.id.clickregister);
		// 获得加载
		inflater = getLayoutInflater().from(this);
		viewGroup = (ViewGroup) findViewById(R.id.loginandregister);
		// 获得登陆的view
		loginView = inflater.inflate(R.layout.login, null);
		// 获得login和cancel按钮
		login = (Button) loginView.findViewById(R.id.loginbtn);
		logincancel = (Button) loginView.findViewById(R.id.cancel);
		luser = (EditText) loginView.findViewById(R.id.inputuser);
		lpwd = (EditText) loginView.findViewById(R.id.inputpwd);
		viewGroup.removeAllViews();
		viewGroup.addView(loginView);

		// 获得注册的view
		registervView = inflater.inflate(R.layout.register, null);
		register = (Button) registervView.findViewById(R.id.registerbtn);
		registercancle = (Button) registervView.findViewById(R.id.cancelbtn);
		ruser = (EditText) registervView.findViewById(R.id.inputreuser);
		rpwd = (EditText) registervView.findViewById(R.id.inputrepwd);
		rpwd1 = (EditText) registervView.findViewById(R.id.inputrepwd2);

		// 设置clickregister的点击事件
		clickRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickRegister.setText("欢迎注册，亲！");
				viewGroup.removeAllViews();
				viewGroup.addView(registervView);

			}
		});
		// 取消登陆的按钮，设置监听器
		logincancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 取消注册的按钮，设置监听器
		registercancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		// 登录按钮注册事件
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checklogin() == true) {
					String user = luser.getText().toString();
					String pwd = lpwd.getText().toString();
					String userjson=setJson("login",user);
					System.out.println("userjson:"+userjson);
					if(userjson.equals("null")||userjson.equals("[]")||userjson.equals("empty"))
					{
						Toast.makeText(context, "用户名不存在", Toast.LENGTH_LONG).show();
						return;
					}
					User user2=GsonTools.GetSingle(userjson, User.class);
					
					pwd=pwd.trim();
					
						
					if(pwd.equals(user2.getPassword()))
					{
						SharedPreferences lr=getSharedPreferences("lr",MODE_PRIVATE );
						Editor editor=lr.edit();
						editor.putString("user",user );
						editor.putString("pwd", pwd);
						editor.commit();
						finish();
					}
					else {
						
						Toast.makeText(context, "密码错误", Toast.LENGTH_LONG).show();
						return;
					}
					
				} else {
					luser.setText("");
					lpwd.setText("");
				}

			}
		});
		// 注册按钮注册事件
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkregister() == true) {
					String user1 = ruser.getText().toString();
					String pwd = rpwd.getText().toString();
					String pwd1 = rpwd1.getText().toString();
					
					String check=setJson("login",user1);
					if(!check.equals("empty"))
					{
						Toast.makeText(context, "该用户名已经存在", Toast.LENGTH_LONG).show();
						return;
					}
					user1=user1.trim();
					pwd=pwd.trim();
					
					User user=new User(user1, pwd, "1.jpg", "河南", "zhong");
					String json=GsonTools.createJsonString(user);
					String back=setJson("insertuser",json);
					if(back.equals("ok"))
					{
						Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
						SharedPreferences lr=getSharedPreferences("lr",MODE_PRIVATE );
						Editor editor=lr.edit();
						editor.putString("user",user1 );
						editor.putString("pwd", pwd);
						editor.commit();
						finish();
						return;
					}
					else{
						Toast.makeText(context, "注册失败", Toast.LENGTH_LONG).show();
					}
					
				} else {
					ruser.setText("");
					rpwd.setText("");
					rpwd1.setText("");
				}
			}
		});
	}
	//执行器，执行线程并且获得返回的值。
	public String setJson(String type,String username1)
	{
		String backString="";
		List<Map<String, String>> params=new ArrayList<Map<String,String>>();
		List<String> result=new ArrayList<String>();
		Map<String, String> map1=new HashMap<String, String>();
		map1.put("action_flag", type);
		map1.put("value", username1);
		params.add(map1);
		result=ThreadExecutor.getResult(DataUrl.DATAURL,params);
	    backString=result.get(0);
		return backString;
		
	}
	
	//获得user的json字符串
	public String insertuser(User user)
	{
		String json=GsonTools.createJsonString(user);
		return json;
	}

	// 检测用户名或密码是否为空
	private boolean checklogin() {

		String user = luser.getText().toString();
		String pwd = lpwd.getText().toString();
		if (user.equals("")) {
			Toast.makeText(this, "用户名为空！！", Toast.LENGTH_LONG).show();
			return false;
		} else {
			if (pwd.equals("")) {
				Toast.makeText(this, "密码为空", Toast.LENGTH_LONG).show();
				return false;
			}

		}

		return true;

	}

	// 检测register
	private boolean checkregister() {
		String user = ruser.getText().toString();
		String pwd = rpwd.getText().toString();
		String pwd1 = rpwd1.getText().toString();
		pwd=pwd.trim();
		pwd1=pwd1.trim();
		if (user.equals("")) {
			Toast.makeText(this, "用户名为空！！", Toast.LENGTH_LONG).show();
			return false;
		} else {
			if (pwd.equals("")) {
				Toast.makeText(this, "密码为空", Toast.LENGTH_LONG).show();
				return false;
			} else {
				if (pwd1.equals("")) {
					Toast.makeText(this, "请再输入密码", Toast.LENGTH_LONG).show();
					return false;
				} else {
					if (!pwd.equals(pwd1)) {
						Toast.makeText(this, "输入密码不一样"+pwd+"aaa"+pwd1, Toast.LENGTH_LONG)
								.show();
						return false;
					}
				}
			}

		}

		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
