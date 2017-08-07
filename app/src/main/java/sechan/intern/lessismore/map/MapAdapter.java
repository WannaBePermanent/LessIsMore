package sechan.intern.lessismore.map;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sechan.intern.lessismore.R;

/**
 * Created by Sechan on 2017-08-07.
 */

public class MapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MapDetail> mapList;
    public MapAdapter(ArrayList<MapDetail> list) {
        mapList = list;

        // getItemCount 베이스 구현자체에서 수량 가져와서 돌림
    }

    public class MapListHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvAddress;
        public MapListHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View mapListView = LayoutInflater.from(parent.getContext()).inflate( R.layout.layout_map_list, parent, false);
        viewHolder = new MapListHolder(mapListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MapListHolder mapHolder = (MapListHolder) holder;
        mapHolder.tvTitle.setText(mapList.get(position).title.replace("<b>","").replace("</b>",""));
        mapHolder.tvAddress.setText(mapList.get(position).address);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("title",mapHolder.tvTitle.getText().toString());
                intent.putExtra("address",mapHolder.tvAddress.getText().toString());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }
}
