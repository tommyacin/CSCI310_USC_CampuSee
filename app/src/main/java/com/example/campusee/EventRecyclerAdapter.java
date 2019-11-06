package com.example.campusee;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder>  {
    private List<Event> mData;
    private LayoutInflater mInflater;
    private EventRecyclerAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public EventRecyclerAdapter(Context context, List<Event> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public EventRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.event_recyclerview_layout, parent, false);
        return new EventRecyclerAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(EventRecyclerAdapter.ViewHolder holder, int position) {
        Event event = mData.get(position);
        holder.event_title_tv.setText(event.title);
        holder.event_building_tv.setText(event.building);
        holder.event_description_tv.setText(event.description);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView event_title_tv;
        TextView event_building_tv;
        TextView event_description_tv;

        ViewHolder(View itemView) {
            super(itemView);
            event_title_tv = itemView.findViewById(R.id.event_title);
            event_building_tv = itemView.findViewById(R.id.event_building);
            event_description_tv = itemView.findViewById(R.id.event_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Event getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(EventRecyclerAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

