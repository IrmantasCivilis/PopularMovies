package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovies.data.FavoriteContract.FavoriteEntry;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteMovies.db";

    private static final int DATABASE_VERSION = 1;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE " + FavoriteEntry.TABLE_NAME +
                " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_MOVIE_AVERAGE_VOTE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
