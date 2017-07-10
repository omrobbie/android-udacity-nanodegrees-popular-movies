package com.omrobbie.popmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.model.MovieItem;

public class MovieListAdapter extends RecyclerView.Adapter<MostPopularViewHolder> {

    private List<MovieItem> list = new ArrayList<>();

    public MovieListAdapter() {
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
    public MostPopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MostPopularViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_most_popular, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(MostPopularViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
