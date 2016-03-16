package com.czh.androidforkftvrelease.life;




import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.map.InitMapApplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateLocation {

	//UI相关
		
		Button mBtnGeoCode = null;	// 将地址编码为坐标
		
		//地图相关
		MapView mMapView = null;	// 地图View
		//搜索相关
		MKSearch mSearch = null;	// 搜索模块
		private LayoutInflater inflater;
		
		EditText editGeoCodeKey;
	private Context context;
	public CreateLocation(Context context)
	{
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	
	public View getView()
	{
		View view=null;
		
		view=inflater.inflate(R.layout.geocoder, null);
		
	 editGeoCodeKey = (EditText)view.findViewById(R.id.geocodekey);
		 //地图初始化
        mMapView = (MapView)view.findViewById(R.id.bmapView);
        
        InitMapApplication app = (InitMapApplication) context
				.getApplicationContext();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(context);
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(InitMapApplication.strKey,
					new InitMapApplication.MyGeneralListener());
		}
        mMapView.getController().enableClick(true);
        mMapView.getController().setZoom(12);
        
        GeoPoint p;
		double cLat = 34.824496;
		double cLon = 114.329077;

		// 设置中心点为开封金明区
		p = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));

		mMapView.getController().setCenter(p);

        // 初始化搜索模块，注册事件监听
        mSearch = new MKSearch();
        mSearch.init(app.mBMapManager, new MKSearchListener() {
            @Override
            public void onGetPoiDetailSearchResult(int type, int error) {
            }
            
			public void onGetAddrResult(MKAddrInfo res, int error) {
				if (error != 0) {
					String str = String.format("错误号：%d", error);
					Toast.makeText(context, str, Toast.LENGTH_LONG).show();
					return;
				}
				//地图移动到该点
				mMapView.getController().animateTo(res.geoPt);	
				if (res.type == MKAddrInfo.MK_GEOCODE){
					//地理编码：通过地址检索坐标点
//					String strInfo = String.format("纬度：%f 经度：%f", res.geoPt.getLatitudeE6()/1e6, res.geoPt.getLongitudeE6()/1e6);
//					Toast.makeText(context, strInfo, Toast.LENGTH_LONG).show();
				}
				
				//生成ItemizedOverlay图层用来标注结果点
				ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(null, mMapView);
				//生成Item
				OverlayItem item = new OverlayItem(res.geoPt, "", null);
				//得到需要标在地图上的资源
				Drawable marker = context.getResources().getDrawable(R.drawable.icon_markf);  
				//为maker定义位置和边界
				marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
				//给item设置marker
				item.setMarker(marker);
				//在图层上添加item
				itemOverlay.addItem(item);
				
				//清除地图其他图层
				mMapView.getOverlays().clear();
				//添加一个标注ItemizedOverlay图层
				mMapView.getOverlays().add(itemOverlay);
				//执行刷新使生效
				mMapView.refresh();
			}
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				
			}
			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
			}
			public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
			}
			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
			}
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {
				// TODO Auto-generated method stub
				
			}

        });
       mBtnGeoCode = (Button)view.findViewById(R.id.geocode);
        
        OnClickListener clickListener = new OnClickListener(){
			public void onClick(View v) {
					SearchButtonProcess(v);
			}
        };
        
      
        mBtnGeoCode.setOnClickListener(clickListener); 
		
		
		return view;
	}
	void SearchButtonProcess(View v) {
		if (mBtnGeoCode.equals(v)) {
		
			//Geo搜索
			mSearch.geocode(editGeoCodeKey.getText().toString(), "开封");
		}
	}
}
