package com.czh.androidforkftvrelease.comment;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.PrivateCredentialPermission;

import com.czh.androidforkftvrelease.R;

import android.R.integer;
import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.GetChars;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class CommentListView extends ListView implements OnScrollListener {

	public static int count = 0;
	// Log�Ĺ�����
	private static final String TaG = "alarm:";

	// padding�ľ����������ƫ�ƾ���ı���
	private final static int RATIO = 3;

	private LayoutInflater inflater;

	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean isRecored;

	// Y�������ʼλ��
	private int startY;
	// ��һ����Ŀ�ĵ�����
	private int firstItemIndex;

	// listviewheader�Ŀ�Ⱥ͸߶�
	private int headContentWidth;
	private int headContentHeight;

	// ����ʱ��״̬
	private int state;
	// �ж���ֱ�ӽ����pull_refesh,������reaslse����refesh��pull_refesh
	private boolean isBack;
	// �ص��ӿڵı���
	private OnRefreshListener refreshListener;
	// �����Ƿ�ˢ��
	private boolean isRefreshable;
	public final static String Tag = "listview";

	// ����listview��footer���ظ����ѡ���״̬��
	private int footcontentheight;
	private int footcontentwidth;
	private final static int NORMAL = 6;
	private final static int LOADMORE = 7;
	private final static int HIDE = 8;
	private LinearLayout footlayout;
	private int footerstate;
	private ProgressBar progressBar;
	private TextView addMore;
	private int lastItemIndex;
	private int totalCount;
	private OnLoadeMoreListener onLoadeMoreListener;

	public CommentListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public CommentListView(Context context, AttributeSet attrs) {
		super(context);
		init(context);

	}

	private void init(Context context) {
		// ���������л�ü�����
		inflater = LayoutInflater.from(context);

		// ���û������¼��ļ�����
		setOnScrollListener(this);

		// ����listview��footer
		footlayout = (LinearLayout) inflater.inflate(
				R.layout.listview_footer_more, null);
		progressBar = (ProgressBar) footlayout
				.findViewById(R.id.listview_footer_progressbar);
		addMore = (TextView) footlayout
				.findViewById(R.id.listview_footer_hint_textview);
		measureView(footlayout);

		footcontentheight = footlayout.getMeasuredHeight();
		footcontentwidth = footlayout.getMeasuredWidth();
		// footlayout.setPadding(0, 0, 0, (-1)*footcontentheight);
		footlayout.setPadding(0, (-1) * footcontentheight, 0, 0);
		// footlayout.setPadding(0, 0, 0, 0);
		footlayout.invalidate();
		addFooterView(footlayout, null, false);
		footerstate = HIDE;
		isRefreshable=true;

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		firstItemIndex = firstVisibleItem;
		lastItemIndex = firstVisibleItem + visibleItemCount - 1;
		totalCount = totalItemCount - 1;
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if ((firstItemIndex == 0 || lastItemIndex == totalCount)
						&& !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
					System.out.println("start" + startY);
				}
				break;
			case MotionEvent.ACTION_UP:
				if (footerstate != LOADMORE) {

					if (footerstate == NORMAL) {
						footerstate = LOADMORE;
						changeFooterViewByState();
						onLoade();

					}
				}
				isRecored = false;

				break;
			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				if (!isRecored && lastItemIndex == totalCount) {
					isRecored = true;
					startY = tempY;
				}
				if (footerstate != LOADMORE) {

					if (footerstate == HIDE) {
						if (startY - tempY > 0) {
							footerstate = NORMAL;

							changeFooterViewByState();

						}
					}
				}
				break;

			}
		}
		return super.onTouchEvent(event);
	}

	private void changeFooterViewByState() {
		// TODO Auto-generated method stub
		switch (footerstate) {
		case NORMAL:
			footlayout.setPadding(0, 0, 0, 0);
			progressBar.setVisibility(View.VISIBLE);
			addMore.setText("���ظ���");
			addMore.setVisibility(View.VISIBLE);
			break;
		case HIDE:
			footlayout.setPadding(0, (-1) * footcontentheight, 0, 0);
			break;
		case LOADMORE:
			footlayout.setPadding(0, 0, 0, 0);
			addMore.setVisibility(View.VISIBLE);
			addMore.setText("���ظ���");
			progressBar.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	

	public interface OnLoadeMoreListener {
		public void onLoade();
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void setOnLoadeMoreListener(OnLoadeMoreListener onLoadeMoreListener) {
		this.onLoadeMoreListener = onLoadeMoreListener;
	}

	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.refreshListener = onRefreshListener;
		isRefreshable = true;
	}

	public void onLoadeComplete() {
		footerstate = HIDE;
		changeFooterViewByState();
	}

	private void onLoade() {
		if (onLoadeMoreListener != null) {
			onLoadeMoreListener.onLoade();
		}
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	public void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��");
		String date = format.format(new Date());
		super.setAdapter(adapter);

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

}
