package com.omrobbie.popmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.model.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<MovieItem> list = new ArrayList<>();

    public MovieAdapter() {
    }

    public void replaceAll(List<MovieItem> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void updateData(List<MovieItem> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_main_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
