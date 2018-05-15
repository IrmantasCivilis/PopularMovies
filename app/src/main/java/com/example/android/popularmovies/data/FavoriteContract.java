package com.example.android.popularmovies.data;

import android.provider.BaseColumns;

public class FavoriteContract {

    public static final String AUTHORITY = "com.example.android.popularmovies";

    public static final android.net.Uri BASE_CONTENT_URI = android.net.Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITE_MOVIES = "favoriteMovies";

    public static final class FavoriteEntry implements BaseColumns {

        public static final android.net.Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

        public static final String CONTENT_LIST_TYPE =
                android.content.ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_FAVORITE_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                android.content.ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_FAVORITE_MOVIES;

        public static final String TABLE_NAME = "favoriteMovies";
        public static final String COLUMN_MOVIE_POSTER = "poster";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_MOVIE_AVERAGE_VOTE = "averageVote";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_ID = "movieId";
    }
}
