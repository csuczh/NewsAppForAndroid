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

	//UI���
		
		Button mBtnGeoCode = null;	// ����ַ����Ϊ����
		
		//��ͼ���
		MapView mMapView = null;	// ��ͼView
		//�������
		MKSearch mSearch = null;	// ����ģ��
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
		 //��ͼ��ʼ��
        mMapView = (MapView)view.findViewById(R.id.bmapView);
        
        InitMapApplication app = (InitMapApplication) context
				.getApplicationContext();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(context);
			/**
			 * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
			 */
			app.mBMapManager.init(InitMapApplication.strKey,
					new InitMapApplication.MyGeneralListener());
		}
        mMapView.getController().enableClick(true);
        mMapView.getController().setZoom(12);
        
        GeoPoint p;
		double cLat = 34.824496;
		double cLon = 114.329077;

		// �������ĵ�Ϊ���������
		p = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));

		mMapView.getController().setCenter(p);

        // ��ʼ������ģ�飬ע���¼�����
        mSearch = new MKSearch();
        mSearch.init(app.mBMapManager, new MKSearchListener() {
            @Override
            public void onGetPoiDetailSearchResult(int type, int error) {
            }
            
			public void onGetAddrResult(MKAddrInfo res, int error) {
				if (error != 0) {
					String str = String.format("����ţ�%d", error);
					Toast.makeText(context, str, Toast.LENGTH_LONG).show();
					return;
				}
				//��ͼ�ƶ����õ�
				mMapView.getController().animateTo(res.geoPt);	
				if (res.type == MKAddrInfo.MK_GEOCODE){
					//������룺ͨ����ַ���������
//					String strInfo = String.format("γ�ȣ�%f ���ȣ�%f", res.geoPt.getLatitudeE6()/1e6, res.geoPt.getLongitudeE6()/1e6);
//					Toast.makeText(context, strInfo, Toast.LENGTH_LONG).show();
				}
				
				//����ItemizedOverlayͼ��������ע�����
				ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(null, mMapView);
				//����Item
				OverlayItem item = new OverlayItem(res.geoPt, "", null);
				//�õ���Ҫ���ڵ�ͼ�ϵ���Դ
				Drawable marker = context.getResources().getDrawable(R.drawable.icon_markf);  
				//Ϊmaker����λ�úͱ߽�
				marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
				//��item����marker
				item.setMarker(marker);
				//��ͼ�������item
				itemOverlay.addItem(item);
				
				//�����ͼ����ͼ��
				mMapView.getOverlays().clear();
				//���һ����עItemizedOverlayͼ��
				mMapView.getOverlays().add(itemOverlay);
				//ִ��ˢ��ʹ��Ч
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
		
			//Geo����
			mSearch.geocode(editGeoCodeKey.getText().toString(), "����");
		}
	}
}
