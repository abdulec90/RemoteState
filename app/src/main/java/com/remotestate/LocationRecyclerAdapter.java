package com.remotestate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.remotestate.database.LocationTable;

import java.util.List;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.LocationViewHolder> {

    private Context context;
    private List<LocationTable> locationList;

    public LocationRecyclerAdapter(Context context, List<LocationTable> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.loation_recycler_items, null, false);
        return new LocationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {

        LocationTable locationTable = locationList.get(position);
        holder.latTxt.setText("Latitude : " + locationTable.getLatitude());
        holder.lngTxt.setText("Longitude : " + locationTable.getLongitude());

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView latTxt;
        TextView lngTxt;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            latTxt = (TextView) itemView.findViewById(R.id.latTxt);
            lngTxt = (TextView) itemView.findViewById(R.id.lngTxt);
        }
    }


}
