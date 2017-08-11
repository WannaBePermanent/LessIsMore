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
import sechan.intern.lessismore.model.helpers.DBData;
import sechan.intern.lessismore.model.helpers.DBHelper;

public class LimRepo {
    // Singleton Pattern 적용, Factory Static Method
    private static LimRepo instance = null;
    private static final ArrayList<Comp> mPost = new ArrayList<>();
    private ArrayList<LimArticle> mArticles;
    private DBHelper dbHelper;
    private String titleImage = null;
    private String title = null;
    Gson gson = new GsonBuilder().registerTypeAdapter(mPost.getClass(), new CompDeserializer()).create();
    ArrayList<DBData> mDbData;
    // 헬퍼들을 모두 정적 팩토리 메소드를 이용해 생성 getInstance();
    // Repo에 다 연결시켜놓는다.


    public static LimRepo getInstance() {
        if (instance == null) {
            instance = new LimRepo();
        }
        return instance;
    }

    public void setDBHelper(Context context) {
        dbHelper = DBHelper.getInstance(context);
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
            return mPost.size() - 1;
        }
        return 0;

    }

    public int addCompImage(String imagePath) {
        Comp compImage = new CompImage(imagePath);
        if (compImage != null) {
            mPost.add(compImage);
            return mPost.size() - 1;
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

    /*public int addCompMap(NGeoPoint point, String title, String address){
        mPost.add(new CompMap(point,title,address));
        return 0;
    }*/
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
        //int pos = mArticles.size()-position-1;
       //mArticles.remove(position);
        dbHelper.removeArticle(mDbData.get(position).getId());

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
        mDbData = dbHelper.getArticles();
        for (DBData dbItem : mDbData) {
            articles.add(gson.fromJson(dbItem.getJsonData(), LimArticle.class));
            //Collections.reverse(articles);
        }
        mArticles = articles;
        return articles;
    }

    public ArrayList<Comp> getPost() {
        return mPost;
    }

    public Comp getComp(int position) {
        return mPost.get(position);
    }

    public CompText getCompText(int position) {
        return (CompText) mPost.get(position);
    }

}
