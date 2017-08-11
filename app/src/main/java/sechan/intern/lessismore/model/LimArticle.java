package sechan.intern.lessismore.model;

import java.util.ArrayList;

import sechan.intern.lessismore.lim.components.Comp;

/**
 * Created by Sechan on 2017-08-10.
 */

public class LimArticle {
    int id;
    String title;
    String date;
    String titleImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public String getTitleImage() {
        return titleImage;
    }



    public ArrayList<Comp> getPost() {
        return post;
    }


    ArrayList<Comp> post;
    public LimArticle(String title, String titleimg,ArrayList<Comp> post,String date){
        this.title=title;
        this.titleImage=titleimg;
        this.post=post;
        this.date=date;

    }

}
