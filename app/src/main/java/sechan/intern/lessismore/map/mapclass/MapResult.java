package sechan.intern.lessismore.map.mapclass;

import java.util.ArrayList;

/**
 * Created by Sechan on 2017-08-07.
 */

public class MapResult {

    // 규칙 14 접근자 메서드 적용
    int total;
    int start;
    int display;
    ArrayList<MapItem> items;

    public int getTotal() {
        return total;
    }

    public int getStart() {
        return start;
    }

    public int getDisplay() {
        return display;
    }

    public ArrayList<MapItem> getItems() {
        return items;
    }
}
