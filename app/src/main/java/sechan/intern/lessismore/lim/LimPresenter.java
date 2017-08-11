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


//public class LimPresenter implements LimContract.Presenter {
public class LimPresenter {

    //Singleton Pattern
    private final LimRepo mRepo;
    private final LimActivity mView;
    private final LimAdapter mAdapter;
    private final TextHelper mTextHelper;
    //private final ImageHelper mImageHelper;
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
        mAdapter = new LimAdapter(mRepo.getPost());
        mAdapter.setPresenter(this);
    }

    //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
//    @Override
    public void start() {
        mView.showHelperLoaded(mRepo.helperLoaded()); // 나중에 삭제, Helper-Repo-Presenter-View가 잘 연결되어 있는지 확인
        mView.setAdapter(mAdapter);
    }

    public void setTitleImage(String imagePath) {
        mRepo.setTitleImage(imagePath);
        mView.setTitleImage(imagePath);
    }

    public int addCompText() {
        // mRepo.saveArticle(); 먼저 상태 저장해야함

        int ret = mRepo.addCompText();
        if (ret >= 0) {
            mView.showMessage("텍스트 추가");
            mAdapter.notifyItemRangeChanged(ret, mRepo.getPost().size());
            //mAdapter.notifyItemInserted(ret);
            //mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());*/
            //mAdapter.notifyDataSetChanged();
        }
        return -1;
    }

    public int addCompImage(String imagePath) {
        int ret = mRepo.addCompImage((imagePath));
        if (ret >= 0) {
            mView.showMessage("이미지 추가");
            mAdapter.notifyItemRangeChanged(ret, mRepo.getPost().size());
            //mAdapter.notifyItemInserted(ret);
            //mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());
            //mAdapter.notifyDataSetChanged();

        }
        return -1;
    }

    public int addCompImage(String imagePath, int position) {
        int ret = mRepo.addCompImage(imagePath, position);
        if (ret >= 0) {
            mView.showMessage("이미지 추가");
            mAdapter.notifyItemRangeChanged(ret, mRepo.getPost().size());
            //mAdapter.notifyItemInserted(ret+1);
            //mAdapter.notifyItemChanged(ret+1);
            //mAdapter.notifyItemRangeChanged(ret,1);
            // mAdapter.notifyDataSetChanged();
            //mAdapter.notifyDataSetChanged();

        }
        return -1;
    }

    public int addCompMap(int mapx, int mapy, String title, String address) {
        //mRepo.addCompMap(mMapHelper.convert(mapx, mapy), title, address);
        mRepo.addCompMap(mapx, mapy, title, address);
        mAdapter.notifyDataSetChanged();
        return 0;
    }


    public void removeComp() {
        int position = mAdapter.getPosition();
        if (position >= 0) {
            mRepo.getPost().remove(position);
            mAdapter.removeComp();
            //mAdapter.notifyItemRemoved(position);
            //  mAdapter.notifyItemRangeChanged(position,mRepo.getPost().size());
            //mAdapter.notifyDataSetChanged();


            hideBtn();
        }
    }


    public void saveArticle(String title) {
/*        int position = mAdapter.getPosition();
        if (position >= 0) {
            Comp comp = mPost.get(position);
            if (comp instanceof CompText) mTextHelper.saveText((CompText) comp);
        }*/
        mTextHelper.saveText();
        mRepo.setTitle(title);
        mRepo.saveArticle();
        showMessage("저장되었습니다.");

    }
    public void removeArticle(int position) {
        mRepo.removeArticle(position);
        showMessage("삭제되었습니다.");
    }

    public void setStripable(boolean strip) {
        mView.showStripBtn(strip);
    }

    public void imageStrip() {
        int position = mAdapter.getPosition();
        CompImage prevImage = (CompImage) mPost.get(position - 1);
        CompImage currentImage = (CompImage) mPost.get(position);
        if ((prevImage.getSize() + currentImage.getSize()) <= 3) {
            prevImage.getImagePath().addAll(currentImage.getImagePath());
            mPost.remove(position);
            mAdapter.notifyDataSetChanged();


        }
        hideBtn();

        //mAdapter.notifyItemChanged(position-1); 체인지 시에 onBind 고려할것
    }

    public void imageDivide() {
        int position = mAdapter.getPosition();
        CompImage image = (CompImage) mPost.get(position);
        int lastIndex = image.getImagePath().size() - 1;
        addCompImage(image.getImagePath(lastIndex), position + 1); //다음 순서에 넣어야함
        image.getImagePath().remove(lastIndex);
        mAdapter.notifyDataSetChanged();
        hideBtn();
        //removeComp();
    }

    public void loadArticle(int position) {
        mRepo.loadArticle(position);
        mView.setTitleImage(mRepo.getTitleImage());
        mView.setTitleText(mRepo.getTitleText());
        mAdapter.notifyDataSetChanged();

    }
    //불러오기

    //불러오기


    public ArrayList<LimArticle> loadArticleList() {
        return mRepo.loadArticleList();

    }

    public void setFocused() {
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

    public void showMessage(String str) {
        mView.showMessage(str);
    }

    public void setCompText(LimEditText viewText, int position) {
        mTextHelper.setCompText(viewText, mRepo.getCompText(position));
    }

    public void saveText(CompText compText) {
        mTextHelper.saveText(compText);
    }

    public void getSpan(LimEditText editText, CompText compText) {
        mTextHelper.getSpan(editText, compText);
    }

    public void setDividable(boolean dividable) {
        if (dividable) mView.showDivideBtn(true);
        else mView.showDivideBtn(false);
    }

    public void hideBtn() {
        setDividable(false);
        setStripable(false);
        mView.showRemoveButton(false);

    }


}