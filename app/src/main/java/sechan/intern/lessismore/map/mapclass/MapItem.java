package sechan.intern.lessismore.map.mapclass;

/**
 * Created by Sechan on 2017-08-07.
 */

public class MapItem {

    // 규칙 14 접근자 메서드 적용


    String title;
    String link;
    String category;
    String description;
    String telephone;
    String address;
    String roadAddress;
    int mapx;
    int mapy;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public int getMapx() {
        return mapx;
    }

    public int getMapy() {
        return mapy;
    }
}

