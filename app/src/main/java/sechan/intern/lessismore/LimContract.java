package sechan.intern.lessismore;

import java.util.Date;

import sechan.intern.lessismore.helpers.Comp;
import sechan.intern.lessismore.helpers.Post;
import sechan.intern.lessismore.util.BasePresenter;
import sechan.intern.lessismore.util.BaseView;

/**
 * Created by NAVER on 2017-07-21.
 */

public interface LimContract {

    interface View extends BaseView<Presenter> {

        void displayComponent(Post comps);

        void displayComponent(Comp comp, int index); //한개만 삽입하거나 추가할때

        void deleteComponent(int index); // 삭제는 인덱스만 필요함

        void displayTitleBackground(Comp img);

        void showSaved();

        void showLoaded();



    }

    interface Presenter extends BasePresenter {
        Post addComp(Comp.MapComp comp); // 파라메터를 맵 관련 정보로 바꿔야함

        Post addComp(String imagepath); // 파라메터를 이미지 관련 정보로 바꿔야할지도 모름

        Post addComp(Comp.TextComp comp); // 파라메터를 텍스트
        // 컴포넌트 추가 종류에 대한 PolyMorphism, 여기가 아니라 다른 곳에서 적용해야 할지도 모름
        //추가

        Post deleteComp(Comp comp);

        Post updateComp(Comp.TextComp comp);
        //수정, 삭제

        Post concatimgs(Comp.ImageComp img1, Comp.ImageComp img2); // 1개 + 1개

        Post concatimgs(Comp.ImagesComp imgs1, Comp.ImageComp img2); // 2개 + 1개
        //이미지 연결

        boolean save(Date date);

        Post load(int index);
        //저장 불러오기

        boolean setTitleBackground(String imagePath); // 리턴과 파라메터 수정해야할지도 모름


    }
}
