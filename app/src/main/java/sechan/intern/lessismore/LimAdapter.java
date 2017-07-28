package sechan.intern.lessismore;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import sechan.intern.lessismore.components.LimConstant;
import sechan.intern.lessismore.components.TextStyle.LimAbsoluteSizeSpan;
import sechan.intern.lessismore.components.TextStyle.LimForegroundColorSpan;
import sechan.intern.lessismore.components.TextStyle.LimStyleSpan;
import sechan.intern.lessismore.components.TextStyle.LimUnderlineSpan;
import sechan.intern.lessismore.helpers.Post;

public class LimAdapter extends RecyclerView.Adapter<LimAdapter.ViewHolder> {
//    private onTextListener textListener;
    private LimPresenter mPresenter = null;
    private LimEditText currentEdit;
    private int start;
    private int end;
    private ArrayList<UnderlineSpan> us = new ArrayList<>();
    Spannable span;
/*
    public interface onTextListener {
        public void onTextCallBack(LimEditText let, int position, int start, int end);

    }

    public void setTextListener(onTextListener o) {
        textListener = o;
    }

    public void onTextCallBack(LimEditText let, int position, int start, int end) {
        textListener.onTextCallBack(let, position, start, end);


    }*/

    // deprecated - 콜백을 사용하지 않고, Presenter를 통해 연결
    // 다른 컴포넌트를 선택했을 때 해당 컴포넌트의 삭제 버튼을 위해서 추가/응용이 필요할 듯
    // Adapter - Presenter 형으로 바꿈

    private Post mPost;
    View v2;

    public void setPresenter(LimPresenter p) {
        mPresenter = p;
    }

    public LimAdapter(Post post) {
        mPost = post; // getItemCount 베이스 구현자체에서 수량 가져와서 돌림
    }

    @Override
    public LimAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comp, parent, false);

        ViewHolder vh = new ViewHolder(v, null);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //업데이트 될 때마다
//          holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        final LimEditText lt;
        final LinearLayout ll_view = (LinearLayout) holder.mView;
        ll_view.removeAllViews();
        // 다른 종류들을 삭제하고 해당하는 컴포넌트만 남겨놓는다.
        switch (mPost.get(position).category) {
            case LimConstant.COMP_TEXT:
                ll_view.addView(holder.edit_comp);

                lt = holder.edit_comp;
                lt.setText(Integer.toString(position));
                lt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            start = lt.getSelectionStart();
                            end = lt.getSelectionEnd();
                            currentEdit = lt;
                            span = lt.getText();
                            getStyle();
                            mPresenter.textFocus(true);
                            //mPresenter.setFocused(position, LimConstant.COMP_TEXT, start, end);

                            //Toast.makeText(ll_view.getContext(),Integer.toString(position)+"번 텍스트 선택" + Integer.toString(lt.getSelectionStart()) + " 부터 " + Integer.toString(lt.getSelectionEnd()), Toast.LENGTH_SHORT).show();
/*                            if (textListener != null) {
                                onTextCallBack(lt, position, start, end);

                            }*/
                            // Presenter에서 Adapter와 View 모두를 관리하게 바꿔서 일부 콜백 함수들을 제거할 수 있었음

                        }
                        else mPresenter.textFocus(false);

                    }
                });
                lt.addOnSelectionChangedListener(new LimEditText.onSelectionChangedListener(
                ) {
                    @Override
                    public void onSelectionChanged(int selStart, int selEnd) {
                        // Toast.makeText(ll_view.getContext(),Integer.toString(position)+"번 텍스트 선택" + Integer.toString(selStart) + " 부터 " + Integer.toString(selEnd), Toast.LENGTH_SHORT).show();
                        start = selStart;
                        end = selEnd;
                        currentEdit = lt;
                        if (span ==null) span =currentEdit.getText();
                        getStyle();

/*                        if (textListener != null) {
                            onTextCallBack(lt, position, selStart, selEnd);

                        }*/
                        //이부분에 텍스트 툴바 상태 설정도 바꿔야함
                    }


                });


                break;
            case LimConstant.COMP_IMAGE:
                ll_view.addView(holder.iv_comp);
                break;

        }


    }

    @Override
    public int getItemCount() {
        //return mDataset.size();
        return mPost.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LimEditText edit_comp;
        //public TextView mTextView;
        public View mView;
        public View mTextView;
        public View iv_comp;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View v, MyCustomEditTextListener myCustomEditTextListener) {
            super(v);
            this.mTextView = v.findViewById(R.id.text_contents);
            this.iv_comp = v.findViewById(R.id.iv_comp);
            this.mView = v;
            this.edit_comp = (LimEditText) v.findViewById(R.id.edit_comp);
            /*
            edit_comp.addOnSelectionChangedListener(new LimEditText.onSelectionChangedListener(
            ) {
                @Override
                public void onSelectionChanged(int selStart, int selEnd) {
                    Toast.makeText(mView.getContext(),"텍스트 선택" + Integer.toString(selStart) + " 부터 " + Integer.toString(selEnd), Toast.LENGTH_SHORT).show();

                }


            });*/
            /*
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edit_comp.addTextChangedListener(myCustomEditTextListener);*/
        }
    }

    public void setBold() {
        preStyle();
        span.setSpan(new LimStyleSpan(Typeface.BOLD),
                start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mPresenter.isBold();
    }
    public void setItalic() {
        preStyle();
        span.setSpan(new LimStyleSpan(Typeface.ITALIC),
                start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mPresenter.isItalic();
    }
    public void setUnderline() {
        preStyle();
        LimUnderlineSpan u = new LimUnderlineSpan();
        us.add(u);
        span.setSpan(u,
                start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mPresenter.isUnderLine();
    }
    public void setColor(int color) {
        preStyle();
        /*span.setSpan(new LimForegroundColorSpan(color),
                start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);*/
        span.setSpan(new LimForegroundColorSpan(color),
                start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 스팬 풀려버림현상

        mPresenter.isColor(color);
    }
    public void setIncSize() {
        preStyle();
        //가져와서 해야함 생각좀해야함

    }
    public void setDecSize() {
        preStyle();

    }


    private void preStyle() {
        //span= currentEdit.getText();
        if (!currentEdit.hasSelection()) {
            currentEdit.getText().insert(currentEdit.getSelectionStart(), " ");
            currentEdit.setSelection(currentEdit.getSelectionStart() - 1,currentEdit.getSelectionStart());
        }
    }
    private void getStyle(){
        LimStyleSpan ss[] = span.getSpans(start,end,LimStyleSpan.class);
        LimUnderlineSpan us[] = span.getSpans(start,end,LimUnderlineSpan.class);
        LimForegroundColorSpan fs[] = span.getSpans(start,end,LimForegroundColorSpan.class);
        LimAbsoluteSizeSpan as[] = span.getSpans(start,end,LimAbsoluteSizeSpan.class);
        mPresenter.clearStyleCheck();
        for (LimStyleSpan s : ss){
            if (s.getStyle()==Typeface.BOLD) {
                mPresenter.isBold();
            }
            if (s.getStyle()==Typeface.ITALIC){
                mPresenter.isItalic();
            }
        }
        for (LimUnderlineSpan u :us){
            mPresenter.isUnderLine();
        }
        for (LimForegroundColorSpan f : fs){
            mPresenter.isColor(f.getForegroundColor());
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            //mDataset[position] = charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}