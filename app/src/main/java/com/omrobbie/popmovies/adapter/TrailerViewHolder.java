package com.omrobbie.popmovies.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.omrobbie.popmovies.BuildConfig;
import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.model.TrailerItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_trailer) ImageView imgTrailer;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final TrailerItem data) {
        Picasso.with(itemView.getContext())
                .load(BuildConfig.YOUTUBE_URL_IMG + data.getKey() + "/0.jpg")
                .into(imgTrailer);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.YOUTUBE_URL + data.getKey()));
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
