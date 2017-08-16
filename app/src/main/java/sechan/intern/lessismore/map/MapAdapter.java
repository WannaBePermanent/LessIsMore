package sechan.intern.lessismore.map;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sechan.intern.lessismore.R;
import sechan.intern.lessismore.map.mapclass.MapItem;

/**
 * Created by Sechan on 2017-08-07.
 */

public class MapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MapItem> mapList;
    private MapListActivity activity;
    public MapAdapter(ArrayList<MapItem> list, MapListActivity activity) {
        this.activity=activity;
        mapList = list;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MapListHolder mapHolder = (MapListHolder) holder;
        MapItem mapItem = mapList.get(position);
        mapItem.setTitle(mapItem.getTitle().replace("<b>","").replace("</b>",""));
        mapHolder.tvTitle.setText(mapItem.getTitle());
        mapHolder.tvAddress.setText(mapItem.getRoadAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapItem mapItem = mapList.get(position);
                Intent intent = new Intent();
                intent.putExtra("title",mapItem.getTitle());
                intent.putExtra("address",mapItem.getRoadAddress());
                intent.putExtra("mapx",mapItem.getMapx());
                intent.putExtra("mapy",mapItem.getMapy());
                activity.setResult(Activity.RESULT_OK,intent);
                activity.finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }
}
