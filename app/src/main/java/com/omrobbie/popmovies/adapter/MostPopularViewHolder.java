package com.omrobbie.popmovies.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.omrobbie.popmovies.BuildConfig;
import com.squareup.picasso.Picasso;

import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.DetailActivity;
import com.omrobbie.popmovies.model.MovieItem;

public class MostPopularViewHolder extends RecyclerView.ViewHolder {

    ImageView poster;

    public MostPopularViewHolder(View itemView) {
        super(itemView);
        poster = (ImageView) itemView.findViewById(R.id.img_movie);
    }

    public void bind(final MovieItem data) {
        Picasso.with(itemView.getContext())
                .load(BuildConfig.BASE_URL_IMG + "w185" + data.getPosterPath())
                .into(poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(itemView.getContext(), DetailActivity.class);
                detail.putExtra("movie", new Gson().toJson(data));
                itemView.getContext().startActivity(detail);
            }
        });
    }
}
