package sechan.intern.lessismore.components;

import sechan.intern.lessismore.components.TextStyle.LimAbsoluteSizeSpan;
import sechan.intern.lessismore.components.TextStyle.LimForegroundColorSpan;
import sechan.intern.lessismore.components.TextStyle.LimStyleSpan;
import sechan.intern.lessismore.components.TextStyle.LimUnderlineSpan;

import static sechan.intern.lessismore.components.LimConstant.COMP_TEXT;

public class CompText extends Comp {
    String text;

/*
    ArrayList<LimStyleSpan> ss = new ArrayList<>();
    ArrayList<LimUnderlineSpan> us = new ArrayList<>();
    ArrayList<LimForegroundColorSpan> fs = new ArrayList<>();
    ArrayList<LimAbsoluteSizeSpan> as = new ArrayList<>();
*/
    LimStyleSpan[] ss;
    LimUnderlineSpan[] us;
    LimForegroundColorSpan[] fs;
    LimAbsoluteSizeSpan[] as;

    public CompText() {
        category = COMP_TEXT;
    }
    public void setStyleSpan(LimStyleSpan[] lss){
        ss=lss;
    }
    public void setUnderlineSpan(LimUnderlineSpan[] lus){
        us=lus;
    }
    public void setForegroudColorSpan(LimForegroundColorSpan[] lfs){
        fs=lfs;
    }
    public void setAbsoluteSizeSpan (LimAbsoluteSizeSpan[] lass){
        as=lass;
    }


  /*  List<Style> style;

    void setStyle(int start, int end, int attributes, int mode) {// mode 1 = Color, 2 = Size, 3 = Bold, 4 = Italic, 5 = Underline
        // mode 3~5는 attributes가 필요없음

    }

    // 추후 Builder Pattern 적용가능한지 생각
    class Style {
        int start;
        int end;
        int attributes;
        int mode;
    }
*/
}