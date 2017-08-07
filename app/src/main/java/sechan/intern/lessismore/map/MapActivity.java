package sechan.intern.lessismore.map;

/**
 * Created by Sechan on 2017-08-04.
 */
public class MapActivity {
    //extends} NMapActivity implements NMapView.OnMapStateChangeListener, NMapOverlayManager.OnCalloutOverlayListener {
/*

    Button btnSearch;

    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    NMapOverlayManager mOverlayManager; //지도 위에 표시되는 오버레이 객체를 관리한다.
    NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = null; //POI 아이템의 선택 상태 변경 시 호출되는 콜백 인터페이스를 정의한다.
    NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener; //말풍선 오버레이 객체 생성 시 호출되는 콜백 인터페이스를 정의한다.
    NMapLocationManager mMapLocationManager; //단말기의 현재 위치 탐색 기능을 사용하기 위한 클래스이다.
    //NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener;
    NMapCompassManager mMapCompassManager; //단말기의 나침반 기능을 사용하기 위한 클래스이다.
    NMapMyLocationOverlay mMyLocationOverlay; //지도 위에 현재 위치를 표시하는 오버레이 클래스이며 NMapOverlay 클래스를 상속한다.
    private NMapView mMapView;// 지도 화면 View
    private NMapController mMapController = null;

    private final String CLIENT_ID = "DDDCTG3Meo6SYF6duEr6";// 애플리케이션 클라이언트 아이디 값

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mMapView = new NMapView(this);
        setContentView(R.layout.layout_map_dialog);
        mMapView = (NMapView) findViewById(R.id.mapview);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setBuiltInZoomControls(true, null);
        mMapView.setOnMapStateChangeListener(this);
        mMapController = mMapView.getMapController();
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);

        int markerId = NMapPOIflagType.PIN;
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(126.9924715, 37.5878882, "꼬리표명", markerId, 0);    //요기 좌표 입력해주면, 그 좌표가 표시됩니다.
        poiData.endPOIdata();
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

        // poiDataOverlay.showAllPOIdata(0);
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

    }

    @Override
    public void onMapInitHandler(NMapView mapview, NMapError errorInfo) {
        if (errorInfo == null) { // success
            mMapController.setMapCenter(
                    new NGeoPoint(126.978371, 37.5666091), 11);
        } else { // fail
            android.util.Log.e("NMAP", "onMapInitHandler: error="
                    + errorInfo.toString());
        }
    }

    */
/**
     * 지도 레벨 변경 시 호출되며 변경된 지도 레벨이 파라미터로 전달된다.
     *//*

    @Override
    public void onZoomLevelChange(NMapView mapview, int level) {
    }

    */
/**
     * 지도 중심 변경 시 호출되며 변경된 중심 좌표가 파라미터로 전달된다.
     *//*

    @Override
    public void onMapCenterChange(NMapView mapview, NGeoPoint center) {
    }

    */
/**
     * 지도 애니메이션 상태 변경 시 호출된다.
     * animType : ANIMATION_TYPE_PAN or ANIMATION_TYPE_ZOOM
     * animState : ANIMATION_STATE_STARTED or ANIMATION_STATE_FINISHED
     *//*

    @Override
    public void onAnimationStateChange(
            NMapView arg0, int animType, int animState) {
    }

    @Override
    public void onMapCenterChangeFine(NMapView arg0) {
    }

    @Override
    public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay nMapOverlay, NMapOverlayItem nMapOverlayItem, Rect rect) {
        return new NMapCalloutBasicOverlay(nMapOverlay,nMapOverlayItem,rect);

        //return null;
    }
    public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {

        Toast.makeText(MapActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();

    }
*/

}

