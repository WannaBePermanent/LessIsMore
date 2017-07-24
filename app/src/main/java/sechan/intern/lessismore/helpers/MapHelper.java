package sechan.intern.lessismore.helpers;


public class MapHelper {
    private static MapHelper instance = null;
    public static MapHelper getInstance() {
        if (instance == null) {
            instance = new MapHelper();
        }
        return instance;
    }
        //NMapView mMapView;
        // NGeoPoint point;
        // String nameTag;

// setNGeoPoint (NGeoPoint){}
// setNameTag (String){}

}
