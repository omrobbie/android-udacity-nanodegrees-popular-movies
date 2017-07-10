package com.omrobbie.popmovies;

import android.app.Application;

import com.omrobbie.popmovies.restapi.RestClient;

public class App extends Application {

    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();

        restClient = new RestClient();
    }

    public static RestClient getRestClient(){
        return restClient;
    }
}
