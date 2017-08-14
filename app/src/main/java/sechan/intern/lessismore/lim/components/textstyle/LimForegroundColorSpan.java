package sechan.intern.lessismore.lim.components.textstyle;


import android.annotation.SuppressLint;
import android.support.annotation.ColorInt;
import android.text.style.ForegroundColorSpan;

@SuppressLint("ParcelCreator")
public class LimForegroundColorSpan extends ForegroundColorSpan {
    public int start;
    public int end;
    public LimForegroundColorSpan(@ColorInt int color) {
        super(color);
    }

}
