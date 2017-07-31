package sechan.intern.lessismore.components.TextStyle;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.text.style.ForegroundColorSpan;

@SuppressLint("ParcelCreator")
public class LimForegroundColorSpan extends ForegroundColorSpan {
    public int start;
    public int end;
    public LimForegroundColorSpan(@ColorInt int color) {
        super(color);
    }

    public LimForegroundColorSpan(Parcel src) {
        super(src);
    }
}
