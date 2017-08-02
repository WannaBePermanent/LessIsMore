package sechan.intern.lessismore;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompImages;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.components.EnumText;

import static sechan.intern.lessismore.components.EnumText.TEXTBOLD;
import static sechan.intern.lessismore.components.EnumText.TEXTCOLOR;
import static sechan.intern.lessismore.components.EnumText.TEXTITALIC;
import static sechan.intern.lessismore.components.EnumText.TEXTUNDERLINE;


//public class LimPresenter implements LimContract.Presenter {
public class LimPresenter {

    //Singleton Pattern
    private final LimRepo mRepo;
    private final MainActivity mView;
    private final LimAdapter mAdapter;
    private final TextHelper mTextHelper;

    public LimPresenter(@NonNull LimRepo repo,
                        @NonNull MainActivity view) {
        mRepo = repo;
        mView = view;
        mView.setPresenter(this);
        mTextHelper = TextHelper.getInstance();
        mTextHelper.setPresenter(this);
        mAdapter = new LimAdapter(mRepo.getPost());
        mAdapter.setPresenter(this);
    }

    //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
//    @Override
    public void start() {
        mView.showHelperLoaded(mRepo.helperLoaded()); // 나중에 삭제, Helper-Repo-Presenter-View가 잘 연결되어 있는지 확인
        mView.setAdapter(mAdapter);
        mAdapter.setContext(mView.getApplicationContext());

    }

    public int addCompText() {
        // mRepo.savePostInstance(); 먼저 상태 저장해야함

        int ret = mRepo.addCompText();
        if (ret >= 0) {
            mView.showMessage("텍스트 추가");

            mAdapter.notifyItemInserted(ret);
            //mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());*/
            //mAdapter.notifyDataSetChanged();
        }
        return -1;
    }

    public int addCompImage(String imagePath) {
        int ret = mRepo.addCompImage((imagePath));
        if (ret >= 0) {
            mView.showMessage("이미지 추가");
            mAdapter.notifyItemInserted(ret);
            //mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());
            //mAdapter.notifyDataSetChanged();

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
            mAdapter.removeComp();
            //mAdapter.notifyItemRemoved(position);
          //  mAdapter.notifyItemRangeChanged(position,mRepo.getPost().size());
            //mAdapter.notifyDataSetChanged();
            mView.showRemoveButton(false);
        }
    }

    public ArrayList<Comp> updateComp(CompText comp) {
        return null;

    }
    //수정, 삭제

    public ArrayList<Comp> concatimgs(CompImage img1, CompImage img2) {
        return null;

    } // 1개 + 1개

    public ArrayList<Comp> concatimgs(CompImages imgs1, CompImage img2) {
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
    public void saveTextStyle(int position, EnumText type, int start, int end, int attr){
        mRepo.getCompText(position).saveTextStyle(type,start,end,attr);
        //mRepo.saveTextStyle(position, type,start,end,attr);

    }
    public void saveTextStyle(int position, EnumText type, int start, int end){
        mRepo.getCompText(position).saveTextStyle(type,start,end);
        //mRepo.saveTextStyle(position, type,start,end);
    }
    public void saveText(int position, String str){
        mRepo.getCompText(position).saveText(str);
    }

    public ArrayList<Comp> load(int index) {
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


    public void setStyle(EnumText style) {
        switch (style) {
            case TEXTBOLD:
                //mAdapter.setBold();
                mTextHelper.setBold();
                break;
            case TEXTITALIC:
                //mAdapter.setItalic();
                mTextHelper.setItalic();
                break;
            case TEXTUNDERLINE:
                //mAdapter.setUnderline();
                mTextHelper.setUnderline();
                break;
            case TEXTINCSIZE:
                //mAdapter.setTextSize(true);
                mTextHelper.setTextSize(true);
                break;
            case TEXTDECSIZE:
                //mAdapter.setTextSize(false);
                mTextHelper.setTextSize(false);
                break;
        }
    }

    public void setStyle(EnumText style, int color) {
        mTextHelper.setColor(color);
        //mAdapter.setColor(color);
    }

    public void setBold(boolean b) {
        mView.setBtn(TEXTBOLD, b);
    }

    public void setItalic(boolean b) {
        mView.setBtn(TEXTITALIC, b);
    }

    public void setUnderline(boolean b) {
        mView.setBtn(TEXTUNDERLINE, b);
    }

    public void setColor(int color) {
        mView.setBtn(TEXTCOLOR, color);
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