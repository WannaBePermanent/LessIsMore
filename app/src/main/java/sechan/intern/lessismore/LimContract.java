package sechan.intern.lessismore;

import java.util.Date;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompImages;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.helpers.Post;
import sechan.intern.lessismore.util.BasePresenter;
import sechan.intern.lessismore.util.BaseView;


public interface LimContract {
    public final static int COMP_TEXT=1;
    public final static int COMP_IMAGE=2;
    public final static int COMP_IMAGES=3;
    public final static int COMP_MAP=4;

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
        void setAdapter(Post post);
        void displayComponent();
    }

    interface Presenter extends BasePresenter {

        //void addCompText(String text);
        //void addCompImage(String uri);
        //void addCompMap(String text);


       int addCompText();
        //int addCompText(int index);
        //int addCompImage();
        int addCompImage(String s);
        int addCompImages();
        int addCompMap();

        // 컴포넌트 추가 종류에 대한 PolyMorphism, 여기가 아니라 다른 곳에서 적용해야 할지도 모름
        //추가

        Post deleteComp(Comp comp);

        Post updateComp(CompText comp);
        //수정, 삭제

        Post concatimgs(CompImage img1, CompImage img2); // 1개 + 1개

        Post concatimgs(CompImages imgs1, CompImage img2); // 2개 + 1개
        //이미지 연결

        boolean save(Date date);

        Post load(int index);
        //저장 불러오기

        boolean setTitleBackground(String imagePath); // 리턴과 파라메터 수정해야할지도 모름

        void loadList();

    }
}
