package sechan.intern.lessismore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.EnumComp;


public class LimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext = null;
    private LimPresenter mPresenter = null;
    public static final GradientDrawable mGrad = new GradientDrawable();
    private static final GradientDrawable mTextGrad = new GradientDrawable();
    private RecyclerView rv;
    private int mPosition = -1;
    private View mComp=null;
    private ArrayList<View> mList = new ArrayList<>();
    private final TextHelper textHelper = TextHelper.getInstance();
    private final LimAdapter mAdapter = this;
    // Adapter - Presenter 형으로 바꿈

    private ArrayList<Comp> mPost;

    public void setPresenter(LimPresenter presenter) {
        mPresenter = presenter;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public LimAdapter(ArrayList<Comp> post) {
        mPost = post;
        mGrad.setCornerRadius(10);
        mGrad.setStroke(4, Color.parseColor("#00C73C"));
        mGrad.setShape(GradientDrawable.RECTANGLE);
        mTextGrad.setCornerRadius(5);
        mTextGrad.setStroke(2, Color.LTGRAY);
        mTextGrad.setShape(GradientDrawable.RECTANGLE);
        //선택시 해당되는 스트록을 위한 그래디언트 생성

        // getItemCount 베이스 구현자체에서 수량 가져와서 돌림
    }


    public class TextHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LimEditText editComp;



        public TextHolder(View itemView) {
            super(itemView);
            editComp = itemView.findViewById(R.id.edit_comp);
         //   mList.add(editComp);
            //if (!mList.contains(editComp)) mList.add(editComp);
            editComp.setBackground(mTextGrad);
            //mList.add(editComp);
        }

        public void init(final TextHolder holder, int position) {
            final LimEditText textComp = holder.editComp;
           // if (!mList.contains(holder.editComp)) mList.add(position, holder.editComp);
           // mList.add(position, holder.editComp);
            //textComp.setText(Integer.toString(position));
            //setDefaultTextSize(lt.getTextSize());
            //setOnClick(holder.editComp, position);
            textComp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        textHelper.setCompText(textComp);
                        mPresenter.textFocus(true);
                        hasFocused(holder);
                        mPresenter.showMessage(Integer.toString(holder.getAdapterPosition()));
                    } else mPresenter.textFocus(false);

                }
            });
            textComp.addOnSelectionChangedListener(new LimEditText.onSelectionChangedListener(
            ) {
                @Override
                public void onSelectionChanged(int selStart, int selEnd) {
                    textHelper.setCompText(textComp);
                }


            });


        }

    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View ivComp;

        public ImageHolder(View itemView) {
            super(itemView);
            this.ivComp = itemView.findViewById(R.id.iv_comp);
          //  mList.add(ivComp);
            //if (!mList.contains(ivComp)) mList.add(ivComp);

        }

        public void init(ImageHolder holder, int position) {
//            if (!mList.contains(holder.ivComp)) mList.add(position, holder.ivComp);
            //mList.add(position, holder.ivComp);
            Glide.with(mContext).load(((CompImage) mPost.get(position)).ImagePath()).into((ImageView) holder.ivComp);
            setOnClick(holder,holder.ivComp);
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
        switch (EnumComp.values()[viewType]) {
            case COMP_TEXT:
                ((TextHolder) holder).init((TextHolder) holder, position);

  /*              Call call=(Call)callSMSFeed.get(position);
                ((TextHolder)holder).showCallDetails(call);  */
                break;
            case COMP_IMAGE:
                ((ImageHolder) holder).init((ImageHolder) holder, position);
/*                SMS sms=(SMS)callSMSFeed.get(position);
                ((SMSViewHolder)holder).showSmsDetails(sms);*/
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
                layout = R.layout.layout_comp_image2;
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


    public void setOnClick(final RecyclerView.ViewHolder holder, final View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition = holder.getAdapterPosition();
                hasFocused(holder);
                mPresenter.showMessage(Integer.toString(mPosition));
            }
        });

    }

    public void saveText() {
        textHelper.saveText(mList);
    }

    public void removeComp() {
        //mList.remove(mPosition);
        notifyItemRemoved(mPosition);
        //notifyDataSetChanged();
        mPosition = -1;
    }


    public int getPosition() {
        return mPosition;
    }

    private void hasFocused(RecyclerView.ViewHolder holder) {
        mPosition = holder.getAdapterPosition();
        mPresenter.setFocused(mPosition);
        if (mComp !=null) mComp.setBackground(mTextGrad);
        mComp = holder.itemView;
        mComp.setBackground(mGrad);


        //holder.itemView.setBackground(mGrad);
//        holder.setIsRecyclable(false);


/*   for (int viewIndex = 0; viewIndex < mList.size(); viewIndex++) {
            View compView = mList.get(viewIndex);
            if (mPosition == viewIndex) compView.setBackground(mGrad); //holder.itemView.setBackground(mGrad);
            else {
                compView.setBackground(mTextGrad);
                compView.clearFocus();
            }
        }
        */



}

    private int findPosition() {

        return -1;


    }

    // =====이하로 텍스트 스타일 지정, 가져오기


}