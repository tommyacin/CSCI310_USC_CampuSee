package com.example.campusee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomepageRecyclerAdapter extends RecyclerView.Adapter<HomepageRecyclerAdapter.ViewHolder>  {
    private List<Publisher> mData;
    private LayoutInflater mInflater;
    private HomepageRecyclerAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public HomepageRecyclerAdapter(Context context, List<Publisher> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public HomepageRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.publisher_name_layout, parent, false);
        return new HomepageRecyclerAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(HomepageRecyclerAdapter.ViewHolder holder, int position) {
        Publisher pub = mData.get(position);
        holder.pub_name_tv.setText(pub.name);
        holder.pub_building_tv.setText(pub.building);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pub_name_tv;
        TextView pub_building_tv;

        ViewHolder(View itemView) {
            super(itemView);
            pub_name_tv = itemView.findViewById(R.id.publisherName);
            pub_building_tv = itemView.findViewById(R.id.publisherBuildingName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Publisher getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(HomepageRecyclerAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
