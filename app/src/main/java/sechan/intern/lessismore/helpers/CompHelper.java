package sechan.intern.lessismore.helpers;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompText;


public class CompHelper {
    private final static int COMP_TEXT = 1;
    private final static int COMP_IMAGE = 2;
    private final static int COMP_MAP = 3;
    private static CompHelper instance = null;

    public static CompHelper getInstance() {
        if (instance == null) {
            instance = new CompHelper();
        }
        return instance;
    }

    public Post post;

    public Comp newCompText(){
     return new CompText();
 }
    public Comp newCompImage(){

        return null;
    }
    public Comp newCompImages(){

        return null;
    }
    public Comp newCompMap(){

        return null;
    }
    public Comp updateComp(Comp comp) {
        //파라메터 받아야함
        return null;
    }

    public Post getCompList() {
        return null;
    }

    public Comp getComp() {

        return null;
    }

}
