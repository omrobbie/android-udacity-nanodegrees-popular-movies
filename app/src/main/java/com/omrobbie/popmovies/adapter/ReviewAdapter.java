package com.omrobbie.popmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.model.ReviewItem;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<ReviewItem> list = new ArrayList<>();

    public ReviewAdapter() {
    }

    public void replaceAll(List<ReviewItem> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.detail_review_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
