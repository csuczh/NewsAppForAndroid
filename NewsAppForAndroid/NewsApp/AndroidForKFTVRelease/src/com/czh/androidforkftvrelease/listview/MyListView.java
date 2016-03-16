package com.czh.androidforkftvrelease.listview;

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

public class MyListView extends ListView implements OnScrollListener{
	
	public static int count=0;
    //Log�Ĺ�����
	private static final String TaG="alarm:";
	//listview�ĸ���״̬
    private final static int RELEASE_TO_REFRESH=0;
    private final static int PULL_TO_REFRESH=1;
    private final static int REFRESHING=2;
    private final static int DONE=3;
    private final static int LOADING=4;
    
    //padding�ľ����������ƫ�ƾ���ı���
    private final static int RATIO=3;
    
    private LayoutInflater inflater;
    private LinearLayout headView;
    private TextView tipsTextView;
    private TextView lastUpdatedTextView;
    private ImageView arrowImageView;
    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;
    //���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
    private boolean isRecored;
    
    //Y�������ʼλ��
    private int startY;
    //��һ����Ŀ�ĵ�����
    private int firstItemIndex;
    
    //listviewheader�Ŀ�Ⱥ͸߶�
    private int headContentWidth;
    private int headContentHeight;
    
    //����ʱ��״̬
    private int state;
    //�ж���ֱ�ӽ����pull_refesh,������reaslse����refesh��pull_refesh
    private boolean isBack;
    //�ص��ӿڵı���
    private OnRefreshListener refreshListener;
    //�����Ƿ�ˢ��
    private boolean isRefreshable;
   public final static String Tag="listview";
   
