package sechan.intern.lessismore.lim.components;
import java.util.ArrayList;

import sechan.intern.lessismore.lim.components.enumcomp.EnumText;
import sechan.intern.lessismore.lim.components.textstyle.LimTextStyle;

import static sechan.intern.lessismore.lim.components.enumcomp.EnumComp.COMP_TEXT;
public class CompText extends Comp {
    // 규칙 14 접근자 메서드 적용
    String text;
    ArrayList<LimTextStyle> textStyles = new ArrayList<>();
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
    public String getText(){
        return text;
    }
    public ArrayList<LimTextStyle> getTextStyle(){
        return textStyles;

    }
    public void clearStyle(){
        textStyles.clear();

    }

}