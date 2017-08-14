package sechan.intern.lessismore.lim.components;


import static sechan.intern.lessismore.lim.components.enumcomp.EnumComp.COMP_MAP;


public class CompMap extends Comp {
    // 규칙 14 접근자 메서드 적용
    int mapx;
    int mapy;
    String title;
    String address;

    public CompMap(int x, int y, String title, String address) {
        super(COMP_MAP);
        mapx = x;
        mapy = y;
        this.title = title;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }


    public String getPointString() {
        return Integer.toString(mapx) + "," + Integer.toString(mapy);
    }


}
