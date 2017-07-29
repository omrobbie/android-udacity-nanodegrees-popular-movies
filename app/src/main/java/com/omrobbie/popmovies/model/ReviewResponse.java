package com.omrobbie.popmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("page") private int page;
    @SerializedName("total_pages") private int totalPages;
    @SerializedName("results") private List<ReviewItem> results;
    @SerializedName("total_results") private int totalResults;

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setResults(List<ReviewItem> results) {
        this.results = results;
    }

    public List<ReviewItem> getResults() {
        return results;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

}