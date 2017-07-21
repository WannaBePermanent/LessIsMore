package sechan.intern.lessismore;

import android.support.annotation.NonNull;

import java.util.Date;

import sechan.intern.lessismore.helpers.Comp;
import sechan.intern.lessismore.helpers.Post;

/**
 * Created by NAVER on 2017-07-21.
 */

public class LimPresenter implements LimContract.Presenter {
    //Singleton Pattern
    private final LimRepo mRepo;

    private final LimContract.View mView;

    public LimPresenter(@NonNull LimRepo repo,
                        @NonNull LimContract.View view) {
        mRepo = repo;
        mView = view;
        mView.setPresenter(this);
    }

    //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
    @Override
    public void start() {


    }

    public Post addComp(Comp.TextComp comp) {
        return null;
    }
    // 파라메터를 텍스트

    public Post addComp(Comp.MapComp comp) {

        return null;
    } // 파라메터를 맵 관련 정보로 바꿔야함

    public Post addComp(String imagepath) {
        return null;

    } // 파라메터를 이미지 관련 정보로 바꿔야할지도 모름

    // 컴포넌트 추가 종류에 대한 PolyMorphism, 여기가 아니라 다른 곳에서 적용해야 할지도 모름
    //추가

    public Post deleteComp(Comp comp) {
        return null;
    }

    public Post updateComp(Comp.TextComp comp) {
        return null;

    }
    //수정, 삭제

    public Post concatimgs(Comp.ImageComp img1, Comp.ImageComp img2) {
        return null;

    } // 1개 + 1개

    public Post concatimgs(Comp.ImagesComp imgs1, Comp.ImageComp img2) {
        return null;

    }
    //이미지 연결

    public boolean save(Date date) {
        return false;

    } //저장

    public Post load(int index) {
        return null;
    }
    //불러오기

    public boolean setTitleBackground(String imagePath) {
        return false;

    } // 리턴과 파라메터 수정해야할지도 모름


}