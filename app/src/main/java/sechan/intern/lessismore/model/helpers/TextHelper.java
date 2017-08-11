package sechan.intern.lessismore.model.helpers;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import java.util.ArrayList;

import sechan.intern.lessismore.lim.LimPresenter;
import sechan.intern.lessismore.lim.components.CompText;
import sechan.intern.lessismore.lim.components.LimEditText;
import sechan.intern.lessismore.lim.components.enumcomp.EnumText;
import sechan.intern.lessismore.lim.components.textstyle.LimAbsoluteSizeSpan;
import sechan.intern.lessismore.lim.components.textstyle.LimForegroundColorSpan;
import sechan.intern.lessismore.lim.components.textstyle.LimStyleSpan;
import sechan.intern.lessismore.lim.components.textstyle.LimTextStyle;
import sechan.intern.lessismore.lim.components.textstyle.LimUnderlineSpan;

import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTCOLOR;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTSIZE;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTUNDERLINE;

/**
 * Created by Sechan on 2017-08-02.
 */

public class TextHelper {
    private static TextHelper instance = null;

    public static TextHelper getInstance() {
        if (instance == null) {
            instance = new TextHelper();
        }
        return instance;
    }

    private LimPresenter mPresenter;
    private boolean doStyleJob = false;
    private boolean doSpace = false;
    private LimStyleSpan spanBold = null; //클래스 만들어서
    private LimStyleSpan spanItalic = null;
    private LimUnderlineSpan spanUnderline = null;
    private LimForegroundColorSpan spanColor = null;
    private LimAbsoluteSizeSpan spanSize = null;
    private LimEditText mEdit;
    private Spannable mSpan;
    private int mStart;
    private int mEnd;
    private int mTextSize;
    CompText mCompText;
    //private CompText currentComp;

    public void setPresenter(LimPresenter pres) {
        mPresenter = pres;
    }

    public void setCompText(LimEditText lt, CompText compText) {
        mCompText = compText;
        setCompText(lt);
    }

    public void setCompText(LimEditText lt) {
        mEdit = lt;
        mSpan = mEdit.getText();
        mTextSize = (int) mEdit.getTextSize();
        init();
        mEdit.addOnSelectionChangedListener(new LimEditText.onSelectionChangedListener() {
            @Override
            public void onSelectionChanged(int selStart, int selEnd) {
                init();
            }
        });

    }

    private void init() {
        mStart = mEdit.getSelectionStart();
        mEnd = mEdit.getSelectionEnd();

        //mSpan = mEdit.getText();
        if (!doSpace) getStyle();
        /*
        if (mPresenter.getCurrentComp() != null && mPresenter.getCurrentComp().getType() == COMP_TEXT)
            saveText((CompText) mPresenter.getCurrentComp());
            */
    }


    public void setBold() {
        if (spanBold == null) {
            spanBold = new LimStyleSpan(Typeface.BOLD);
            setStyle(spanBold);
            mPresenter.setBold(true); //메소드명 변경
        } else {
            setStyle(spanBold, spanBold.start, spanBold.end, new LimStyleSpan(Typeface.BOLD), new LimStyleSpan(Typeface.BOLD));
            spanBold = null;
            mPresenter.setBold(false);

        }


    }

    public void setItalic() {
        if (spanItalic == null) {
            spanItalic = new LimStyleSpan(Typeface.ITALIC);
            setStyle(spanItalic);
            mPresenter.setItalic(true);
        } else {
            setStyle(spanItalic, spanItalic.start, spanItalic.end, new LimStyleSpan(Typeface.ITALIC), new LimStyleSpan(Typeface.ITALIC));
            spanItalic = null;
            mPresenter.setItalic(false);
        }
    }

    public void setUnderline() {
        if (spanUnderline == null) {
            spanUnderline = new LimUnderlineSpan();
            setStyle(spanUnderline);
            mPresenter.setUnderline(true);
        } else {
            setStyle(spanUnderline, spanUnderline.start, spanUnderline.end, new LimUnderlineSpan(), new LimUnderlineSpan());
            spanUnderline = null;
            mPresenter.setUnderline(false);

        }
    }

