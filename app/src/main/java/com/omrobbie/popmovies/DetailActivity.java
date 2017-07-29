package com.omrobbie.popmovies;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.omrobbie.popmovies.adapter.TrailerAdapter;
import com.omrobbie.popmovies.model.MovieItem;
import com.omrobbie.popmovies.model.TrailerItem;
import com.omrobbie.popmovies.model.TrailerResponse;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.releaseDate) TextView releaseDate;
    @BindView(R.id.rating) TextView rating;
    @BindView(R.id.synopsis) TextView synopsis;
    @BindView(R.id.parentDetail) CoordinatorLayout parentDetail;
    @BindViews({
            R.id.star1,
            R.id.star2,
            R.id.star3,
            R.id.star4,
            R.id.star5
    }) List<ImageView> ratingStarViews;

    @BindView(R.id.rv_trailer) RecyclerView rv_trailer;

    private Gson gson = new Gson();
    private long movieID;

    private TrailerAdapter adapter;
    private LinearLayoutManager layoutManager;

    private Parcelable layoutManagerSavedState;
    private Call<TrailerResponse> responseCall;
    private ArrayList<TrailerItem> trailers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent() != null) {
            String json = getIntent().getStringExtra("movie");
            MovieItem movie = gson.fromJson(json, MovieItem.class);

            parentDetail.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(movie.getOriginalTitle());
            Picasso.with(DetailActivity.this)
                    .load(BuildConfig.BASE_URL_IMG + "w300" + movie.getBackdropPath())
                    .into(backdrop);

            Picasso.with(DetailActivity.this)
                    .load(BuildConfig.BASE_URL_IMG + "w185" + movie.getPosterPath())
                    .into(poster);

            releaseDate.setText(getDateString(movie.getReleaseDate()));
            rating.setText(String.valueOf(movie.getVoteAverage()));
            synopsis.setText(movie.getOverview());

            double userRating = movie.getVoteAverage() / 2;
            int integerPart = (int) userRating;

            // Fill stars
            for (int i = 0; i < integerPart; i++) {
                ratingStarViews.get(i).setImageResource(R.drawable.ic_star_black_24dp);
            }

            // Fill half star
            if (Math.round(userRating) > integerPart) {
                ratingStarViews.get(integerPart).setImageResource(
                        R.drawable.ic_star_half_black_24dp);
            }

            // Load trailer
            layoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
            adapter = new TrailerAdapter();
            rv_trailer.setLayoutManager(layoutManager);
            rv_trailer.setAdapter(adapter);

            loadData();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("movie", getIntent().getStringExtra("movie"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DetailActivity.this.finish();
    }

    String getDateString(String date) {
        String result = "";
        DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat("dd MMMM yyyy");
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void loadData() {

        String json = getIntent().getStringExtra("movie");
        movieID = gson.fromJson(json, MovieItem.class).getId();

        responseCall = App.getRestClient()
                .getService()
                .getTrailer(movieID);
        
        responseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if(response.isSuccessful()) {
                    List<TrailerItem> data = new ArrayList<TrailerItem>();
                    for (TrailerItem trailer : response.body().getResults()) {
                        if (trailer.getType().equals("Trailer")) data.add(trailer);
                    }

                    trailers.clear();
                    for (TrailerItem trailer : data) {
                        trailers.add(trailer);
                    }
                    adapter.replaceAll(data);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
            }
        });
    }
}
