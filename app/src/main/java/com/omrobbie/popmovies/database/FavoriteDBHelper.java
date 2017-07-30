package com.omrobbie.popmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.omrobbie.popmovies.database.FavoriteContract.FavoriteEntry;

public class FavoriteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                FavoriteEntry.COLUMN_TITLE + " TEXT, " +
                FavoriteEntry.COLUMN_POSTER + " TEXT, " +
                FavoriteEntry.COLUMN_SYNOPSIS + " TEXT, " +
                FavoriteEntry.COLUMN_RATING + " REAL, " +
                FavoriteEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                FavoriteEntry.COLUMN_BACKDROP + " TEXT " +
                "); ";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }
}
