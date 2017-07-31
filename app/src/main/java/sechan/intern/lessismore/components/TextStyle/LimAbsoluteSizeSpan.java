package sechan.intern.lessismore.components.TextStyle;

import android.annotation.SuppressLint;
import android.os.Parcel;
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

    public LimAbsoluteSizeSpan(int size, boolean dip) {
        super(size, dip);
    }

    public LimAbsoluteSizeSpan(Parcel src) {
        super(src);
    }
}
