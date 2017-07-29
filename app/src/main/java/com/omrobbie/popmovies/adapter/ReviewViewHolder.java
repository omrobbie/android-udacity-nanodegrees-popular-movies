package com.omrobbie.popmovies.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.omrobbie.popmovies.R;
import com.omrobbie.popmovies.model.ReviewItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_author) TextView author;
    @BindView(R.id.review_content) TextView content;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final ReviewItem data) {
        author.setText(data.getAuthor());
        content.setText(data.getContent());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
