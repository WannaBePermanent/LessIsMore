package sechan.intern.lessismore.lim.components.textstyle;

import android.annotation.SuppressLint;
import android.text.style.AbsoluteSizeSpan;

/**
 * Created by Sechan on 2017-07-28.
 */

@SuppressLint("ParcelCreator")
public class LimAbsoluteSizeSpan extends AbsoluteSizeSpan {
    public int start;
    public int end;
    public LimAbsoluteSizeSpan(int size) {
        super(size);
    }


}
