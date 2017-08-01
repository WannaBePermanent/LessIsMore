package sechan.intern.lessismore;

import android.support.annotation.NonNull;

import java.util.Date;

import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompImages;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.components.LimConstant;
import sechan.intern.lessismore.helpers.Post;


//public class LimPresenter implements LimContract.Presenter {
public class LimPresenter {

    //Singleton Pattern
    private final LimRepo mRepo;

    private final MainActivity mView;
    private LimAdapter mAdapter;


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
        mAdapter.setContext(mView.getApplicationContext());

    }

    public int addCompText() {
        // mRepo.savePostInstance(); 먼저 상태 저장해야함

        int ret = mRepo.addCompText();
        if (ret >= 0) {
            mView.showMessage("텍스트 추가");
            mAdapter.notifyItemInserted(ret);
        }
        return -1;
    }

    public int addCompImage(String imagePath) {
        int ret = mRepo.addCompImage((imagePath));
        if (ret >= 0) {
            mView.showMessage("이미지 추가");
            mAdapter.notifyItemInserted(ret);
        }
        return -1;
    }

    public int addCompImages() {
        //mRepo.addCompImages();
        return 0;
    }

    public int addCompMap() {
        //mRepo.addCompMap();
        return 0;
    }

    public void removeComp(){
        int position = mAdapter.getPosition();
        if (position >= 0) {
            mRepo.getPost().remove(position);
            mAdapter.deleteComp();
            mView.showRemoveButton(false);
        }
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
    public boolean save() {
    mAdapter.saveText();
        return false;
    } //저장
    public void saveTextStyle(int position, int type, int start, int end, int attr){
        mRepo.getCompText(position).saveTextStyle(type,start,end,attr);
        //mRepo.saveTextStyle(position, type,start,end,attr);

    }
    public void saveTextStyle(int position, int type, int start, int end){
        mRepo.getCompText(position).saveTextStyle(type,start,end);
        //mRepo.saveTextStyle(position, type,start,end);
    }
    public void saveText(int position, String str){
        mRepo.getCompText(position).saveText(str);
    }

    public Post load(int index) {
        return null;
    }
    //불러오기

    public boolean setTitleBackground(String imagePath) {
        return false;

    } // 리턴과 파라메터 수정해야할지도 모름

    public void loadList() {

    }

    public void setFocused(int position) {
        mView.showRemoveButton(true);


    }


    public void setStyle(int style) {
        switch (style) {
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
                mAdapter.setTextSize(true);
                break;
            case LimConstant.TEXTDECSIZE:
                mAdapter.setTextSize(false);
                break;
        }
    }

    public void setStyle(int style, int color) {
        mAdapter.setColor(color);
    }

    public void isBold(boolean b) {
        mView.setBtn(LimConstant.TEXTBOLD, b);
    }

    public void isItalic(boolean b) {
        mView.setBtn(LimConstant.TEXTITALIC, b);
    }

    public void isUnderLine(boolean b) {
        mView.setBtn(LimConstant.TEXTUNDERLINE, b);
    }

    public void isColor(int color) {
        mView.setBtn(LimConstant.TEXTCOLOR, color);
    }

    public void clearStyleCheck() {
        mView.clearBtn();
    }

    public void textFocus(boolean focus) {
        mView.showTextWidget(focus);
    }

    public void showMessage(String str){
        mView.showMessage(str);
    }
}