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
	 * MapView 是地图主控件
	 */
	private MapView mMapView = null;
	/**
	 * 用MapController完成地图控制
	 */
	private MapController mMapController = null;
	/**
	 * MKMapViewListener 用于处理地图事件回调
	 */
	MKMapViewListener mMapListener = null;

	// 路线的起点和终点
	private EditText start;
	private EditText end;
	// 查询按钮和下一题条路线的按钮
	private Button search;
	private Button next;

	// 浏览路线节点相关
	Button mBtnPre = null;// 上一个节点
	Button mBtnNext = null;// 下一个节点

	int nodeIndex = -2;// 节点索引,供浏览节点时使用
	MKRoute route = null;// 保存驾车/步行路线数据的变量，供浏览节点时使用
	TransitOverlay transitOverlay = null;// 保存公交路线图层数据的变量，供浏览节点时使用
	RouteOverlay routeOverlay = null;

	int searchType = -1;// 记录搜索的类型，区分驾车/步行和公交
	private PopupOverlay pop = null;// 弹出泡泡图层，浏览节点时使用
	private TextView popupText = null;// 泡泡view
	private View viewCache = null;

	// 搜索相关
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private Context context;
	// 设置不同查找的索引和总数
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
		traffictype="公交车";
		trafficfirst="时间最少";
		RadioGroup group = (RadioGroup) view.findViewById(R.id.mygroup);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				// 获取变更后的选中项的ID
				int radioButtonId = group.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);

				traffictype = rb.getText().toString();
			}
		});
		RadioGroup group2 = (RadioGroup) view.findViewById(R.id.mygroupfirst);
		group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				// 获取变更后的选中项的ID
				int radioButtonId = group.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);

				trafficfirst = rb.getText().toString();
			}
		});
		

		// 按键点击事件
		OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				// 发起搜索
				
				SearchButtonProcess(traffictype, trafficfirst);
			}

			
		};
		OnClickListener nodeClickListener = new OnClickListener() {
			public void onClick(View v) {
				// 浏览路线节点
				nodeClick(v);
			}
		};
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(traffictype.equals("公交车"))
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
					
				}else if(traffictype.equals("汽车")){
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

		// 创建 弹出泡泡图层
		createPaopao();

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
		mMapController = mMapView.getController();
		/**
		 * 设置地图是否响应点击事件 .
		 */
		mMapController.enableClick(true);
		/**
		 * 设置地图缩放级别
		 */
		mMapController.setZoom(12);

		GeoPoint p;
		double cLat = 34.824496;
		double cLon = 114.329077;

		// 设置中心点为开封金明区
		p = new GeoPoint((int) (cLat * 1E6), (int) (cLon * 1E6));

		mMapController.setCenter(p);

		/**
		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * 在此处理地图移动完成回调 缩放，平移等操作完成后，此回调被触发
				 */
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * 在此处理底图poi点击事件 显示底图poi名称并移动至该点 设置过：
				 * mMapController.enableClick(true); 时，此回调才能被触发
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
				 * 当调用过 mMapView.getCurrentMap()后，此回调会被触发 可在此保存截图至存储设备
				 */
			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 * 地图完成带动画的操作（如: animationTo()）后，此回调被触发
				 */
			}

			/**
			 * 在此处理地图载完成事件
			 */
			@Override
			public void onMapLoadFinish() {
				Toast.makeText(context, "地图加载完成", Toast.LENGTH_SHORT).show();

			}

		};
		mMapView.regMapViewListener(
				InitMapApplication.getInstance().mBMapManager, mMapListener);

		mMapView.regMapTouchListner(new MKMapTouchListener() {

			@Override
			public void onMapClick(GeoPoint arg0) {
				// TODO Auto-generated method stub
				// 在此处理地图点击事件
				// 消隐pop
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

		// 初始化搜索模块，注册事件监听
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
				//起点或终点有歧义，需要选择具体的城市列表或地址列表
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//遍历所有地址
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
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
				//起点或终点有歧义，需要选择具体的城市列表或地址列表
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//遍历所有地址
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
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
				//起点或终点有歧义，需要选择具体的城市列表或地址列表
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//遍历所有地址
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
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
		 //泡泡点击响应回调
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
			//驾车、步行使用的数据结构相同，因此类型为驾车或步行，节点浏览方法相同
			if (nodeIndex < -1 || route == null || nodeIndex >= route.getNumSteps())
				return;
			
			//上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 0){
				//索引减
				nodeIndex--;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
				//弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						route.getStep(nodeIndex).getPoint(),
						5);
			}
			//下一个节点
			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps()-1)){
				//索引加
				nodeIndex++;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
				//弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						route.getStep(nodeIndex).getPoint(),
						5);
			}
		}
		if (searchType == 1){
			//公交换乘使用的数据结构与其他不同，因此单独处理节点浏览
			if (nodeIndex < -1 || transitOverlay == null || nodeIndex >= transitOverlay.getAllItem().size())
				return;
			
			//上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 1){
				//索引减
				nodeIndex--;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
				//弹出泡泡
				popupText.setBackgroundResource(R.drawable.popup);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(),
						5);
			}
			//下一个节点
			if (mBtnNext.equals(v) && nodeIndex < (transitOverlay.getAllItem().size()-2)){
				//索引加
				nodeIndex++;
				//移动到指定索引的坐标
				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
				//弹出泡泡
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
		//重置浏览节点的路线数据
				route = null;
				routeOverlay = null;
				transitOverlay = null; 
				mBtnPre.setVisibility(View.INVISIBLE);
				mBtnNext.setVisibility(View.INVISIBLE);
				// 对起点终点的name进行赋值，也可以直接对坐标赋值，赋值坐标则将根据坐标进行搜索
				MKPlanNode stNode = new MKPlanNode();
				stNode.name = start.getText().toString();
				MKPlanNode enNode = new MKPlanNode();
				enNode.name = end.getText().toString();

				if(trafficfirst2.equals("时间最少"))
				{
					mSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
					
				}
				else if(trafficfirst2.equals("费用最低")){
					mSearch.setDrivingPolicy(MKSearch.ECAR_FEE_FIRST);
					 
				}else {
					mSearch.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
				}
				
				// 实际使用中请对起点终点城市进行正确的设定
				if (traffictype2.equals("汽车")) {
					mSearch.drivingSearch("开封", stNode, "开封", enNode);
				} else if (traffictype2.equals("公交车")) {
					mSearch.transitSearch("开封", stNode, enNode);
				} else if (traffictype2.equals("步行")) {
					mSearch.walkingSearch("开封", stNode, "开封", enNode);
				} 
	}

	public void setbusoverlay(int index)
	{
		searchType = 1;
		transitOverlay = new TransitOverlay((Activity) context, mMapView);
	    // 此处仅展示一个方案作为示例
		transitOverlay.setData(busLineResult.getPlan(index));
	  //清除其他图层
	    mMapView.getOverlays().clear();
	  //添加路线图层
	    mMapView.getOverlays().add(transitOverlay);
	  //执行刷新使生效
	    mMapView.refresh();
	    // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
	    mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());
	  //移动地图到起点
	    mMapView.getController().animateTo(busLineResult.getStart().pt);
	  //重置路线节点索引，节点浏览时使用
	    nodeIndex = 0;
	    mBtnPre.setVisibility(View.VISIBLE);
		mBtnNext.setVisibility(View.VISIBLE);
	}
	
	public void setcaroverlay(int index)
	{
		searchType = 0;
	    routeOverlay = new RouteOverlay((Activity) context, mMapView);
	    // 此处仅展示一个方案作为示例
	    routeOverlay.setData(carLineResult.getPlan(0).getRoute(index));
	    //清除其他图层
	    mMapView.getOverlays().clear();
	    //添加路线图层
	    mMapView.getOverlays().add(routeOverlay);
	    //执行刷新使生效
	    mMapView.refresh();
	    // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
	    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
	    //移动地图到起点
	    mMapView.getController().animateTo(carLineResult.getStart().pt);
	    //将路线数据保存给全局变量
	    route = carLineResult.getPlan(0).getRoute(index);
	    //重置路线节点索引，节点浏览时使用
	    nodeIndex = -1;
	    mBtnPre.setVisibility(View.VISIBLE);
		mBtnNext.setVisibility(View.VISIBLE);
	}
	public void setwalkoverlay(int index)
	{
		searchType = 2;
		routeOverlay = new RouteOverlay((Activity) context, mMapView);
	    // 此处仅展示一个方案作为示例
		routeOverlay.setData(walkLineResult.getPlan(0).getRoute(index));
		//清除其他图层
	    mMapView.getOverlays().clear();
	  //添加路线图层
	    mMapView.getOverlays().add(routeOverlay);
	  //执行刷新使生效
	    mMapView.refresh();
	    // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
	    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
	  //移动地图到起点
	    mMapView.getController().animateTo(walkLineResult.getStart().pt);
	    //将路线数据保存给全局变量
	    route = walkLineResult.getPlan(0).getRoute(index);
	    //重置路线节点索引，节点浏览时使用
	    nodeIndex = -1;
	    mBtnPre.setVisibility(View.VISIBLE);
		mBtnNext.setVisibility(View.VISIBLE);
	}

}
