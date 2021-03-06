package com.example.android.popularmovies;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.customclasses.Movie;
import com.example.android.popularmovies.customclasses.Review;
import com.example.android.popularmovies.customclasses.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.MainActivity.LOG_TAG;

public final class QueryUtils {

    private static final String KEY_RESULTS = "results";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_ID = "id";

    // Trailers keys
    private static final String KEY_KEY = "key";
    private static final String KEY_NAME = "name";

    // Reviews keys
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    public QueryUtils() {
    }

    private static List<Movie> extractFeaturesFromJson(String movieJSON) {

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONArray moviesArray = baseJsonResponse.optJSONArray(KEY_RESULTS);

            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject currentMovie = moviesArray.getJSONObject(i);
                String moviePoster = currentMovie.getString(KEY_POSTER_PATH);
                String movieTitle = currentMovie.getString(KEY_ORIGINAL_TITLE);
                String releaseDate = currentMovie.getString(KEY_RELEASE_DATE);
                String plotSynopsis = currentMovie.getString(KEY_OVERVIEW);
                double voteAverage = currentMovie.getDouble(KEY_VOTE_AVERAGE);
                int movieId = currentMovie.getInt(KEY_ID);

                Movie movie = new Movie(moviePoster, movieTitle, releaseDate, plotSynopsis, voteAverage, movieId);
                movies.add(movie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing movie JSON");

        }
        return movies;
    }

    private static List<Trailer> extractTrailersFromJson(String videoJSON) {

        if (TextUtils.isEmpty(videoJSON)) {
            return null;
        }

        List<Trailer> trailers = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(videoJSON);
            JSONArray trailersArray = baseJsonResponse.optJSONArray(KEY_RESULTS);

            for (int i = 0; i < trailersArray.length(); i++) {
                JSONObject currentTrailer = trailersArray.getJSONObject(i);
                String trailerName = currentTrailer.getString(KEY_NAME);
                String trailerKey = currentTrailer.getString(KEY_KEY);

                Trailer trailer = new Trailer(trailerName, trailerKey);
                trailers.add(trailer);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing movie JSON");
        }
        return trailers;
    }

    private static List<Review> extractReviewsFromJson(String reviewJSON) {
        if (TextUtils.isEmpty(reviewJSON)) {
            return null;
        }
        List<Review> reviews = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(reviewJSON);
            JSONArray reviewsArray = baseJsonResponse.optJSONArray(KEY_RESULTS);

            for (int i = 0; i < reviewsArray.length(); i++) {
                JSONObject currentReview = reviewsArray.getJSONObject(i);
                String reviewAuthor = currentReview.getString(KEY_AUTHOR);
                String reviewContent = currentReview.getString(KEY_CONTENT);

                Review review = new Review(reviewAuthor, reviewContent);
                reviews.add(review);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing movie JSON");
        }
        return reviews;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static List<Movie> fetchMovieData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractFeaturesFromJson(jsonResponse);
    }

    public static List<Trailer> fetchVideoData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractTrailersFromJson(jsonResponse);
    }

    public static List<Review> fetchReviewsData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractReviewsFromJson(jsonResponse);
    }

}
