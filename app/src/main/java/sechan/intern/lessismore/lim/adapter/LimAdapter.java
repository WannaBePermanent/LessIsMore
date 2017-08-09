package sechan.intern.lessismore.lim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

import sechan.intern.lessismore.R;
import sechan.intern.lessismore.lim.LimPresenter;
import sechan.intern.lessismore.lim.components.Comp;
import sechan.intern.lessismore.lim.components.CompImage;
import sechan.intern.lessismore.lim.components.CompMap;
import sechan.intern.lessismore.lim.components.CompText;
import sechan.intern.lessismore.lim.components.LimEditText;
import sechan.intern.lessismore.lim.components.enumcomp.EnumComp;


public class LimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperListener {
    private LimPresenter mPresenter = null;
    private int mPosition = -1;
    private View mCompView = null;
    private ArrayList<Comp> mPost;
    private static final int MAX_IMAGE = 3;
    private static final String clientId =  "DDDCTG3Meo6SYF6duEr6";
    private static final String packageName = "sechan.intern.lessismore";

    // Adapter - Presenter 형으로 바꿈

    @Override
    public boolean onItemMove(int fromPosition, int toPosition, RecyclerView.ViewHolder holder) {
        Collections.swap(mPost, fromPosition, toPosition);
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
    }


    public class TextHolder extends LimHolder {
        public LimEditText viewText;
        public TextHolder(View itemView) {
            super(itemView);
            viewText = itemView.findViewById(R.id.edit_comp);

        }

        @Override
        public void init(final LimHolder holder, int position) {
            final CompText compText = ((CompText) mPost.get(position));
            viewText.setText(compText.getText());
            mPresenter.getSpan(viewText, compText);
            viewText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        //mPosition = holder.getAdapterPosition();
                        hasFocused(holder);
                        mPresenter.setCompText(viewText);
                        mPresenter.textFocus(true);

                    } else {
                        mPresenter.textFocus(false);
                       mPresenter.saveText(compText);
                    }

                }
            });
        }
    }

    public class ImageHolder extends LimHolder {
        public ArrayList<ImageView> viewImage = new ArrayList<>();

        public ImageHolder(View itemView) {
            super(itemView);
            viewImage.add((ImageView) itemView.findViewById(R.id.iv_comp0));
            viewImage.add((ImageView) itemView.findViewById(R.id.iv_comp1));
            viewImage.add((ImageView) itemView.findViewById(R.id.iv_comp2));

        }

        @Override
        public void init(final LimHolder holder, final int position) {
            CompImage compImage = (CompImage) mPost.get(position);
            for (int i = 0; i < MAX_IMAGE; i++) {
                viewImage.get(i).setVisibility(View.GONE);
            }
            for (int i = 0; i < compImage.getSize(); i++) {
                viewImage.get(i).setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext().getApplicationContext()).load(compImage.getImagePath(i)).into(viewImage.get(i));

            }



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hasFocused(holder);
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition > 0) {
                        if (mPost.get(currentPosition - 1) instanceof CompImage)
                            mPresenter.setStripable(true);
                    }
                    if (((CompImage) mPost.get(currentPosition)).getSize() > 1)
                        mPresenter.setDividable(true);

                }
            });
        }

    }

    public class MapHolder extends LimHolder {

        public ImageView mapImage;
        public TextView mapTitle,mapAddress;
        public MapHolder(View itemView) {
            super(itemView);

            mapImage = itemView.findViewById(R.id.mapImage);
            mapTitle = itemView.findViewById(R.id.mapTitle2);
            mapAddress = itemView.findViewById(R.id.mapAddress2);
        }

        @Override
        public void init(final LimHolder holder, final int position) {

            CompMap compMap = (CompMap) mPost.get(position);
            mapTitle.setText(compMap.getTitle());
            mapAddress.setText(compMap.getAddress());
            String mapURL = "https://openapi.naver.com/v1/map/staticmap.bin?clientId=" + clientId+ "&url=" + packageName + "&crs=NHN:128&level=11&w=300&h=400&baselayer=default&markers="+compMap.getPointString() + "&center=" + compMap.getPointString();
            Glide.with(itemView.getContext()).load(mapURL).into(mapImage);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hasFocused(holder);
                }});
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mPost.get(position).getType().ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setSelected(false);
        holder.itemView.clearFocus();
        mPosition = -1;
        ((LimHolder) holder).init((LimHolder) holder, position);

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
                layout = R.layout.layout_comp_text;
                View textView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new TextHolder(textView);
                break;
            case COMP_IMAGE:
                layout = R.layout.layout_comp_image;
                View imageView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new ImageHolder(imageView);
                break;
            case COMP_MAP:
                layout = R.layout.layout_comp_map2;
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
        notifyItemRemoved(mPosition);
        //notifyDataSetChanged();
        mPosition = -1;
    }


    public int getPosition() {
        return mPosition;
    }

    private void hasFocused(RecyclerView.ViewHolder holder) {
        if (mCompView == holder.itemView) return;
        mPresenter.setStripable(false);
        mPresenter.setDividable(false);
        mPosition = holder.getAdapterPosition();
        mPresenter.setFocused();
        if (mCompView != null) {
            mCompView.setSelected(false);
            mCompView.clearFocus();
        }
        mCompView = holder.itemView;
        mCompView.setSelected(true);
        mPresenter.showMessage(Integer.toString(mPosition));
    }


}