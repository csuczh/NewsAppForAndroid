package com.czh.androidforkftvrelease.comment;

import com.czh.androidforkftvrelease.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

public class CommentOnItemClick implements OnItemClickListener {

	private Context context;

	public CommentOnItemClick(Context context) {
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
          new CommentDialog(context).show();
	}

	
}
