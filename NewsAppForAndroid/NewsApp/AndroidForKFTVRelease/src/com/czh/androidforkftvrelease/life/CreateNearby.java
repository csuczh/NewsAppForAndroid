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
	private MKSearch mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��

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

		// �������ĵ�Ϊ���������
		p = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));

		mMapView.getController().setCenter(p);
		initMapView();
		// ��ʼ������ģ�飬ע�������¼�����
		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {
			// �ڴ˴�������ҳ���
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
				if (error != 0) {
					Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(context, "�ɹ����鿴����ҳ��", Toast.LENGTH_SHORT)
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
				// ����ſɲο�MKEvent�еĶ���
				if (error != 0 || res == null) {
					Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_LONG)
							.show();
					return;
				}
				// ����ͼ�ƶ�����һ��POI���ĵ�
				if (res.getCurrentNumPois() > 0) {
					// ��poi�����ʾ����ͼ��
					MyPoiOverlay poiOverlay = new MyPoiOverlay((Activity) context,
							mMapView, mSearch);
					poiOverlay.setData(res.getAllPoi());
					mMapView.getOverlays().clear();
					mMapView.getOverlays().add(poiOverlay);
					mMapView.refresh();
					// ��ePoiTypeΪ2��������·����4��������·��ʱ�� poi����Ϊ��
					for (MKPoiInfo info : res.getAllPoi()) {
						if (info.pt != null) {
							mMapView.getController().animateTo(info.pt);
							break;
						}
					}
				} else if (res.getCityListNum() > 0) {
					// ������ؼ����ڱ���û���ҵ����������������ҵ�ʱ�����ذ����ùؼ�����Ϣ�ĳ����б�
					String strInfo = "��";
					for (int i = 0; i < res.getCityListNum(); i++) {
						strInfo += res.getCityListInfo(i).city;
						strInfo += ",";
					}
					strInfo += "�ҵ����";
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
     * Ӱ��������ť����¼�
     * @param v
     */
    public void searchButtonProcess(View v) {
         
         
          mSearch.poiSearchInCity("����", 
                ( (Button) v).getText().toString());
    }
    public void goToNextPage(View v) {
        //������һ��poi
        int flag = mSearch.goToPoiPage(++load_Index);
        if (flag != 0) {
            Toast.makeText(context, "��������ʼ��Ȼ����������һ������", Toast.LENGTH_SHORT).show();
        }
    }
}
