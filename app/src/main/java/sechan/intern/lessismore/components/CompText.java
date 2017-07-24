package sechan.intern.lessismore.components;

import java.util.List;


public class CompText extends Comp {

    List<Style> style;

    void setStyle(int start, int end,  int attributes,int mode) {// mode 1 = Color, 2 = Size, 3 = Bold, 4 = Italic, 5 = Underline
        // mode 3~5는 attributes가 필요없음

    }
    // 추후 Builder Pattern 적용가능한지 생각
    class Style {
        int start;
        int end;
        int attributes;
        int mode;
    }


}