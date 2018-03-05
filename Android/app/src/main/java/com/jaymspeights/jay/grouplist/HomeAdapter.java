package com.jaymspeights.jay.grouplist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jay on 3/1/2018.
 */
public class HomeAdapter extends RecyclerView.Adapter{

    public ArrayList<String> data;

    HomeAdapter(String[] input) {
        data = new ArrayList<String>();
        for (int i = 0; i < input.length; i++) {
            data.add(input[i]);
        }
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
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list, parent, false);
        ViewHolder vh = new ViewHolder(layout);
        return vh;
    }

    /**
     * called when adapter needs to populate the viewholder with data
     * csv is split and put into the respective textviews
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String[] elem = data.get(position).split("_");
        ((TextView)((ViewHolder)holder).layout.getChildAt(0)).setText(elem[0]);
        ((TextView)((ViewHolder)holder).layout.getChildAt(1)).setText(elem[1]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

