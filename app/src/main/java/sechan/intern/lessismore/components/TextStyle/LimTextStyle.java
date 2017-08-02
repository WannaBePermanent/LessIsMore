package sechan.intern.lessismore.components.TextStyle;

import sechan.intern.lessismore.components.EnumText;

/**
 * Created by Sechan on 2017-08-01.
 */

public class LimTextStyle {
    EnumText type;
    int start;
    int end;
    int attr;

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
