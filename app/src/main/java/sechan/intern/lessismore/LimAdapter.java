package sechan.intern.lessismore;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import sechan.intern.lessismore.helpers.Post;

import static sechan.intern.lessismore.LimContract.COMP_IMAGE;
import static sechan.intern.lessismore.LimContract.COMP_TEXT;

public class LimAdapter extends RecyclerView.Adapter<LimAdapter.ViewHolder> {
    private Post mPost;

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
        LinearLayout ll_view = (LinearLayout) holder.mView;
        ll_view.removeAllViews();
        // 다른 종류들을 삭제하고 해당하는 컴포넌트만 남겨놓는다.
        switch (mPost.get(position).Category) {
            case COMP_TEXT: ll_view.addView(holder.edit_comp);
                holder.edit_comp.setText(Integer.toString(position));
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
        public EditText edit_comp;
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

            this.edit_comp = (EditText) v.findViewById(R.id.edit_comp);
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