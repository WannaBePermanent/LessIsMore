package sechan.intern.lessismore.components;


import java.util.ArrayList;

import sechan.intern.lessismore.components.TextStyle.LimTextStyle;

import static sechan.intern.lessismore.components.EnumComp.COMP_TEXT;


public class CompText extends Comp {
    String text;
    ArrayList<LimTextStyle> textStyles = new ArrayList<>();
/*
    ArrayList<LimStyleSpan> ss = new ArrayList<>();
    ArrayList<LimUnderlineSpan> us = new ArrayList<>();
    ArrayList<LimForegroundColorSpan> fs = new ArrayList<>();
    ArrayList<LimAbsoluteSizeSpan> as = new ArrayList<>();
        LimStyleSpan[] ss;
    LimUnderlineSpan[] us;
    LimForegroundColorSpan[] fs;
    LimAbsoluteSizeSpan[] as;
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
    }*/
    public CompText() {
        super(COMP_TEXT);
    }

    public void saveTextStyle(EnumText type, int start, int end){
        textStyles.add(new LimTextStyle(type,start,end));
    }
    public void saveTextStyle(EnumText type, int start, int end, int attr){
        textStyles.add(new LimTextStyle(type,start,end,attr));
    }
    public void saveText(String str){
        this.text = str;
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