   //����listview��footer���ظ����ѡ���״̬��
   private int footcontentheight;
   private int footcontentwidth;
   private final static int NORMAL=6;
   private final static int LOADMORE=7;
   private final static int HIDE=8;
   private LinearLayout footlayout;
   private int footerstate;
   private ProgressBar progressBar;
   private TextView addMore;
   private int lastItemIndex;
   private int totalCount;
   private OnLoadeMoreListener onLoadeMoreListener;
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	public MyListView(Context context,AttributeSet attrs)
	{
		super(context);
		init(context);
		
	}
	private void init(Context context){
		//���������л�ü�����
		inflater=LayoutInflater.from(context);
		headView=(LinearLayout)inflater.inflate(R.layout.listview_head_refresh,null);
		arrowImageView=(ImageView)headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumHeight(50);
		arrowImageView.setMinimumWidth(70);
		
		tipsTextView=(TextView)headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView=(TextView)headView.findViewById(R.id.head_lastUpdatedTextView);
		//����headView�Ŀ�Ⱥ͸߶�
		measureView(headView);
		headContentHeight=headView.getMeasuredHeight();
		headContentWidth=headView.getMeasuredWidth();
		
		headView.setPadding(0, -1*headContentHeight, 0, 0);
		headView.invalidate();
		//��listview��ͷ�����listview��
		addHeaderView(headView,null,false);
		//addHeaderView(headView);
	   
		//���û������¼��ļ�����
		setOnScrollListener(this);
		
		
		//���ü�ͷ�ķ���
		animation=new RotateAnimation(0, -180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);
		
		reverseAnimation=new RotateAnimation(-180, 0,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		
		
		state=DONE;
		isRefreshable=true;
		
		
		//����listview��footer
		footlayout=(LinearLayout)inflater.inflate(R.layout.listview_footer_more,null);
		progressBar=(ProgressBar)footlayout.findViewById(R.id.listview_footer_progressbar);
		addMore=(TextView)footlayout.findViewById(R.id.listview_footer_hint_textview);
		measureView(footlayout);
		footcontentheight=footlayout.getMeasuredHeight();
		footcontentwidth=footlayout.getMeasuredWidth();
		//footlayout.setPadding(0, (-1)*footcontentheight, 0, 0);
		addFooterView(footlayout, null, false);
		footerstate=NORMAL;
		
	}
	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		firstItemIndex=firstVisibleItem;
		lastItemIndex = firstVisibleItem + visibleItemCount - 2;
		totalCount=totalItemCount-2;
		System.out.println(String.valueOf(totalCount)+"   totalcount");
	    System.out.println(String.valueOf(lastItemIndex)+"   lastItemIndex");
	    setBackgroundResource(R.drawable.fragment_bottom_normal);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(isRefreshable)
		{
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if((firstItemIndex==0||lastItemIndex==totalCount)&&!isRecored)
				{
					isRecored=true;
					startY=(int)event.getY();
					
				}
				break;
			case MotionEvent.ACTION_UP:
				if(state!=REFRESHING&&state!=LOADING&&footerstate!=LOADMORE)
				{
					if(state==PULL_TO_REFRESH)
					{
						state=DONE;
						changeHeaderViewByState();
						
					}
					if(state==RELEASE_TO_REFRESH)
					{
						state=REFRESHING;
						changeHeaderViewByState();
						onRefresh();
						
						count=0;
					}
					if(footerstate==NORMAL)
					{
						footerstate=LOADMORE;
						changeFooterViewByState();
						onLoade();
						
					}
				}
				isRecored=false;
				isBack=false;
				break;
			case MotionEvent.ACTION_MOVE:
				int tempY=(int)event.getY();
				if(!isRecored&&(firstItemIndex==0||lastItemIndex==totalCount))
				{
					isRecored=true;
					startY=tempY;
				}
				if(state!=REFRESHING&&isRecored&&state!=LOADING&&footerstate!=LOADMORE)
				{
					if(state==RELEASE_TO_REFRESH)
					{
						setSelection(0);
						if((tempY-startY)/RATIO<headContentHeight&&(tempY-startY)>0)
						{
							state=PULL_TO_REFRESH;
							changeHeaderViewByState();
							
						}else if(tempY-startY<=0){
							state=GONE;
							changeHeaderViewByState();
							
						}else {
							
						}
					}
					if(state==PULL_TO_REFRESH)
					{
						setSelection(0);
						if((tempY-startY)/RATIO>=headContentHeight)
						{
							state=RELEASE_TO_REFRESH;
							isBack=true;
							changeHeaderViewByState();
							
						}else if (tempY-startY<=0) {
							state=DONE;
							changeHeaderViewByState();
							
							
						}
					}
					if(state==DONE)
					{
						if(tempY-startY>0)
						{
							if(tempY-startY>0)
							{
								state=PULL_TO_REFRESH;
								changeHeaderViewByState();
							}
						}
					}
					if(state==PULL_TO_REFRESH)
					{
						headView.setPadding(0, -1*headContentHeight+(tempY-startY)/RATIO, 0, 0);
						
					}
					if(state==RELEASE_TO_REFRESH)
					{
						headView.setPadding(0, (tempY-startY)/RATIO-headContentHeight, 0, 0);
					}
					if(footerstate==HIDE)
					{
						if(startY-tempY>0)
						{
							footerstate=NORMAL;
							
							changeFooterViewByState();
							
						}
					}
					/*if(footerstate==NORMAL)
					{
						
						if((startY-tempY)/RATIO>=footcontentheight)
						{
							state=LOADMORE;
							
							changeFooterViewByState();
							
						}else if (startY-tempY<=0) {
							state=HIDE;
							changeFooterViewByState();
							
							
						}
						
					}*/
					
//					if(footerstate==NORMAL)
//					{
//						setSelection(totalCount+1);
//						footlayout.setPadding(0, -1*footcontentheight+(startY-tempY)/RATIO, 0, 0);
//					}
					
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
			progressBar.setVisibility(View.INVISIBLE);
			addMore.setText("���ظ���");
			break;
		case HIDE:
			footlayout.setPadding(0, (-1)*footcontentheight, 0, 0);
			break;
		case LOADMORE:
			footlayout.setPadding(0, 0, 0, 0);
			addMore.setVisibility(View.INVISIBLE);
			progressBar.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}
	public void changeHeaderViewByState()
	{
		
		switch (state) {
		case RELEASE_TO_REFRESH:
			
			arrowImageView.setVisibility(View.VISIBLE);
			tipsTextView.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			
			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);
			tipsTextView.setText("�ɿ�ˢ��");
					
			break;
		case PULL_TO_REFRESH:
			
			tipsTextView.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if(isBack)
			{
				isBack=false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
				tipsTextView.setText("����ˢ��");
			}
			else {
				tipsTextView.setText("����ˢ��");
				
			}
			break;
		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextView.setText("����ˢ��");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			
			break;
		case DONE:
			headView.setPadding(0, -1*headContentHeight, 0, 0);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.arrow_down);
			tipsTextView.setText("����ˢ��");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		/*
		tipsTextView.setVisibility(View.VISIBLE);
		lastUpdatedTextView.setVisibility(View.VISIBLE);
		arrowImageView.clearAnimation();
		arrowImageView.setVisibility(View.VISIBLE);*/
		
	}
	
	public interface OnLoadeMoreListener{
		public void onLoade();
	}
	public interface OnRefreshListener{
		public void onRefresh();
	}
	public void setOnLoadeMoreListener(OnLoadeMoreListener onLoadeMoreListener)
	{
		this.onLoadeMoreListener=onLoadeMoreListener;
	}
	public void setOnRefreshListener(OnRefreshListener onRefreshListener)
	{
		this.refreshListener=onRefreshListener;
		isRefreshable=true;
	}
	public void onLoadeComplete()
	{
		footerstate=HIDE;
		changeFooterViewByState();
	}
	public void onRefreshComplete()
	{
		state=DONE;
		SimpleDateFormat format=new SimpleDateFormat("yyyy��MM��dd��");
		String date=format.format(new Date());
		lastUpdatedTextView.setText("�������ʱ�䣺"+date);
		changeHeaderViewByState();
	}
	private void onLoade()
	{
		if(onLoadeMoreListener!=null)
		{
			onLoadeMoreListener.onLoade();
		}
	}
	private void onRefresh()
	{
		if(refreshListener!=null)
		{
			refreshListener.onRefresh();
		}
	}
    public void measureView(View child)
    {
    	ViewGroup.LayoutParams p=child.getLayoutParams();
    	if(p==null)
    	{
    		p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    	}
    	int childWidthSpec=ViewGroup.getChildMeasureSpec(0,0+0,p.width);
    	int lpHeight=p.height;
    	int childHeightSpec;
    	if(lpHeight>0)
    	{
    		childHeightSpec =MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
    	}
    	else {
			childHeightSpec=MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
    	child.measure(childWidthSpec, childHeightSpec);
    }
    
    public void setAdapter(BaseAdapter adapter)
    {
    	SimpleDateFormat format=new SimpleDateFormat("yyyy��MM��dd��");
		String date=format.format(new Date());
		lastUpdatedTextView.setText("������£�"+date);
		super.setAdapter(adapter);
    
    }
	
}
