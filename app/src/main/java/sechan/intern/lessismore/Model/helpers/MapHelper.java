package sechan.intern.lessismore.Model.helpers;


import android.content.Context;
import android.view.View;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import sechan.intern.lessismore.lim.components.CompMap;
import sechan.intern.lessismore.map.Geo.GeoTrans;
import sechan.intern.lessismore.map.Geo.GeoTransPoint;
import sechan.intern.lessismore.map.NaverMap.NMapPOIflagType;
import sechan.intern.lessismore.map.NaverMap.NMapViewerResourceProvider;

public class MapHelper {


    private static final String CLIENT_ID = "DDDCTG3Meo6SYF6duEr6";

    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    NMapOverlayManager mOverlayManager; //지도 위에 표시되는 오버레이 객체를 관리한다.
    NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = null; //POI 아이템의 선택 상태 변경 시 호출되는 콜백 인터페이스를 정의한다.
    NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener; //말풍선 오버레이 객체 생성 시 호출되는 콜백 인터페이스를 정의한다.
    private NMapController mMapController = null;

    private static MapHelper instance = null;

    public static MapHelper getInstance() {
        if (instance == null) {
            instance = new MapHelper();
        }
        return instance;
    }

    public void setMapView(NMapView mapview, CompMap comp) {
        NMapView mapView;
        NMapContext mMapContext;
        CompMap compMap;
        Context context;
        compMap = comp;
        mapView = mapview;
        context = ((View) mapView.getParent()).getContext();
        mMapContext = new NMapContext(context);
        mMapContext.onCreate();
        mapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapContext.setupMapView(mapView);
        mMapContext.onStart();
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestFocus();
        mapView.setBuiltInZoomControls(true, null);
        mMapViewerResourceProvider = new NMapViewerResourceProvider(context);
        mOverlayManager = new NMapOverlayManager(context, mapView, mMapViewerResourceProvider);
        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
        int markerId = NMapPOIflagType.PIN;
        NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(compMap.getGeoPoint(), compMap.getTitle(), markerId, 0);
        mMapController.setMapCenter(compMap.getGeoPoint(), 13);
        poiData.endPOIdata();
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
    }



    public NGeoPoint convert(int mapx, int mapy) {
        GeoTransPoint point = new GeoTransPoint(mapx, mapy);
        GeoTransPoint point2 = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, point);
        return new NGeoPoint(point2.getX(), point2.getY());
    }
}