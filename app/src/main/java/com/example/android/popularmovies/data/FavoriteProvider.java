package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.android.popularmovies.data.FavoriteContract.FavoriteEntry;

public class FavoriteProvider extends ContentProvider {

    private static final int MOVIES = 100;
    private static final int MOVIE_WITH_ID = 101;

    private static final android.content.UriMatcher sUriMatcher = buildUriMatcher();
    private FavoriteDbHelper mFavoriteDbHelper;

    public static android.content.UriMatcher buildUriMatcher(){

        android.content.UriMatcher uriMatcher = new android.content.UriMatcher(android.content.UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITE_MOVIES, MOVIES);
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITE_MOVIES + "/#", MOVIE_WITH_ID);

        return uriMatcher;

    }

    @Override
    public boolean onCreate() {

        mFavoriteDbHelper = new FavoriteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final android.database.sqlite.SQLiteDatabase db = mFavoriteDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {

            case MOVIES:
                returnCursor =  db.query(FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

                case MOVIE_WITH_ID:
                    selection = FavoriteEntry._ID + "=?";
                    selectionArgs = new String[]{String.valueOf(android.content.ContentUris.parseId(uri))};
                    returnCursor = db.query(FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
        }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return FavoriteEntry.CONTENT_LIST_TYPE;
            case MOVIE_WITH_ID:
                return FavoriteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
        }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final android.database.sqlite.SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch(match) {

            case MOVIES:
                long id = db.insert(FavoriteEntry.TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = android.content.ContentUris.withAppendedId(FavoriteEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final android.database.sqlite.SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int moviesDeleted;

        switch (match) {

            case MOVIES:
                moviesDeleted = db.delete(FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIE_WITH_ID:

                String id = uri.getPathSegments().get(1);
                moviesDeleted = db.delete(FavoriteEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (moviesDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return moviesDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
