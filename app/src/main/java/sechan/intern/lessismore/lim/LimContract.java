package sechan.intern.lessismore.lim;

import java.util.ArrayList;
import java.util.Date;

import sechan.intern.lessismore.BasePresenter;
import sechan.intern.lessismore.BaseView;
import sechan.intern.lessismore.lim.adapter.LimAdapter;
import sechan.intern.lessismore.lim.components.Comp;
import sechan.intern.lessismore.lim.components.CompText;


public interface LimContract {
/*    public final static int COMP_TEXT=1;
    public final static int COMP_IMAGE=2;
    public final static int COMP_IMAGES=3;
    public final static int COMP_MAP=4;*/

    interface View extends BaseView<Presenter> {

       // void displayComponent(Post comps);
        void displayComponent(int index);
        void displayComponent(Comp comp, int index); //한개만 삽입하거나 추가할때

        void deleteComponent(int index); // 삭제는 인덱스만 필요함

        void displayTitleBackground(Comp img);

        void showSaved();

        void showLoaded();

       void showHelperLoaded(boolean loaded);
        void showMessage(String message);
        void setAdapter(LimAdapter adapter);
        void displayComponent();
    }

    interface Presenter extends BasePresenter {

        //void addCompText(String text);
        //void addCompImage(String uri);
        //void addCompMap(String text);


       int addCompText();
        void setStyle(int style);
        void setStyle(int style, int color);
        //int addCompText(int index);
        //int addCompImage();
        int addCompImage(String s);
        int addCompImages();
        int addCompMap();

        // 컴포넌트 추가 종류에 대한 PolyMorphism, 여기가 아니라 다른 곳에서 적용해야 할지도 모름
        //추가

        ArrayList<Comp> deleteComp(Comp comp);

        ArrayList<Comp> updateComp(CompText comp);
        //수정, 삭제




        boolean save(Date date);

        ArrayList<Comp> load(int index);
        //저장 불러오기

        boolean setTitleBackground(String imagePath); // 리턴과 파라메터 수정해야할지도 모름

        void loadList();

    }
}
