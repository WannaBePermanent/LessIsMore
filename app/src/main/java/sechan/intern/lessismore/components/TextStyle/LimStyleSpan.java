package sechan.intern.lessismore.components.TextStyle;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.text.style.StyleSpan;

@SuppressLint("ParcelCreator")
public class LimStyleSpan extends StyleSpan {

    int start;
    int end;


    public LimStyleSpan(int style) {
        super(style);
    }

    public LimStyleSpan (Parcel src) {
        super(src);
    }
}

