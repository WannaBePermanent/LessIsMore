package sechan.intern.lessismore.Lim;

import android.os.Bundle;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;

import sechan.intern.lessismore.R;

/**
 * Created by Sechan on 2017-08-04.
 */
public class MapActivity extends NMapActivity implements NMapView.OnMapStateChangeListener

        {
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


        //LinearLayout MapLayout = (LinearLayout) findViewById(R.id.MapLayout);
        //MapLayout.addView(mMapView);
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

    /**
     * 지도 레벨 변경 시 호출되며 변경된 지도 레벨이 파라미터로 전달된다.
     */
    @Override
    public void onZoomLevelChange(NMapView mapview, int level) {}

    /**
     * 지도 중심 변경 시 호출되며 변경된 중심 좌표가 파라미터로 전달된다.
     */
    @Override
    public void onMapCenterChange(NMapView mapview, NGeoPoint center) {}

    /**
     * 지도 애니메이션 상태 변경 시 호출된다.
     * animType : ANIMATION_TYPE_PAN or ANIMATION_TYPE_ZOOM
     * animState : ANIMATION_STATE_STARTED or ANIMATION_STATE_FINISHED
     */
    @Override
    public void onAnimationStateChange(
            NMapView arg0, int animType, int animState) {}

    @Override
    public void onMapCenterChangeFine(NMapView arg0) {}
}

