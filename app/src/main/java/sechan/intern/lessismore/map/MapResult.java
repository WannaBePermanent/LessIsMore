package sechan.intern.lessismore.map;

/**
 * Created by Sechan on 2017-08-07.
 */

public class MapResult {
    MapResult2 result;

    class MapResult2 {
        int total;
        String userquery;
        MapItem[] items;

    }

    class MapItem {
        String address;


        boolean isRoadAddress;
        boolean isAdmAddress;
        AddrDetail addrdetail;
        Point point;

        class AddrDetail {
            String country;
            String sido;
            String sigugun;
            String dongmyun;
            String rest;
        }

        class Point {
            String x;
            String y;

        }
    }
}

