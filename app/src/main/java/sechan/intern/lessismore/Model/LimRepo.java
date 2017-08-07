package sechan.intern.lessismore.Model;


import java.util.ArrayList;

import sechan.intern.lessismore.lim.components.Comp;
import sechan.intern.lessismore.lim.components.CompImage;
import sechan.intern.lessismore.lim.components.CompText;
import sechan.intern.lessismore.Model.helpers.DBHelper;
import sechan.intern.lessismore.Model.helpers.MapHelper;

public class LimRepo {
    // Singleton Pattern 적용, Factory Static Method
    private static LimRepo instance = null;
    private static final ArrayList<Comp> mPost = new ArrayList<>();
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
        if (mPost != null) return true;
        return false;
    }

    public int addCompText() {
        Comp compText = new CompText();
        if (compText != null) {
            mPost.add(compText);
            return mPost.size()-1;
        }
        return 0;

    }

    public int addCompImage(String imagePath) {
        Comp compImage = new CompImage(imagePath);
        if (compImage != null) {
            mPost.add(compImage);
            return mPost.size()-1;
        }
        return 0;
    }
    public int addCompImage(String imagePath, int position) {
        Comp compImage = new CompImage(imagePath);
        if (compImage != null) {
            mPost.add(position, compImage);
            return position;
        }
        return 0;
    }


    public int savePostInstance(){

        return 0;
    }

/*    public void saveTextStyle(int position, int type, int start, int end, int attr){
        ((CompText) mPost.get(position)).saveTextStyle(type,start,end,attr);

    }
    public void saveTextStyle(int position, int type, int start, int end){
        ((CompText) mPost.get(position)).saveTextStyle(type,start,end);
    }
    public void saveText(int position, String str){
        ((CompText) mPost.get(position)).saveText(str);
    }
    //mPresenter와 역할이 겹치는 것은 mPresenter에서 Repo를 불러와서 처리하는 식으로 바꾸기
    */


    public ArrayList<Comp> getPost() {
        return mPost;
    }

    public Comp getComp(int position){
        return mPost.get(position);
    }
    public CompText getCompText(int position){
        return (CompText) mPost.get(position);
    }

}
