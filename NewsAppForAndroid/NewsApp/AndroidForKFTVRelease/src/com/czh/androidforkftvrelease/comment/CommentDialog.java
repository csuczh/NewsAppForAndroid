package com.czh.androidforkftvrelease.comment;

import com.czh.androidforkftvrelease.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CommentDialog extends Dialog {

	private Button good;
	private Button copy;
	private Button comment;

	public CommentDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setContentView(R.layout.comments_listview_click_showdialog);

		good = (Button) this.findViewById(R.id.good);
		copy = (Button) this.findViewById(R.id.copy);
		comment = (Button) this.findViewById(R.id.comment);

		good.setOnClickListener(new Click());
		copy.setOnClickListener(new Click());
		comment.setOnClickListener(new Click());
	}

	public class Click implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dismiss();
		}

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		Window mWindow = this.getWindow();
	    WindowManager.LayoutParams lp = mWindow.getAttributes();
	    lp.width = 400; // 宽度
        lp.height = 200; // 高度
        lp.alpha = 0.7f; // 透明度
        mWindow.setAttributes(lp);

	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

}
