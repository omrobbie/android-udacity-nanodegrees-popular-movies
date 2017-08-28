package com.omrobbie.popmovies.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.omrobbie.popmovies.BuildConfig;
import com.omrobbie.popmovies.DetailActivity;
import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.model.MovieItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_movie) ImageView imgMovie;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final MovieItem data) {
        Picasso.with(itemView.getContext())
                .load(BuildConfig.BASE_URL_IMG + "w185" + data.getPosterPath())
                .resize(185, 278)
                .centerCrop()
                .into(imgMovie);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("movie", new Gson().toJson(data));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) itemView.getContext(), imgMovie, "image");
                    itemView.getContext().startActivity(intent, activityOptionsCompat.toBundle());
                }
                else itemView.getContext().startActivity(intent);
            }
        });
    }
}
