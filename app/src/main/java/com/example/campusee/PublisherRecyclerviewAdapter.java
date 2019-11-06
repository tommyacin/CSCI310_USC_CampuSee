package com.example.campusee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PublisherRecyclerviewAdapter extends RecyclerView.Adapter<PublisherRecyclerviewAdapter.ViewHolder> {
    private List<Notification> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public PublisherRecyclerviewAdapter(Context context, List<Notification> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.publisher_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notif = mData.get(position);
        holder.notif_title_tv.setText(notif.title);
        holder.notif_description_tv.setText(notif.description);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView notif_title_tv;
        TextView notif_description_tv;

        ViewHolder(View itemView) {
            super(itemView);
            notif_title_tv = itemView.findViewById(R.id.notif_title);
            notif_description_tv = itemView.findViewById(R.id.notif_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Notification getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
