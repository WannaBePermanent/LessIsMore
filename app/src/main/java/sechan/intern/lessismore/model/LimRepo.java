package sechan.intern.lessismore.model;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sechan.intern.lessismore.lim.components.Comp;
import sechan.intern.lessismore.lim.components.CompDeserializer;
import sechan.intern.lessismore.lim.components.CompImage;
import sechan.intern.lessismore.lim.components.CompMap;
import sechan.intern.lessismore.lim.components.CompText;
import sechan.intern.lessismore.model.helpers.DBHelper;
import sechan.intern.lessismore.model.helpers.LimDBArticle;

public class LimRepo {
    // Singleton Pattern 적용, Factory Static Method
    private static LimRepo instance = null;
    private static final ArrayList<Comp> mPost = new ArrayList<>(); // 규칙3. 정적 싱글턴 객체 생성
    private ArrayList<LimArticle> mArticles;
    private DBHelper dbHelper;
    private String titleImage = null;
    private String title = null;
    Gson gson = new GsonBuilder().registerTypeAdapter(mPost.getClass(), new CompDeserializer()).create();
    ArrayList<LimDBArticle> mLimDbArticle;
    // 헬퍼들을 모두 정적 팩토리 메소드를 이용해 생성 getInstance();
    // Repo에 다 연결시켜놓는다.


    public static LimRepo getInstance() { // 규칙1, 규칙3 정적팩터리를 사용한 싱글턴 패턴
        if (instance == null) {
            instance = new LimRepo();
        }
        return instance;
    }

    public void setDBHelper(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }



    public int addCompText() {
            mPost.add(new CompText());
            return mPost.size() - 1;
    }

    public int addCompImage(String imagePath) {
            mPost.add(new CompImage(imagePath));
            return mPost.size() - 1;

    }

    public int addCompImage(String imagePath, int position) {
            mPost.add(position, new CompImage(imagePath));
            return position;

    }

    public int addCompMap(int mapx, int mapy, String title, String address) {
        mPost.add(new CompMap(mapx, mapy, title, address));
        return 0;
    }

    public void setTitleImage(String imagePath) {
        titleImage = imagePath;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public String getTitleText() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int saveArticle() {
        DateFormat df = new SimpleDateFormat("yyyy'년' MM'월' dd'일' HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        LimArticle article = new LimArticle(title, titleImage, mPost, date);
        String jsonArticle = gson.toJson(article);
        dbHelper.insertArticle(jsonArticle);



        return 0;

    }

    public void removeArticle(int position) {
        dbHelper.removeArticle(mLimDbArticle.get(position).getId());

    }

    public void loadArticle(int position) {
        LimArticle article = mArticles.get(position);
        mPost.clear();
        mPost.addAll(article.getPost());
        title = article.getTitle();
        titleImage = article.getTitleImage();


    }

    public ArrayList<LimArticle> loadArticleList() {
        ArrayList<LimArticle> articles = new ArrayList<>();
        mLimDbArticle = dbHelper.getArticles();
        for (LimDBArticle dbItem : mLimDbArticle) {
            articles.add(gson.fromJson(dbItem.getJsonData(), LimArticle.class));
        }
        mArticles = articles;
        return articles;
    }

    public ArrayList<Comp> getPost() {
        return mPost;
        // 규칙 13. 멤버 접근 권한 최소화, 외부에서 직접 수정 불가.
    }

    public CompText getCompText(int position) {
        return (CompText) mPost.get(position);

    }
    public void removeComp(int position){
        mPost.remove(position);
    }

}
