package sechan.intern.lessismore;


import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.helpers.DBHelper;
import sechan.intern.lessismore.helpers.ImageHelper;
import sechan.intern.lessismore.helpers.MapHelper;
import sechan.intern.lessismore.helpers.Post;

public class LimRepo {
    // Singleton Pattern 적용, Factory Static Method
    private static LimRepo instance = null;
    //private static final ArrayList<Comp> compOrder = new ArrayList<>();
    private static final Post LimPost = new Post();
    //private static final CompHelper compHelper = CompHelper.getInstance();
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
        if (LimPost != null) return true;
        return false;
    }

    public int addCompText() {
        Comp c = new CompText();
        if (c != null) {
            LimPost.add(c);
            return LimPost.size()-1;
        }
        return 0;

    }

    public int addCompImage(String imagePath) {
        Comp c = new CompImage(imagePath);
        if (c != null) {
            LimPost.add(c);
            return LimPost.size()-1;
        }
        return 0;
        //CompHelper을 쓸 필요가 있는지 생각해봐야함 - 일단 안쓰고함
    }

    public int savePostInstance(){

        return 0;
    }

    public Post getPost() {
        return LimPost;
    }
}
