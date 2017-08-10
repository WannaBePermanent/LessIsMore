package sechan.intern.lessismore.model;

import java.util.ArrayList;

import sechan.intern.lessismore.lim.components.Comp;

/**
 * Created by Sechan on 2017-08-10.
 */

public class LimArticle {
    String title;
    String date;
    String titleImage;
    ArrayList<Comp> post;
    public LimArticle(String title, String titleimg,ArrayList<Comp> post,String date){
        this.title=title;
        this.titleImage=titleimg;
        this.post=post;
        this.date=date;

    }

}
