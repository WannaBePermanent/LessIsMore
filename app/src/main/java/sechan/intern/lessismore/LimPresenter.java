package sechan.intern.lessismore;

import android.support.annotation.NonNull;

import java.util.Date;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompImages;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.helpers.Post;


public class LimPresenter implements LimContract.Presenter {

    //Singleton Pattern
    private final LimRepo mRepo;

    private final LimContract.View mView;
    //private static final ArrayList<Comp> compOrder = new ArrayList<>();

    public LimPresenter(@NonNull LimRepo repo,
                        @NonNull LimContract.View view) {
        mRepo = repo;
        mView = view;
        mView.setPresenter(this);
    }

    //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
    @Override
    public void start() {
        mView.showHelperLoaded(mRepo.helperLoaded()); // 나중에 삭제, Helper-Repo-Presenter-View가 잘 연결되어 있는지 확인
        mView.setAdapter(mRepo.getPost());

    }

/*    public int addCompText() {
        // mRepo.savePostInstance(); 먼저 상태 저장해야함
        if (mRepo.addCompText() == 1) {
            mView.showMessage("텍스트 추가");
            mView.displayComponent();
        }
        return 0;
    }*/
    public int addCompText() {
        // mRepo.savePostInstance(); 먼저 상태 저장해야함
        // index 규약 추후 수정
        int rv = mRepo.addCompText();
        if (rv >= 0) {
            mView.showMessage("텍스트 추가");
            mView.displayComponent(rv);
        }
        return -1;
    }
    public int addCompImage(String imagePath) {
        if (mRepo.addCompImage(imagePath) == 1) {
            mView.showMessage("이미지 추가");
            mView.displayComponent();
        }
        return 0;
    }

    public int addCompImages() {
        //mRepo.addCompImages();
        return 0;
    }

    public int addCompMap() {
        //mRepo.addCompMap();
        return 0;
    }

    public Post deleteComp(Comp comp) {
        return null;
    }

    public Post updateComp(CompText comp) {
        return null;

    }
    //수정, 삭제

    public Post concatimgs(CompImage img1, CompImage img2) {
        return null;

    } // 1개 + 1개

    public Post concatimgs(CompImages imgs1, CompImage img2) {
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

    public void loadList() {


    }
}