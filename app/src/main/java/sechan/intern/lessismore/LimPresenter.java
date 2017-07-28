package sechan.intern.lessismore;

import android.support.annotation.NonNull;

import java.util.Date;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompImages;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.components.LimConstant;
import sechan.intern.lessismore.helpers.Post;


//public class LimPresenter implements LimContract.Presenter {
public class LimPresenter  {

    //Singleton Pattern
    private final LimRepo mRepo;
    //private final LimContract.View mView;
    private final MainActivity mView;
    private LimAdapter mAdapter;
    private int text_start, text_end;
    private int focus;
    private int category;
    //private int[] getFocus = new int[4];
    //private static final ArrayList<Comp> compOrder = new ArrayList<>();

    public LimPresenter(@NonNull LimRepo repo,
                        @NonNull MainActivity view) {
        mRepo = repo;
        mView = view;
        mView.setPresenter(this);
    }

    //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
//    @Override
    public void start() {
        mView.showHelperLoaded(mRepo.helperLoaded()); // 나중에 삭제, Helper-Repo-Presenter-View가 잘 연결되어 있는지 확인
        mAdapter = new LimAdapter(mRepo.getPost());
        mView.setAdapter(mAdapter);
        mAdapter.setPresenter(this);

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

    public void setFocused(int position, int comp) {


    }


    public void setStyle(int style){
        switch(style){
            case LimConstant.TEXTBOLD:
                mAdapter.setBold();
                break;
            case LimConstant.TEXTITALIC:
                mAdapter.setItalic();
                break;
            case LimConstant.TEXTUNDERLINE:
                mAdapter.setUnderline();
                break;
            case LimConstant.TEXTINCSIZE:
                mAdapter.setIncSize();
                break;
            case LimConstant.TEXTDECSIZE:
                mAdapter.setDecSize();
                break;
        }
    }
    public void setStyle(int style, int color){
        mAdapter.setColor(color);
    }
    public void isBold(){
        mView.setBtn(LimConstant.TEXTBOLD);
    }
    public void isItalic(){
        mView.setBtn(LimConstant.TEXTITALIC);
    }
    public void isUnderLine(){
        mView.setBtn(LimConstant.TEXTUNDERLINE);
    }
    public void isColor(int color){
        mView.setBtn(LimConstant.TEXTCOLOR,color);
    }
    public void clearStyleCheck(){
        mView.clearBtn();
    }
    public void textFocus(boolean focus){
        mView.showTextWidget(focus);
    }

/*    public void setFocused(int position, int comp, int start, int end) {
        focus = position;
        category = comp;
        text_start = start;
        text_end = end;

    }*/
/*
    public int getFocused() {

        return focus;
    }

    public int getCategory() {
    return category;

    }
    public int getText_start(){
        return text_start;
    }
    public int getText_end(){

        return text_end;
    }*/
}