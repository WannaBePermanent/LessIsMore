package sechan.intern.lessismore.lim.components.textstyle;

import sechan.intern.lessismore.lim.components.enumcomp.EnumText;

/**
 * Created by Sechan on 2017-08-01.
 */

public class LimTextStyle {
    EnumText type;
    int start;
    int end;
    int attr;

    public EnumText getType() {
        return type;
    }

    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }
    public int getAttr() {
        return attr;
    }


    public LimTextStyle(EnumText type, int start, int end, int attr) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.attr = attr;
    }

    public LimTextStyle(EnumText type, int start, int end) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.attr = 0;

    }
}
