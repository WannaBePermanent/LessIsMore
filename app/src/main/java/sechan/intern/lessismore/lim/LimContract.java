package sechan.intern.lessismore.lim;

import java.util.ArrayList;

import sechan.intern.lessismore.BasePresenter;
import sechan.intern.lessismore.BaseView;
import sechan.intern.lessismore.lim.adapter.LimAdapter;
import sechan.intern.lessismore.lim.components.CompText;
import sechan.intern.lessismore.lim.components.LimEditText;
import sechan.intern.lessismore.lim.components.enumcomp.EnumText;
import sechan.intern.lessismore.model.LimArticle;


public interface LimContract {

    interface View extends BaseView<Presenter> {
        void showMessage(String message);
        void setAdapter(LimAdapter adapter);

        void setBtn(EnumText style, boolean isSet);

        void showRemoveButton(boolean isVisible);

        void setBtn(EnumText style, int color);

        void clearBtn();

        void showTextWidget(boolean show);

        void showStripBtn(boolean show);

        void showDivideBtn(boolean show);

        void setTitleImage(String imagePath);

        void setTitleText(String title);
    }

    interface Presenter extends BasePresenter {

        void addCompText();
        void setStyle(EnumText enumText, int attr);

        void setFocused();

        void setStyle(EnumText enumText);

        void setStripable(boolean strip);

        void imageStrip();
        void imageDivide();

        //메소드들이 LimContract에 맵핑되므로 LimContract와 같이 수정해야함
        @Override
        void start();

        void setTitleImage(String image);
        void loadArticle(int position);
        void removeArticle(int position);
        void removeComp();
        void saveArticle(String title);
        ArrayList<LimArticle> loadArticleList();

        void addCompImage(String imagePath, int position);

        void addCompMap(int mapx, int mapy, String title, String address);
        void addCompImage(String s);

        void setBold(boolean b);

        void setItalic(boolean b);

        void setUnderline(boolean b);

        void setColor(int color);

        void clearStyleCheck();

        void textFocus(boolean focus);

        void showMessage(String str);

        void setCompText(LimEditText viewText, int position);

        void saveText(CompText compText);

        void getSpan(LimEditText editText, CompText compText);

        void setDividable(boolean dividable);

        void hideBtn();
    }

    interface Adapter {
        void setPresenter(LimPresenter presenter);
        void removeComp();
        int getPosition();
    }
}
