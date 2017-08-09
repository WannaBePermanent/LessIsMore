package sechan.intern.lessismore.lim.components;


import static sechan.intern.lessismore.lim.components.enumcomp.EnumComp.COMP_MAP;


public class CompMap extends Comp {
    //NGeoPoint geoPoint;
    int mapx;
    int mapy;
    String title;
    String address;
    boolean map=false;
/*
    public CompMap(NGeoPoint point, String title, String address) {
        super(COMP_MAP);
        geoPoint = point;
        this.title = title;
        this.address = address;
    }*/
public CompMap(int x, int y, String title, String address) {
    super(COMP_MAP);
    mapx=x;
    mapy=y;
    this.title = title;
    this.address = address;
}
    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

/*    public NGeoPoint getGeoPoint() {

        return geoPoint;
    }*/
public String getPointString(){
    return Integer.toString(mapx) + "," +Integer.toString(mapy);
}
    public void setMap(boolean bool){
        map = bool;
    }
    public boolean getMap(){
        return map;

    }

}
