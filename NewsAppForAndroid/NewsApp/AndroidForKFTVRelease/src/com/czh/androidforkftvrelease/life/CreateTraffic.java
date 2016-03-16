package com.czh.androidforkftvrelease.life;

import java.util.zip.Inflater;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.map.BMapUtil;
import com.czh.androidforkftvrelease.map.InitMapApplication;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTraffic {
	private LayoutInflater inflater;
	private String traffictype;
	private String trafficfirst;

	/**
	 * MapView �ǵ�ͼ���ؼ�
	 */
	private MapView mMapView = null;
	/**
	 * ��MapController��ɵ�ͼ����
	 */
	private MapController mMapController = null;
	/**
	 * MKMapViewListener ���ڴ����ͼ�¼��ص�
	 */
	MKMapViewListener mMapListener = null;

	// ·�ߵ������յ�
	private EditText start;
	private EditText end;
	// ��ѯ��ť����һ����·�ߵİ�ť
	private Button search;
	private Button next;

	// ���·�߽ڵ����
	Button mBtnPre = null;// ��һ���ڵ�
	Button mBtnNext = null;// ��һ���ڵ�

	int nodeIndex = -2;// �ڵ�����,������ڵ�ʱʹ��
	MKRoute route = null;// ����ݳ�/����·�����ݵı�����������ڵ�ʱʹ��
	TransitOverlay transitOverlay = null;// ���湫��·��ͼ�����ݵı�����������ڵ�ʱʹ��
	RouteOverlay routeOverlay = null;

	int searchType = -1;// ��¼���������ͣ����ּݳ�/���к͹���
	private PopupOverlay pop = null;// ��������ͼ�㣬����ڵ�ʱʹ��
	private TextView popupText = null;// ����view
	private View viewCache = null;

	// �������
	MKSearch mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	private Context context;
	// ���ò�ͬ���ҵ�����������
	private int busindex;
	private int buscount;
	private int carindex;
	private int carcount;
	private int walkindex;
	private int walkcount;
	
	MKTransitRouteResult busLineResult;
	MKDrivingRouteResult carLineResult;
	MKWalkingRouteResult walkLineResult;

	public CreateTraffic(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public View GetView() {
		
		final View view = inflater.inflate(R.layout.life_route_xml, null);

		busindex = 0;
		buscount = 0;
		carindex = 0;
		carcount = 0;
		walkindex = 0;
		walkcount = 0;
		mBtnPre = (Button) view.findViewById(R.id.pre);
		mBtnNext = (Button) view.findViewById(R.id.next);

		start = (EditText) view.findViewById(R.id.start);
		end = (EditText) view.findViewById(R.id.end);

		search = (Button) view.findViewById(R.id.search);
		next = (Button) view.findViewById(R.id.nextroute);
		traffictype="������";
		trafficfirst="ʱ������";
		RadioGroup group = (RadioGroup) view.findViewById(R.id.mygroup);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				// ��ȡ������ѡ�����ID
				int radioButtonId = group.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);

				traffictype = rb.getText().toString();
			}
		});
		RadioGroup group2 = (RadioGroup) view.findViewById(R.id.mygroupfirst);
		group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				// ��ȡ������ѡ�����ID
				int radioButtonId = group.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);

				trafficfirst = rb.getText().toString();
			}
		});
		

		// ��������¼�
		OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				// ��������
				
				SearchButtonProcess(traffictype, trafficfirst);
			}

			
		};
		OnClickListener nodeClickListener = new OnClickListener() {
			public void onClick(View v) {
				// ���·�߽ڵ�
				nodeClick(v);
			}
		};
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(traffictype.equals("������"))
				{
					busindex++;
					if(busindex==buscount)
					{
						busindex=0;
					}
					if(busLineResult!=null)
					{
						setbusoverlay(busindex);
					}
					
				}else if(traffictype.equals("����")){
					carindex++;
					if(carindex==carcount)
					{
						carindex=0;
					}
					if(carLineResult!=null)
					{
						setcaroverlay(carindex);
					}
					
				}else {
					walkindex++;
					if(walkindex==walkcount)
					{
						walkindex=0;
					}
					if(walkLineResult!=null)
					{
						setwalkoverlay(walkindex);
					}
				}
				
			}
		});
		mMapView = (MapView) view.findViewById(R.id.bmapView);

		search.setOnClickListener(clickListener);
		mBtnPre.setOnClickListener(nodeClickListener);
		mBtnNext.setOnClickListener(nodeClickListener);

		// ���� ��������ͼ��
		createPaopao();

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
		mMapController = mMapView.getController();
		/**
		 * ���õ�ͼ�Ƿ���Ӧ����¼� .
		 */
		mMapController.enableClick(true);
		/**
		 * ���õ�ͼ���ż���
		 */
		mMapController.setZoom(12);

		GeoPoint p;
		double cLat = 34.824496;
		double cLon = 114.329077;

		// �������ĵ�Ϊ���������
		p = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));

		mMapController.setCenter(p);

		/**
		 * MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
		 */
		mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * �ڴ˴����ͼ�ƶ���ɻص� ���ţ�ƽ�ƵȲ�����ɺ󣬴˻ص�������
				 */
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * �ڴ˴����ͼpoi����¼� ��ʾ��ͼpoi���Ʋ��ƶ����õ� ���ù���
				 * mMapController.enableClick(true); ʱ���˻ص����ܱ�����
				 * 
				 */
				String title = "";
				if (mapPoiInfo != null) {
					title = mapPoiInfo.strText;
					Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
					mMapController.animateTo(mapPoiInfo.geoPt);
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 * �����ù� mMapView.getCurrentMap()�󣬴˻ص��ᱻ���� ���ڴ˱����ͼ���洢�豸
				 */
			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 * ��ͼ��ɴ������Ĳ�������: animationTo()���󣬴˻ص�������
				 */
			}

			/**
			 * �ڴ˴����ͼ������¼�
			 */
			@Override
			public void onMapLoadFinish() {
				Toast.makeText(context, "��ͼ�������", Toast.LENGTH_SHORT).show();

			}

		};
		mMapView.regMapViewListener(
				InitMapApplication.getInstance().mBMapManager, mMapListener);

		mMapView.regMapTouchListner(new MKMapTouchListener() {

			@Override
			public void onMapClick(GeoPoint arg0) {
				// TODO Auto-generated method stub
				// �ڴ˴����ͼ����¼�
				// ����pop
				if (pop != null) {
					pop.hidePop();
				}
			}

			@Override
			public void onMapDoubleClick(GeoPoint arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMapLongClick(GeoPoint arg0) {
				// TODO Auto-generated method stub

			}
		});

		// ��ʼ������ģ�飬ע���¼�����
		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {

			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetBusDetailResult(MKBusLineResult res, int error) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				// TODO Auto-generated method stub
				//�����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//�������е�ַ
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				// ����ſɲο�MKEvent�еĶ���
				if (error != 0 || res == null) {
					Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
			
				searchType = 0;
				
				carLineResult=res;
				busLineResult=null;
				walkLineResult=null;
				
				carcount=res.getPlan(0).getNumRoutes();
				carindex=0;
				setcaroverlay(carindex);

			}

			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			
			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
				// TODO Auto-generated method stub
				//�����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//�������е�ַ
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}

				searchType = 2;
				
				walkLineResult=res;
				busLineResult=null;
				carLineResult=null;
				
				walkcount=res.getPlan(0).getNumRoutes();
				walkindex=0;
				setwalkoverlay(walkindex);
			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
				// TODO Auto-generated method stub
				//�����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//�������е�ַ
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
				
				searchType = 1;
				busLineResult=res;
				carLineResult=null;
				walkLineResult=null;
				
				buscount=res.getNumPlan();
				busindex=0;
				
				setbusoverlay(busindex);
				
			}
		});
		return view;
	}
	
	private void createPaopao() {
		// TODO Auto-generated method stub
		 //���ݵ����Ӧ�ص�
        PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
        };
        pop = new PopupOverlay(mMapView,popListener);
		
	}

	protected void nodeClick(View v) {
		// TODO Auto-generated method stub
		viewCache = inflater.inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
		if (searchType == 0 || searchType == 2){
			//�ݳ�������ʹ�õ����ݽṹ��ͬ���������Ϊ�ݳ����У��ڵ����������ͬ
			if (nodeIndex < -1 || route == null || nodeIndex >= route.getNumSteps())
				return;
			
			//��һ���ڵ�
			if (mBtnPre.equals(v) && nodeIndex > 0){
				//������
				nodeIndex--;
				//�ƶ���ָ������������
				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
				//��������
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						route.getStep(nodeIndex).getPoint(),
						5);
			}
			//��һ���ڵ�
			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps()-1)){
				//������
				nodeIndex++;
				//�ƶ���ָ������������
				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
				//��������
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						route.getStep(nodeIndex).getPoint(),
						5);
			}
		}
		if (searchType == 1){
			//��������ʹ�õ����ݽṹ��������ͬ����˵�������ڵ����
			if (nodeIndex < -1 || transitOverlay == null || nodeIndex >= transitOverlay.getAllItem().size())
				return;
			
			//��һ���ڵ�
			if (mBtnPre.equals(v) && nodeIndex > 1){
				//������
				nodeIndex--;
				//�ƶ���ָ������������
				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
				//��������
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(),
						5);
			}
			//��һ���ڵ�
			if (mBtnNext.equals(v) && nodeIndex < (transitOverlay.getAllItem().size()-2)){
				//������
				nodeIndex++;
				//�ƶ���ָ������������
				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
				//��������
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(),
						5);
			}
		}
	}

	protected void SearchButtonProcess(String traffictype2, String trafficfirst2) {
		// TODO Auto-generated method stub
		//��������ڵ��·������
				route = null;
				routeOverlay = null;
				transitOverlay = null; 
				mBtnPre.setVisibility(View.INVISIBLE);
				mBtnNext.setVisibility(View.INVISIBLE);
				// ������յ��name���и�ֵ��Ҳ����ֱ�Ӷ����긳ֵ����ֵ�����򽫸��������������
				MKPlanNode stNode = new MKPlanNode();
				stNode.name = start.getText().toString();
				MKPlanNode enNode = new MKPlanNode();
				enNode.name = end.getText().toString();

				if(trafficfirst2.equals("ʱ������"))
				{
					mSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
					
				}
				else if(trafficfirst2.equals("�������")){
					mSearch.setDrivingPolicy(MKSearch.ECAR_FEE_FIRST);
					 
				}else {
					mSearch.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
				}
				
				// ʵ��ʹ�����������յ���н�����ȷ���趨
				if (traffictype2.equals("����")) {
					mSearch.drivingSearch("����", stNode, "����", enNode);
				} else if (traffictype2.equals("������")) {
					mSearch.transitSearch("����", stNode, enNode);
				} else if (traffictype2.equals("����")) {
					mSearch.walkingSearch("����", stNode, "����", enNode);
				} 
	}

	public void setbusoverlay(int index)
	{
		searchType = 1;
		transitOverlay = new TransitOverlay((Activity) context, mMapView);
	    // �˴���չʾһ��������Ϊʾ��
		transitOverlay.setData(busLineResult.getPlan(index));
	  //�������ͼ��
	    mMapView.getOverlays().clear();
	  //���·��ͼ��
	    mMapView.getOverlays().add(transitOverlay);
	  //ִ��ˢ��ʹ��Ч
	    mMapView.refresh();
	    // ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
	    mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());
	  //�ƶ���ͼ�����
	    mMapView.getController().animateTo(busLineResult.getStart().pt);
	  //����·�߽ڵ��������ڵ����ʱʹ��
	    nodeIndex = 0;
	    mBtnPre.setVisibility(View.VISIBLE);
		mBtnNext.setVisibility(View.VISIBLE);
	}
	
	public void setcaroverlay(int index)
	{
		searchType = 0;
	    routeOverlay = new RouteOverlay((Activity) context, mMapView);
	    // �˴���չʾһ��������Ϊʾ��
	    routeOverlay.setData(carLineResult.getPlan(0).getRoute(index));
	    //�������ͼ��
	    mMapView.getOverlays().clear();
	    //���·��ͼ��
	    mMapView.getOverlays().add(routeOverlay);
	    //ִ��ˢ��ʹ��Ч
	    mMapView.refresh();
	    // ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
	    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
	    //�ƶ���ͼ�����
	    mMapView.getController().animateTo(carLineResult.getStart().pt);
	    //��·�����ݱ����ȫ�ֱ���
	    route = carLineResult.getPlan(0).getRoute(index);
	    //����·�߽ڵ��������ڵ����ʱʹ��
	    nodeIndex = -1;
	    mBtnPre.setVisibility(View.VISIBLE);
		mBtnNext.setVisibility(View.VISIBLE);
	}
	public void setwalkoverlay(int index)
	{
		searchType = 2;
		routeOverlay = new RouteOverlay((Activity) context, mMapView);
	    // �˴���չʾһ��������Ϊʾ��
		routeOverlay.setData(walkLineResult.getPlan(0).getRoute(index));
		//�������ͼ��
	    mMapView.getOverlays().clear();
	  //���·��ͼ��
	    mMapView.getOverlays().add(routeOverlay);
	  //ִ��ˢ��ʹ��Ч
	    mMapView.refresh();
	    // ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
	    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
	  //�ƶ���ͼ�����
	    mMapView.getController().animateTo(walkLineResult.getStart().pt);
	    //��·�����ݱ����ȫ�ֱ���
	    route = walkLineResult.getPlan(0).getRoute(index);
	    //����·�߽ڵ��������ڵ����ʱʹ��
	    nodeIndex = -1;
	    mBtnPre.setVisibility(View.VISIBLE);
		mBtnNext.setVisibility(View.VISIBLE);
	}

}
