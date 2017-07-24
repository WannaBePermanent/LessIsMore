package sechan.intern.lessismore;


import java.util.ArrayList;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.helpers.CompHelper;
import sechan.intern.lessismore.helpers.DBHelper;
import sechan.intern.lessismore.helpers.ImageHelper;
import sechan.intern.lessismore.helpers.MapHelper;

public class LimRepo {
    // Singleton Pattern 적용, Factory Static Method
    private static LimRepo instance = null;
    private static final ArrayList<Comp> compOrder = new ArrayList<>();
    private static final CompHelper compHelper = CompHelper.getInstance();
    private static final ImageHelper imageHelper = ImageHelper.getInstance();
    private static final MapHelper mapHelper = MapHelper.getInstance();
    private static final DBHelper dbHelper = DBHelper.getInstance();
    // 헬퍼들을 모두 정적 팩토리 메소드를 이용해 생성 getInstance();
    // Repo에 다 연결시켜놓는다.


    public static LimRepo getInstance() {
        if (instance == null) {
            instance = new LimRepo();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
    public boolean helperLoaded() {
        if (compHelper != null) return true;
        return false;
    }
    public int addCompText(){
        Comp c = compHelper.newCompText();
        if (c != null) {
            compOrder.add(c);
            return 1;
        }
        return 0;

    }
}
