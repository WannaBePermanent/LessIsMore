package sechan.intern.lessismore.lim.components.textstyle;


import android.annotation.SuppressLint;
import android.text.style.StyleSpan;

@SuppressLint("ParcelCreator")
public class LimStyleSpan extends StyleSpan {

    public int start;
    public int end;


    public LimStyleSpan(int style) {
        super(style);
    }

}