    //부모로 공통으로 사용, 데이터 정보 클래스로 만들기
    public void setColor(int color) {
        if (spanColor == null) {
            spanColor = new LimForegroundColorSpan(color);
            setStyle(spanColor);
        } else {
            setStyle(spanColor, spanColor.start, spanColor.end, new LimForegroundColorSpan(spanColor.getForegroundColor()), new LimForegroundColorSpan(spanColor.getForegroundColor()));
            setStyle(new LimForegroundColorSpan(color));
        }
        mPresenter.setColor(color);
    }

    public void setTextSize(boolean inc) {
        int textSize = mTextSize;
        if (spanSize != null) {
            textSize = spanSize.getSize();
            setStyle(spanSize, spanSize.start, spanSize.end, new LimAbsoluteSizeSpan(textSize), new LimAbsoluteSizeSpan(textSize));
        }
        if (inc) spanSize = new LimAbsoluteSizeSpan(textSize + 5);
        else spanSize = new LimAbsoluteSizeSpan(textSize - 5);
        setStyle(spanSize);

    }

    private void setStyle(CharacterStyle cSpan, int start, int end, CharacterStyle newSpan, CharacterStyle newSpan2) {
        mSpan.removeSpan(cSpan);
        int end2 = end;
        if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
        if (start <= mStart && mStart <= end2) {
            mSpan.setSpan(newSpan, start, mStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        if (start <= mEnd && mEnd <= end2) {
            mSpan.setSpan(newSpan2, mEnd, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        //쪼개서 스펜넣기

    }

    private void setStyle(CharacterStyle newSpan) {
        preStyle();
        mSpan.setSpan(newSpan,
                mStart, mEnd,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

    }

    private void setStyle(Spannable initSpan, CharacterStyle newSpan, int start, int end) {
        initSpan.setSpan(newSpan,
                start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        // 로딩할 때 쓰는 setStyle

    }
    //setStyle로 메소드 바꿔서 줄임

    private boolean preStyle() { // 공백 생성후 선택

        if (!mEdit.hasSelection()) {
            doSpace = true;
            mEdit.getText().insert(mEdit.getSelectionStart(), " ");
            mEdit.setSelection(mEdit.getSelectionStart() - 1, mEdit.getSelectionStart());
            doSpace = false;
            return true;
        }
        return false;
    }


    private void getStyle() {
        LimStyleSpan styleSpan[] = mSpan.getSpans(mStart, mEnd, LimStyleSpan.class);
        LimUnderlineSpan underlineSpan[] = mSpan.getSpans(mStart, mEnd, LimUnderlineSpan.class);
        LimForegroundColorSpan colorSpan[] = mSpan.getSpans(mStart, mEnd, LimForegroundColorSpan.class);
        LimAbsoluteSizeSpan sizeSpan[] = mSpan.getSpans(mStart, mEnd, LimAbsoluteSizeSpan.class);
        mPresenter.clearStyleCheck();
        spanBold = null;
        spanItalic = null;
        spanUnderline = null;
        spanColor = null;
        for (LimStyleSpan styleElement : styleSpan) {
            if (mSpan.getSpanStart(styleElement) == mEnd) continue; // 선택 앞쪽에서는 스타일 적용이 일어나면 안된다
            if (styleElement.getStyle() == Typeface.BOLD) {
                spanBold = styleElement;
                spanBold.start = mSpan.getSpanStart(styleElement);
                spanBold.end = mSpan.getSpanEnd(styleElement);
                mPresenter.setBold(true);
            }
            if (styleElement.getStyle() == Typeface.ITALIC) {
                spanItalic = styleElement;
                spanItalic.start = mSpan.getSpanStart(styleElement);
                spanItalic.end = mSpan.getSpanEnd(styleElement);
                mPresenter.setItalic(true);

            }
        }
        for (LimUnderlineSpan underlineElement : underlineSpan) {
            if (mSpan.getSpanStart(underlineElement) == mEnd) continue;
            spanUnderline = underlineElement;
            spanUnderline.start = mSpan.getSpanStart(underlineElement);
            spanUnderline.end = mSpan.getSpanEnd(underlineElement);
            mPresenter.setUnderline(true);

        }

        for (LimForegroundColorSpan colorElement : colorSpan) {
            if (mSpan.getSpanStart(colorElement) == mEnd) continue;
            spanColor = colorElement;
            spanColor.start = mSpan.getSpanStart(colorElement);
            spanColor.end = mSpan.getSpanEnd(colorElement);
            mPresenter.setColor(colorElement.getForegroundColor());
        }
        for (LimAbsoluteSizeSpan sizeElement : sizeSpan) {
            if (mSpan.getSpanStart(sizeElement) == mEnd) continue;
            spanSize = sizeElement;
            spanSize.start = mSpan.getSpanStart(sizeElement);
            spanSize.end = mSpan.getSpanEnd(sizeElement);
        }
    }

    public void saveText(){
     if (mCompText != null)   saveText(mCompText);
    }
    public void saveText(CompText compText) {
        compText.saveText(mEdit.getText().toString());
        Spannable tempSpan = mEdit.getText();
        compText.clearStyle();
        LimStyleSpan styleSpan[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimStyleSpan.class);
        LimUnderlineSpan underlineSpan[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimUnderlineSpan.class);
        LimForegroundColorSpan colorSpan[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimForegroundColorSpan.class);
        LimAbsoluteSizeSpan sizeSpan[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimAbsoluteSizeSpan.class);
        for (LimStyleSpan styleElement : styleSpan) {
            styleElement.start = tempSpan.getSpanStart(styleElement);
            styleElement.end = tempSpan.getSpanEnd(styleElement);
            if (styleElement.getStyle() == Typeface.BOLD)
                compText.saveTextStyle(EnumText.TEXTBOLD, styleElement.start, styleElement.end);
            else compText.saveTextStyle(EnumText.TEXTITALIC, styleElement.start, styleElement.end);
        }
        for (LimUnderlineSpan underlineElement : underlineSpan) {
            underlineElement.start = tempSpan.getSpanStart(underlineElement);
            underlineElement.end = tempSpan.getSpanEnd(underlineElement);
            compText.saveTextStyle(TEXTUNDERLINE, underlineElement.start, underlineElement.end);
        }
        for (LimForegroundColorSpan colorElement : colorSpan) {
            colorElement.start = tempSpan.getSpanStart(colorElement);
            colorElement.end = tempSpan.getSpanEnd(colorElement);
            compText.saveTextStyle(TEXTCOLOR, colorElement.start, colorElement.end, colorElement.getForegroundColor());
        }
        for (LimAbsoluteSizeSpan sizeElement : sizeSpan)
        {
            sizeElement.start = tempSpan.getSpanStart(sizeElement);
            sizeElement.end = tempSpan.getSpanEnd(sizeElement);
            compText.saveTextStyle(TEXTSIZE, sizeElement.start, sizeElement.end, sizeElement.getSize());
        }
    }

    public void getSpan(LimEditText viewText, CompText compText) {
        if (doStyleJob) return;
        doStyleJob = true;
        ArrayList<LimTextStyle> textStyles = compText.getTextStyle();
        Spannable initSpan = viewText.getText();
        for (LimTextStyle textStyle : textStyles) {
            int start = textStyle.getStart();
            int end = textStyle.getEnd();
            int attr = textStyle.getAttr();
            switch (textStyle.getType()) {
                case TEXTBOLD:
                    setStyle(initSpan, new LimStyleSpan(EnumText.TEXTBOLD.getValue()), start, end);
                    break;
                case TEXTITALIC:
                    setStyle(initSpan, new LimStyleSpan(EnumText.TEXTITALIC.getValue()), start, end);
                    break;
                case TEXTUNDERLINE:
                    setStyle(initSpan, new LimUnderlineSpan(), start, end);
                    break;
                case TEXTSIZE:
                    setStyle(initSpan, new LimAbsoluteSizeSpan(attr), start, end);
                    break;
                case TEXTCOLOR:
                    setStyle(initSpan, new LimForegroundColorSpan(attr), start, end);
                    break;
            }
        }
        doStyleJob = false;
    }

}
