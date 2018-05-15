package com.example.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavoriteContract;

public class FavoriteCursorAdapter extends CursorAdapter {

    private static final String IMAGE_SIZE = "w185/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private Cursor mCursor;
    private Context mContext;

    public FavoriteCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_view_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView title = view.findViewById(R.id.list_text_view);

        int titleColumnIndex = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIE_TITLE);

        String movieTitle = cursor.getString(titleColumnIndex);

        title.setText(movieTitle);
    }
}
