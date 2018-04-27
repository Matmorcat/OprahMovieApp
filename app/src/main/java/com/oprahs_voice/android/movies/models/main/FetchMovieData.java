package com.oprahs_voice.android.movies.models.main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.oprahs_voice.android.movies.activities.FavoritesActivity;
import com.oprahs_voice.android.movies.activities.MainActivity;
import com.oprahs_voice.android.movies.interfaces.ServerInterface;
import com.oprahs_voice.android.movies.utilities.Movie;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FetchMovieData extends AsyncTask<String, Void, Movie> {
    private final String TAG = "" + getClass();
    private WeakReference<Context> weakContext;
    private int ID;

    public FetchMovieData(Context _context, int _id) {
        this.weakContext = new WeakReference<>(_context);
        this.ID = _id;
    }
    /**
     * Required method for AsyncTask that defines what operations are to be done on the thread.
     *
     * @param _params the required parameters
     * @return a list of movies
     */
    @Override
    protected Movie doInBackground(String... _params) {

        try {
                ServerInterface serverInterface = new ServerInterface(this.weakContext);
                String page = serverInterface.getMovieByID(ID);
                Log.d("Test:", page);
                MovieDataParser dataParser = new MovieDataParser(page);
                Movie movie = dataParser.getMovie();

            return movie;

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return null;
    }

    /**
     * AsyncTask method which defines what is to be done after finishing the task - in our case,
     * it sends the movie data to the movie adapter.
     *
     * @param _result the list of movies to alter
     */
    @Override
    protected void onPostExecute(Movie _result) {
        if (_result != null) {
            List<Movie> movie = new ArrayList<>();
            movie.add(_result);
            MainActivity.getMovieAdapter().updateValuesSet(movie);
        }
    }


}