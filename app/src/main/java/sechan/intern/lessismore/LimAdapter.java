package sechan.intern.lessismore;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import sechan.intern.lessismore.helpers.Post;

import static sechan.intern.lessismore.LimContract.COMP_IMAGE;
import static sechan.intern.lessismore.LimContract.COMP_TEXT;

public class LimAdapter extends RecyclerView.Adapter<LimAdapter.ViewHolder> {
    private onTextListener textListener;
    public interface onTextListener {
        public void onTextCallBack(int position, int start, int end);

    }
    public void setTextListener(onTextListener o){
        textListener = o;
    }
    public void onTextCallBack(int position, int start, int end){
        textListener.onTextCallBack(position, start, end);


    }

    // 뷰(메인액티비티)를 위한 콜백함수 구현, 텍스트가 바뀌거나 다른 텍스트 컴포넌트가 선택되었을 때 호출
    // 현재는 텍스트 설정 창을 위해서만 구현
    // 다른 컴포넌트를 선택했을 때 해당 컴포넌트의 삭제 버튼을 위해서 추가/응용이 필요할 듯
    // Adapter - Presenter 이을지도 생각해볼것

    private Post mPost;
    View v2;
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
        // update MyCustomEditTextListener every time we bind a new item
//          holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        final LimEditText lt;
        final LinearLayout ll_view = (LinearLayout) holder.mView;
        ll_view.removeAllViews();
        // 다른 종류들을 삭제하고 해당하는 컴포넌트만 남겨놓는다.
        switch (mPost.get(position).Category) {
            case COMP_TEXT: ll_view.addView(holder.edit_comp);
                lt = holder.edit_comp;

                lt.setText(Integer.toString(position));

                lt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            //Toast.makeText(ll_view.getContext(),Integer.toString(position)+"번 텍스트 선택" + Integer.toString(lt.getSelectionStart()) + " 부터 " + Integer.toString(lt.getSelectionEnd()), Toast.LENGTH_SHORT).show();
                            if(textListener != null){
                                onTextCallBack(position,lt.getSelectionStart(),lt.getSelectionEnd());
                            }
                            //이부분에 텍스트 툴바 상태 설정도 바꿔야함
                            //어댑터를 뷰 종속이라 보고, 뷰 콜백으로 가서 뷰 콜백 단에서 프리젠터 호출하는 방법도 고려
                        }

                    }
                });
               lt.addOnSelectionChangedListener(new LimEditText.onSelectionChangedListener(
                ) {
                    @Override
                    public void onSelectionChanged(int selStart, int selEnd) {
                       // Toast.makeText(ll_view.getContext(),Integer.toString(position)+"번 텍스트 선택" + Integer.toString(selStart) + " 부터 " + Integer.toString(selEnd), Toast.LENGTH_SHORT).show();

                        if(textListener != null){
                            onTextCallBack(position,selStart,selEnd);
                        }
                        //이부분에 텍스트 툴바 상태 설정도 바꿔야함
                    }


                });



                break;
            case COMP_IMAGE: ll_view.addView(holder.iv_comp);
                break;

        }


    }

    @Override
    public int getItemCount() {
        //return mDataset.size();
        return mPost.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
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