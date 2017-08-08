package sechan.intern.lessismore.lim;

import android.support.annotation.NonNull;

import com.nhn.android.maps.NMapView;

import java.util.ArrayList;
import java.util.Date;

import sechan.intern.lessismore.Model.LimRepo;
import sechan.intern.lessismore.Model.helpers.MapHelper;
import sechan.intern.lessismore.Model.helpers.TextHelper;
import sechan.intern.lessismore.lim.adapter.LimAdapter;
import sechan.intern.lessismore.lim.components.Comp;
import sechan.intern.lessismore.lim.components.CompImage;
import sechan.intern.lessismore.lim.components.CompMap;
import sechan.intern.lessismore.lim.components.Enum.EnumText;
import sechan.intern.lessismore.lim.components.LimEditText;

import static sechan.intern.lessismore.lim.components.Enum.EnumText.TEXTBOLD;
import static sechan.intern.lessismore.lim.components.Enum.EnumText.TEXTCOLOR;
import static sechan.intern.lessismore.lim.components.Enum.EnumText.TEXTITALIC;
import static sechan.intern.lessismore.lim.components.Enum.EnumText.TEXTUNDERLINE;


//public class LimPresenter implements LimContract.Presenter {
public class LimPresenter {

    //Singleton Pattern
    private final LimRepo mRepo;
    private final LimActivity mView;
    private final LimAdapter mAdapter;
    private final TextHelper mTextHelper;
    private final MapHelper mMapHelper;
    //private final ImageHelper mImageHelper;
    private final ArrayList<Comp> mPost;
    public LimPresenter(@NonNull LimRepo repo,
                        @NonNull LimActivity view) {
        mRepo = repo;
        mPost = mRepo.getPost();
        mView = view;
        mView.setPresenter(this);
        mTextHelper = TextHelper.getInstance();
        mTextHelper.setPresenter(this);
        mMapHelper = MapHelper.getInstance();
/*        mImageHelper = ImageHelper.getInstance();
        mImageHelper.setPresenter(this);*/
        mAdapter = new LimAdapter(mRepo.getPost());
        mAdapter.setPresenter(this);
    }

    //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
//    @Override
    public void start() {
        mView.showHelperLoaded(mRepo.helperLoaded()); // 나중에 삭제, Helper-Repo-Presenter-View가 잘 연결되어 있는지 확인
        mView.setAdapter(mAdapter);
    }

    public int addCompText() {
        // mRepo.savePostInstance(); 먼저 상태 저장해야함

        int ret = mRepo.addCompText();
        if (ret >= 0) {
            mView.showMessage("텍스트 추가");
            mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());
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
            mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());
            //mAdapter.notifyItemInserted(ret);
            //mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());
            //mAdapter.notifyDataSetChanged();

        }
        return -1;
    }
    public int addCompImage(String imagePath,int position) {
        int ret = mRepo.addCompImage(imagePath,position);
        if (ret >= 0) {
            mView.showMessage("이미지 추가");
            mAdapter.notifyItemRangeChanged(ret,mRepo.getPost().size());
            //mAdapter.notifyItemInserted(ret+1);
            //mAdapter.notifyItemChanged(ret+1);
            //mAdapter.notifyItemRangeChanged(ret,1);
            // mAdapter.notifyDataSetChanged();
            //mAdapter.notifyDataSetChanged();

        }
        return -1;
    }

    public int addCompMap(int mapx,int mapy, String title, String address) {
        mRepo.addCompMap(mMapHelper.convert(mapx,mapy),title,address);
        return 0;
    }
    public void setMapView(NMapView mapView, int position){
        mMapHelper.setMapView(mapView,(CompMap) mPost.get(position));
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


    public boolean save(Date date) {
        return false;

    } //저장

    public void setStripable(boolean strip){
        mView.showStripBtn(strip);
    }
    public void imageStrip() {
        int position = mAdapter.getPosition();
        CompImage prevImage = (CompImage) mPost.get(position-1);
        CompImage currentImage = (CompImage) mPost.get(position);
        if ((prevImage.getSize() + currentImage.getSize())<=3){
            prevImage.getImagePath().addAll(currentImage.getImagePath());
            mPost.remove(position);
            mAdapter.notifyDataSetChanged();


        }
        hideBtn();

        //mAdapter.notifyItemChanged(position-1); 체인지 시에 onBind 고려할것
    }
    public void imageDivide(){
        int position = mAdapter.getPosition();
        CompImage image = (CompImage) mPost.get(position);
        int lastIndex = image.getImagePath().size() - 1;
        addCompImage(image.getImagePath(lastIndex),position+1); //다음 순서에 넣어야함
        image.getImagePath().remove(lastIndex);
        mAdapter.notifyDataSetChanged();
        hideBtn();
        //removeComp();
    }

    public boolean save() {
        mAdapter.saveText();
        return false;
    } //저장

    public void saveTextStyle(int position, EnumText type, int start, int end, int attr) {
        mRepo.getCompText(position).saveTextStyle(type, start, end, attr);
        //mRepo.saveTextStyle(position, type,start,end,attr);

    }

    public void saveTextStyle(int position, EnumText type, int start, int end) {
        mRepo.getCompText(position).saveTextStyle(type, start, end);
        //mRepo.saveTextStyle(position, type,start,end);
    }

    public void saveText(int position, String str) {
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

    public void showMessage(String str) {
        mView.showMessage(str);
    }

    public void setCompText(LimEditText compText) {
        mTextHelper.setCompText(compText);
    }

    public void saveText() {
        //mTextHelper.saveText();
    }
    public Comp getCurrentComp(){
        int position = mAdapter.getPosition();
        if (position>=0) return mPost.get(mAdapter.getPosition());
        else return null;
    }

    public void setDividable(boolean dividable){
        if (dividable) mView.showDivideBtn(true);
        else mView.showDivideBtn(false);
    }

    public void hideBtn(){
        setDividable(false);
        setStripable(false);
        mView.showRemoveButton(false);

    }


}