package sechan.intern.lessismore.lim.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

/**
 * Created by Sechan on 2017-08-03.
 */

public class FocusHelper {
    private static FocusHelper instance = null;
    private static final GradientDrawable mFocusedBackground = new GradientDrawable();
    private static final GradientDrawable mDefaultBackground = new GradientDrawable();

    public static FocusHelper getInstance() {
        if (instance == null) {
            instance = new FocusHelper();
            setBorderStyle();
        }
        return instance;
    }

    private static void setBorderStyle() {
        mFocusedBackground.setCornerRadius(10);
        mFocusedBackground.setStroke(4, Color.parseColor("#00C73C"));
        mFocusedBackground.setShape(GradientDrawable.RECTANGLE);
        mDefaultBackground.setCornerRadius(5);
        mDefaultBackground.setStroke(2, Color.LTGRAY);
        mDefaultBackground.setShape(GradientDrawable.RECTANGLE);
    }

    public void setBorder(View view, boolean isFocused) {
        if (isFocused) view.setBackground(mFocusedBackground);
        else view.setBackground(mDefaultBackground);
    }


}
