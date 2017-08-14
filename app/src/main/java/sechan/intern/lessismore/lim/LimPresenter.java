package sechan.intern.lessismore.lim;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import sechan.intern.lessismore.lim.adapter.LimAdapter;
import sechan.intern.lessismore.lim.components.Comp;
import sechan.intern.lessismore.lim.components.CompImage;
import sechan.intern.lessismore.lim.components.CompText;
import sechan.intern.lessismore.lim.components.LimEditText;
import sechan.intern.lessismore.lim.components.enumcomp.EnumText;
import sechan.intern.lessismore.model.LimArticle;
import sechan.intern.lessismore.model.LimRepo;
import sechan.intern.lessismore.model.helpers.TextHelper;

import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTBOLD;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTCOLOR;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTITALIC;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTUNDERLINE;


public class LimPresenter implements LimContract.Presenter {


    //Singleton Pattern
    private final LimRepo mRepo;
    private final LimActivity mView;
    private final LimAdapter mAdapter;
    private final TextHelper mTextHelper;
    private final ArrayList<Comp> mPost;

    public LimPresenter(@NonNull LimRepo repo,
                        @NonNull LimActivity view) {
        mRepo = repo;
        mPost = mRepo.getPost();
        mView = view;
        mView.setPresenter(this);
        mRepo.setDBHelper(mView.getApplicationContext());
        mTextHelper = TextHelper.getInstance();
        mTextHelper.setPresenter(this);
        mAdapter = new LimAdapter(mPost);
        mAdapter.setPresenter(this);
    }

    //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
    @Override
    public void start() {
        mView.setAdapter(mAdapter);
    }

    @Override
    public void setTitleImage(String imagePath) {
        mRepo.setTitleImage(imagePath);
        mView.setTitleImage(imagePath);
    }

    @Override
    public void addCompText() {

        int ret = mRepo.addCompText();
        if (ret >= 0) {
            mAdapter.notifyItemRangeChanged(ret, mPost.size());

        }

    }

    @Override
    public void addCompImage(String imagePath) {
        int ret = mRepo.addCompImage((imagePath));
        if (ret >= 0) {
            mAdapter.notifyItemRangeChanged(ret, mPost.size());

        }

    }

    @Override
    public void addCompImage(String imagePath, int position) {
        int ret = mRepo.addCompImage(imagePath, position);
        if (ret >= 0) {
            mAdapter.notifyItemRangeChanged(ret, mPost.size());


        }
    }

    @Override
    public void addCompMap(int mapx, int mapy, String title, String address) {
        mRepo.addCompMap(mapx, mapy, title, address);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeComp() {
        int position = mAdapter.getPosition();
        if (position >= 0) {
            mPost.remove(position);
            mAdapter.removeComp();
            //mAdapter.notifyItemRemoved(position);
            //  mAdapter.notifyItemRangeChanged(position,mPost.size());
            //mAdapter.notifyDataSetChanged();


            hideBtn();
        }
    }

    @Override
    public void saveArticle(String title) {
        mTextHelper.saveText();
        mRepo.setTitle(title);
        mRepo.saveArticle();
        showMessage("저장되었습니다.");

    }

    @Override
    public void removeArticle(int position) {
        mRepo.removeArticle(position);
        showMessage("삭제되었습니다.");
    }

    @Override
    public void setStripable(boolean strip) {
        mView.showStripBtn(strip);
    }

    @Override
    public void imageStrip() {
        int position = mAdapter.getPosition();
        CompImage prevImage = (CompImage) mPost.get(position - 1);
        CompImage currentImage = (CompImage) mPost.get(position);
        if ((prevImage.getSize() + currentImage.getSize()) <= 3) {
            prevImage.getImagePath().addAll(currentImage.getImagePath());
            //mPost.remove(position);
            mRepo.removeComp(position);
            mAdapter.notifyDataSetChanged();
        }
        hideBtn();

        //mAdapter.notifyItemChanged(position-1); 체인지 시에 onBind 고려할것
    }

    @Override
    public void imageDivide() {
        int position = mAdapter.getPosition();
        CompImage image = (CompImage) mPost.get(position);
        int lastIndex = image.getImagePath().size() - 1;
        addCompImage(image.getImagePath(lastIndex), position + 1); //다음 순서에 넣어야함
        image.getImagePath().remove(lastIndex);
        mAdapter.notifyDataSetChanged();
        hideBtn();
    }

    @Override
    public void loadArticle(int position) {
        mRepo.loadArticle(position);
        mView.setTitleImage(mRepo.getTitleImage());
        mView.setTitleText(mRepo.getTitleText());
        mAdapter.notifyDataSetChanged();

    }


    @Override
    public ArrayList<LimArticle> loadArticleList() {
        return mRepo.loadArticleList();

    }

    @Override
    public void setFocused() {
        mView.showRemoveButton(true);

    }

    @Override
    public void setStyle(EnumText style) {
        switch (style) {
            case TEXTBOLD:
                mTextHelper.setBold();
                break;
            case TEXTITALIC:
                mTextHelper.setItalic();
                break;
            case TEXTUNDERLINE:
                mTextHelper.setUnderline();
                break;
            case TEXTINCSIZE:
                mTextHelper.setTextSize(true);
                break;
            case TEXTDECSIZE:
                mTextHelper.setTextSize(false);
                break;
        }
    }

    @Override
    public void setStyle(EnumText style, int color) {
        mTextHelper.setColor(color);        
    }

    @Override
    public void setBold(boolean b) {
        mView.setBtn(TEXTBOLD, b);
    }

    @Override
    public void setItalic(boolean b) {
        mView.setBtn(TEXTITALIC, b);
    }

    @Override
    public void setUnderline(boolean b) {
        mView.setBtn(TEXTUNDERLINE, b);
    }

    @Override
    public void setColor(int color) {
        mView.setBtn(TEXTCOLOR, color);
    }

    @Override
    public void clearStyleCheck() {
        mView.clearBtn();
    }

    @Override
    public void textFocus(boolean focus) {
        mView.showTextWidget(focus);
    }

    @Override
    public void showMessage(String str) {
        mView.showMessage(str);
    }

    @Override
    public void setCompText(LimEditText viewText, int position) {
        mTextHelper.setCompText(viewText, mRepo.getCompText(position));
    }

    @Override
    public void saveText(CompText compText) {
        mTextHelper.saveText(compText);
    }

    @Override
    public void getSpan(LimEditText editText, CompText compText) {
        mTextHelper.getSpan(editText, compText);
    }

    @Override
    public void setDividable(boolean dividable) {
        if (dividable) mView.showDivideBtn(true);
        else mView.showDivideBtn(false);
    }

    @Override
    public void hideBtn() {
        setDividable(false);
        setStripable(false);
        mView.showRemoveButton(false);

    }


}