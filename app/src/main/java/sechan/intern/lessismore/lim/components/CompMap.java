package sechan.intern.lessismore.lim.components;


import com.nhn.android.maps.maplib.NGeoPoint;

import static sechan.intern.lessismore.lim.components.Enum.EnumComp.COMP_MAP;


public class CompMap extends Comp {
    NGeoPoint geoPoint;
    String title;
    String address;

    public CompMap(NGeoPoint point, String title, String address) {
        super(COMP_MAP);
        geoPoint = point;
        this.title = title;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public NGeoPoint getGeoPoint() {

        return geoPoint;
    }

}
