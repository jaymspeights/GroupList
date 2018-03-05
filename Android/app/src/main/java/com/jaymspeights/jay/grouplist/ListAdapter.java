package com.jaymspeights.jay.grouplist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jay on 3/1/2018.
 */
public class ListAdapter extends RecyclerView.Adapter{

    String[] data;

    ListAdapter(String[] data) {
        this.data = data;

    }


    /**
     * custom viewholder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public ViewHolder(LinearLayout l) {
            super(l);
            layout = l;
        }
    }

    /**
     * called when the adapter needs to create a new view
     * inflates the view from view_holder.xml
     *
     * view is linear layout with 2 TextViews
     * one for firstname and one for lastname
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(layout);
        return vh;
    }

    /**
     * called when adapter needs to populate the viewholder with data
     * csv is split and put into the respective textviews
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView)((ViewHolder)holder).layout.getChildAt(0)).setText(data[position].split("_")[1]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}

