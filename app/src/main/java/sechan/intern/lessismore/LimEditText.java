package sechan.intern.lessismore;


import android.content.Context;
import android.util.AttributeSet;

public class LimEditText extends android.support.v7.widget.AppCompatEditText {
    private onSelectionChangedListener listeners;
    public interface onSelectionChangedListener {

        public void onSelectionChanged(int selStart, int selEnd);
    }
    public LimEditText(Context context) {
        super(context);
        // listeners=null;

    }

    public LimEditText(Context context, AttributeSet attrs){
        super(context, attrs);
        //listeners=null;
    }
    public LimEditText(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

    }

    public void addOnSelectionChangedListener(onSelectionChangedListener o){
        listeners=o;
    }
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if(listeners!=null)    listeners.onSelectionChanged(selStart,selEnd);
    }




}