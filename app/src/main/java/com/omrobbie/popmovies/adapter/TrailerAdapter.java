package com.omrobbie.popmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.model.TrailerItem;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private List<TrailerItem> list = new ArrayList<>();

    public TrailerAdapter() {
    }

    public void replaceAll(List<TrailerItem> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.detail_trailer_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
