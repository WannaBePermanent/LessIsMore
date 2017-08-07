package sechan.intern.lessismore.lim.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Sechan on 2017-08-04.
 */

public interface ItemTouchHelperListener {
    boolean onItemMove(int fromPosition, int toPosition,RecyclerView.ViewHolder holder);
}
