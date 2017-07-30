package com.omrobbie.popmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.omrobbie.popmovies.adapter.ReviewAdapter;
import com.omrobbie.popmovies.adapter.TrailerAdapter;
import com.omrobbie.popmovies.database.FavoriteContract;
import com.omrobbie.popmovies.model.MovieItem;
import com.omrobbie.popmovies.model.ReviewItem;
import com.omrobbie.popmovies.model.ReviewResponse;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.releaseDate)
    TextView releaseDate;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.synopsis)
    TextView synopsis;
    @BindView(R.id.parentDetail)
    CoordinatorLayout parentDetail;
    @BindViews({
            R.id.star1,
            R.id.star2,
            R.id.star3,
            R.id.star4,
            R.id.star5
    })
    List<ImageView> ratingStarViews;

    @BindView(R.id.rv_trailer)
    RecyclerView rvTrailer;
    @BindView(R.id.rv_review)
    RecyclerView rvReview;

    @BindView(R.id.favorite)
    ImageView favorite;

    private Gson gson = new Gson();
    private long movieID;
    private LinearLayoutManager layoutManager;

    private ArrayList<TrailerItem> trailerItems = new ArrayList<>();
    private Call<TrailerResponse> trailerResponseCall;
    private TrailerAdapter trailerAdapter;

    private ArrayList<ReviewItem> reviewItems = new ArrayList<>();
    private Call<ReviewResponse> reviewResponseCall;
    private ReviewAdapter reviewAdapter;

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String json = getIntent().getStringExtra("movie");
        final MovieItem movieItem = gson.fromJson(json, MovieItem.class);

        parentDetail.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(movieItem.getOriginalTitle());
        Picasso.with(DetailActivity.this)
                .load(BuildConfig.BASE_URL_IMG + "w300" + movieItem.getBackdropPath())
                .into(backdrop);

        Picasso.with(DetailActivity.this)
                .load(BuildConfig.BASE_URL_IMG + "w185" + movieItem.getPosterPath())
                .into(poster);

        releaseDate.setText(getDateString(movieItem.getReleaseDate()));
        rating.setText(String.valueOf(movieItem.getVoteAverage()));
        synopsis.setText(movieItem.getOverview());

        double userRating = movieItem.getVoteAverage() / 2;
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

        // Load trailer list
        layoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        trailerAdapter = new TrailerAdapter();
        rvTrailer.setLayoutManager(layoutManager);
        rvTrailer.setAdapter(trailerAdapter);

        loadDataTrailer(movieItem.getId());

        // Load review list
        reviewAdapter = new ReviewAdapter();
        rvReview.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        rvReview.setAdapter(reviewAdapter);

        loadDataReview(movieItem.getId());

        // Load favorite list
        favorite.setTag(R.drawable.ic_favorite_black_24dp);
        loadDataFavorite(movieItem.getId());
        getSupportLoaderManager().initLoader(1, null, loaderCallbacks);

        // Set favorite click
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Integer) favorite.getTag() == R.drawable.ic_favorite_black_24dp) {
                    FavoriteDelete(movieItem);
                } else {
                    FavoriteSave(movieItem);
                }
                getSupportLoaderManager().restartLoader(1, null, loaderCallbacks);
            }
        });
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

    private void loadDataTrailer(long movieID) {
        trailerResponseCall = App.getRestClient()
                .getService()
                .getTrailer(movieID);

        trailerResponseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    List<TrailerItem> data = new ArrayList<>();
                    for (TrailerItem trailer : response.body().getResults()) {
                        if (trailer.getType().equals("Trailer")) data.add(trailer);
                    }

                    trailerItems.clear();
                    for (TrailerItem trailer : data) {
                        trailerItems.add(trailer);
                    }
                    trailerAdapter.replaceAll(data);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
            }
        });
    }

    private void loadDataReview(long movieID) {
        reviewResponseCall = App.getRestClient()
                .getService()
                .getReviews(movieID);

        reviewResponseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    List<ReviewItem> data = new ArrayList<>();
                    for (ReviewItem review : response.body().getResults()) {
                        data.add(review);
                    }

                    reviewItems.clear();
                    for (ReviewItem review : data) {
                        reviewItems.add(review);
                    }
                    reviewAdapter.replaceAll(data);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
            }
        });
    }

    private void loadDataFavorite(final long movieID) {
        loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<Cursor>(DetailActivity.this) {
                    @Override
                    public Cursor loadInBackground() {
                        try {
                            return getContentResolver().query(
                                    ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, movieID),
                                    null, null, null, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onStartLoading() {
                        forceLoad();
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                FavoriteSet(data.getCount() > 0);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };
    }

    private void FavoriteSet(boolean isFavorite) {
        int imgSource;

        if (isFavorite) {
            imgSource = R.drawable.ic_favorite_black_24dp;
        } else {
            imgSource = R.drawable.ic_favorite_border_black_24dp;
        }
        favorite.setImageResource(imgSource);
        favorite.setTag(imgSource);
    }

    private void FavoriteSave(MovieItem movieItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIE_ID, movieItem.getId());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, movieItem.getOriginalTitle());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP, movieItem.getBackdropPath());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER, movieItem.getPosterPath());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_RATING, movieItem.getVoteAverage());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE, movieItem.getReleaseDate());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_SYNOPSIS, movieItem.getOverview());
        getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, contentValues);
        Toast.makeText(this, "You like this movie", Toast.LENGTH_SHORT).show();
    }

    private void FavoriteDelete(MovieItem movieItem) {
        long result = getContentResolver().delete(
                ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, movieItem.getId()),
                null, null);

        if (result > 0) getSupportLoaderManager().restartLoader(1, null, loaderCallbacks);
        Toast.makeText(this, "Removed from favorite list", Toast.LENGTH_SHORT).show();
    }

}