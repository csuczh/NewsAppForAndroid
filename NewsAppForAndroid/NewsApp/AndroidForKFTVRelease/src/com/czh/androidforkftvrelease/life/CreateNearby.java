package com.czh.androidforkftvrelease.life;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionInfo;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.map.InitMapApplication;
import com.czh.androidforkftvrelease.map.MyPoiOverlay;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNearby {
	private Context context;
	private LayoutInflater inflater;

	private MapView mMapView = null;
	private MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	private int load_Index;

	public CreateNearby(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public View getView() {
		View view = null;
		view = inflater.inflate(R.layout.life_nearby_xml, null);

	    OnClickListener clickListener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchButtonProcess(v);
			}
		};
		Button button=(Button)view.findViewById(R.id.ktv);
		button.setOnClickListener(clickListener);
		((Button)view.findViewById(R.id.film)).setOnClickListener(clickListener);
		((Button)view.findViewById(R.id.rent)).setOnClickListener(clickListener);
		((Button)view.findViewById(R.id.hotel)).setOnClickListener(clickListener);
		((Button)view.findViewById(R.id.workbank)).setOnClickListener(clickListener);
		((Button)view.findViewById(R.id.postbank)).setOnClickListener(clickListener);
		
		
		InitMapApplication app = (InitMapApplication) context
				.getApplicationContext();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(context);
			app.mBMapManager.init(InitMapApplication.strKey,
					new InitMapApplication.MyGeneralListener());
		}

		mMapView = (MapView) view.findViewById(R.id.nearbymapView);
		mMapView.getController().enableClick(true);
        mMapView.getController().setZoom(12);
        GeoPoint p;
		double cLat = 34.824496;
		double cLon = 114.329077;

		// 设置中心点为开封金明区
		p = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));

		mMapView.getController().setCenter(p);
		initMapView();
		// 初始化搜索模块，注册搜索事件监听
		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {
			// 在此处理详情页结果
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
				if (error != 0) {
					Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(context, "成功，查看详情页面", Toast.LENGTH_SHORT)
							.show();
				}
			}

			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetPoiResult(MKPoiResult res, int arg1, int error) {
				// TODO Auto-generated method stub
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_LONG)
							.show();
					return;
				}
				// 将地图移动到第一个POI中心点
				if (res.getCurrentNumPois() > 0) {
					// 将poi结果显示到地图上
					MyPoiOverlay poiOverlay = new MyPoiOverlay((Activity) context,
							mMapView, mSearch);
					poiOverlay.setData(res.getAllPoi());
					mMapView.getOverlays().clear();
					mMapView.getOverlays().add(poiOverlay);
					mMapView.refresh();
					// 当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
					for (MKPoiInfo info : res.getAllPoi()) {
						if (info.pt != null) {
							mMapView.getController().animateTo(info.pt);
							break;
						}
					}
				} else if (res.getCityListNum() > 0) {
					// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
					String strInfo = "在";
					for (int i = 0; i < res.getCityListNum(); i++) {
						strInfo += res.getCityListInfo(i).city;
						strInfo += ",";
					}
					strInfo += "找到结果";
					Toast.makeText(context, strInfo, Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0,
					int arg1) {
				// TODO Auto-generated method stub

			}
		});
		return view;
	}

	private void initMapView() {
		mMapView.setLongClickable(true);
		mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);
	}
	/**
     * 影响搜索按钮点击事件
     * @param v
     */
    public void searchButtonProcess(View v) {
         
         
          mSearch.poiSearchInCity("开封", 
                ( (Button) v).getText().toString());
    }
    public void goToNextPage(View v) {
        //搜索下一组poi
        int flag = mSearch.goToPoiPage(++load_Index);
        if (flag != 0) {
            Toast.makeText(context, "先搜索开始，然后再搜索下一组数据", Toast.LENGTH_SHORT).show();
        }
    }
}
