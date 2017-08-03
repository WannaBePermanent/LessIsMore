package sechan.intern.lessismore.Lim.Adapater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sechan.intern.lessismore.Lim.LimPresenter;
import sechan.intern.lessismore.R;
import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.components.EnumComp;
import sechan.intern.lessismore.components.LimEditText;


public class LimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LimPresenter mPresenter = null;
    private FocusHelper mFocusHelper;
    private int mPosition = -1;
    private View mComp = null;
    private ArrayList<View> mList = new ArrayList<>();
    private ArrayList<Comp> mPost;
    // Adapter - Presenter 형으로 바꿈

    public void setPresenter(LimPresenter presenter) {
        mPresenter = presenter;
    }

    public LimAdapter(ArrayList<Comp> post) {
        mPost = post;
        mFocusHelper = FocusHelper.getInstance();
        // getItemCount 베이스 구현자체에서 수량 가져와서 돌림
    }


    public class TextHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LimEditText compText;

        public TextHolder(View itemView) {
            super(itemView);
            compText = itemView.findViewById(R.id.edit_comp);
            mFocusHelper.setBorder(compText, false);
        }

        public void init(final TextHolder holder, int position) {
            final LimEditText compText = holder.compText;
            compText.setText(((CompText) mPost.get(position)).getText());
            compText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        mPresenter.setCompText(compText);
                        mPresenter.textFocus(true);
                        hasFocused(holder.itemView, holder.getAdapterPosition());
                    } else mPresenter.textFocus(false);

                }
            });
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public ImageView compImage;
        public ImageHolder(View itemView) {
            super(itemView);
            compImage =  itemView.findViewById(R.id.iv_comp0);
            mFocusHelper.setBorder(itemView, false);
        }

        public void init(ImageHolder holder, int position) {
            Glide.with(holder.itemView.getContext().getApplicationContext()).load(((CompImage) mPost.get(position)).ImagePath()[0]).into(holder.compImage);
            setOnClick(holder, holder.compImage);
        }

    }

    public class MapHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View mLLView;

        public MapHolder(View itemView) {
            super(itemView);
            this.mLLView = itemView;


        }
    }

    public void setOnClick(final RecyclerView.ViewHolder holder, final View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition = holder.getAdapterPosition();
                hasFocused(holder.itemView, holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return mPost.get(position).getType().ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (mList.size() > position) {
            mList.remove(position);
        }
        mList.add(position, holder.itemView);

        switch (EnumComp.values()[viewType]) {
            case COMP_TEXT:
                ((TextHolder) holder).init((TextHolder) holder, position);
                break;
            case COMP_IMAGE:
                ((ImageHolder) holder).init((ImageHolder) holder, position);
                break;
            case COMP_MAP:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        int layout;
        RecyclerView.ViewHolder viewHolder = null;
        switch (EnumComp.values()[viewType]) {
            case COMP_TEXT:
                layout = R.layout.layout_comp_text2;
                View textView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new TextHolder(textView);
                break;
            case COMP_IMAGE:
                layout = R.layout.layout_comp_image;
                View imageView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new ImageHolder(imageView);
                break;
            case COMP_MAP:
                layout = R.layout.layout_comp_map;
                View mapView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new MapHolder(mapView);
                break;

        }

        return viewHolder;
    }


    public void saveText() {
        //mPresenter.saveText();
    }

    public void removeComp() {
        //mList.remove(mPosition);
        mList.remove(mPosition);
        notifyItemRemoved(mPosition);
        //notifyDataSetChanged();
        mPosition = -1;
    }


    public int getPosition() {
        return mPosition;
    }

    private void hasFocused(View itemView, int position) {
        mPosition = position;
        mPresenter.setFocused(mPosition);
        if (mComp != null) {
            mFocusHelper.setBorder(mComp, false);
            mComp.clearFocus();
        }
        mComp = itemView;
        mFocusHelper.setBorder(mComp, true);
        mPresenter.showMessage(Integer.toString(mPosition));

    }

    public void imageStrip(int position, int order){
        ImageView imageView2 = mList.get(position).findViewById(R.id.iv_comp1);
        Glide.with(mList.get(position).getContext().getApplicationContext()).load(((CompImage) mPost.get(position)).ImagePath()[1]).into(imageView2);
        imageView2.setVisibility(View.VISIBLE);


    }
    private int findPosition() {

        return -1;


    }

    // =====이하로 텍스트 스타일 지정, 가져오기


}