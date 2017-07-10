package com.omrobbie.popmovies;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.omrobbie.popmovies.adapter.MovieListAdapter;
import com.omrobbie.popmovies.model.MovieItem;
import com.omrobbie.popmovies.model.MovieResponse;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.parentMain) RelativeLayout parentMain;

    String sHighestRated;
    String sMostPopular;

    private MovieListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private Parcelable layoutManagerSavedState;

    private String selectedSort;
    private String categorySelected;
    private int currentPage = 1;
    private Call<MovieResponse> responseCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sHighestRated = "High Rated";
        sMostPopular = "Most Popular";

        setSupportActionBar(toolbar);

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        selectedSort = "most_popular";
        getSupportActionBar().setSubtitle(sMostPopular);
        if (savedInstanceState != null) {
            selectedSort = savedInstanceState.getString("selectedSort");
            if (selectedSort.equals("high_rated"))
                getSupportActionBar().setSubtitle(sHighestRated);
            layoutManagerSavedState = savedInstanceState.getParcelable("layout_manager");
        }

        adapter = new MovieListAdapter();
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this);

        loadData(selectedSort);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_high_rated:
                selectedSort = "high_rated";
                currentPage = 1;
                loadData(selectedSort);
                getSupportActionBar().setSubtitle(sHighestRated);
                return true;
            case R.id.menu_most_popular:
                selectedSort = "most_popular";
                currentPage = 1;
                loadData(selectedSort);
                getSupportActionBar().setSubtitle(sMostPopular);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedSort", selectedSort);
        outState.putParcelable("layout_manager", rv.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (responseCall != null) responseCall.cancel();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        loadData(selectedSort);
    }

    void loadData(String category) {
        categorySelected = category;

        if (category.equals("most_popular")) {
            responseCall = App.getRestClient()
                    .getService()
                    .getPopularMovie(currentPage);
        } else {
            responseCall = App.getRestClient()
                    .getService()
                    .getHighRatedMovie(currentPage);
        }

        responseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<MovieItem> data = response.body().getResults();
                    if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
                    if (currentPage > 1) {
                        adapter.updateData(data);
                    } else {
                        adapter.replaceAll(data);

                        if (layoutManagerSavedState != null) {
                            rv.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
                        }
                    }

                } else {
                    loadFailed();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                loadFailed();
            }
        });
    }

    void loadFailed() {
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
        Toast.makeText(this, "Failed to load data!", Toast.LENGTH_SHORT).show();
    }
}
