package sechan.intern.lessismore.Lim.Adapater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

import sechan.intern.lessismore.Lim.LimPresenter;
import sechan.intern.lessismore.Model.helpers.ImageHelper;
import sechan.intern.lessismore.R;
import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.CompText;
import sechan.intern.lessismore.components.Enum.EnumComp;
import sechan.intern.lessismore.components.LimEditText;


public class LimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperListener {
    private LimPresenter mPresenter = null;
    private ImageHelper mImageHelper = ImageHelper.getInstance();
    private FocusHelper mFocusHelper;
    private int mPosition = -1;
    private View mComp = null;
    private RecyclerView.ViewHolder mHolder;
    private ArrayList<RecyclerView.ViewHolder> mList = new ArrayList<>();
    private ArrayList<Comp> mPost;
    // Adapter - Presenter 형으로 바꿈

    @Override
    public boolean onItemMove(int fromPosition, int toPosition, RecyclerView.ViewHolder holder) {
        Collections.swap(mPost, fromPosition, toPosition);
        Collections.swap(mList, fromPosition, toPosition);
        mPresenter.hideBtn();

        notifyItemMoved(fromPosition, toPosition);
        mPosition = -1;
        return true;
    }

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

        }

        public void init(final TextHolder holder, int position) {
            final LimEditText compText = holder.compText;

            compText.setText(((CompText) mPost.get(position)).getText());
            mFocusHelper.setBorder(compText, false);
            compText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        mPosition = holder.getAdapterPosition();
                        mPresenter.setCompText(compText);
                        mPresenter.textFocus(true);
                        hasFocused(holder);
                    } else mPresenter.textFocus(false);

                }
            });
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public ArrayList<ImageView> compImage = new ArrayList<>();

        public ImageHolder(View itemView) {
            super(itemView);
            compImage.add((ImageView) itemView.findViewById(R.id.iv_comp0));
            compImage.add((ImageView) itemView.findViewById(R.id.iv_comp1));
            compImage.add((ImageView) itemView.findViewById(R.id.iv_comp2));
            mFocusHelper.setBorder(itemView, false);
        }

        public void init(final ImageHolder holder, final int position) {
            mImageHelper.setCompImage(compImage, ((CompImage) mPost.get(position)).getImagePath());
            for (int i=0;i<((CompImage)mPost.get(position)).getSize();i++) mImageHelper.setImage(i);


            mFocusHelper.setBorder(holder.itemView, false);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hasFocused(holder);
                    //mPosition = holder.getAdapterPosition();
                    if (holder.getAdapterPosition() > 0) {
                        //if (mPresenter.getHolder(holder.getAdapterPosition() - 1) instanceof ImageHolder)
                        if (mList.get(holder.getAdapterPosition() - 1) instanceof ImageHolder)
                            mPresenter.setStripable(true);
                    }
                    if (((CompImage) mPost.get(holder.getAdapterPosition())).getSize() > 1)
                        mPresenter.setDividable(true);

                }
            });
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
        mList.add(position, holder);

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
        /*if (mList.get(mPosition) instanceof ImageHolder) {
            ((ImageHolder) mList.get(mPosition)).compImage.get(1).setVisibility(View.GONE);
            ((ImageHolder) mList.get(mPosition)).compImage.get(2).setVisibility(View.GONE);
        }*/
        //RecyclerView.ViewHolder viewHolder = mPresenter.getHolder(mPosition);
        if (mList.get(mPosition) instanceof ImageHolder) {
            ((ImageHolder) mList.get(mPosition)).compImage.get(1).setVisibility(View.GONE);
            ((ImageHolder) mList.get(mPosition)).compImage.get(2).setVisibility(View.GONE);
        }
            mList.remove(mPosition);
            notifyItemRemoved(mPosition);
            //notifyDataSetChanged();
            mPosition = -1;
        }



    public int getPosition() {
        return mPosition;
    }

    private void hasFocused(RecyclerView.ViewHolder holder) {
        mPresenter.setStripable(false);
        mPresenter.setDividable(false);
        mPosition = holder.getAdapterPosition();
        mHolder = holder;
        mPresenter.setFocused(mPosition);
        if (mComp != null) {
            mFocusHelper.setBorder(mComp, false);
            mComp.clearFocus();
        }
        mComp = mHolder.itemView;
        mFocusHelper.setBorder(mComp, true);
        mPresenter.showMessage(Integer.toString(mPosition));
    }

    public ArrayList<ImageView> getPrevImage() {
        //return ((ImageHolder) mPresenter.getHolder(mPosition - 1)).compImage;
        return ((ImageHolder) mList.get(mPosition - 1)).compImage;
    }

    public ArrayList<ImageView> getImage() {
        return ((ImageHolder) mHolder).compImage;

    }

    private int findPosition() {

        return -1;


    }

    // =====이하로 텍스트 스타일 지정, 가져오기


}