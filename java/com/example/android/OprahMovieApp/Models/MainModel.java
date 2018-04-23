package com.example.android.OprahMovieApp.Models;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.android.OprahMovieApp.Interfaces.ServerInterface;
import com.example.android.OprahMovieApp.Views.MainActivity;
import com.example.android.OprahMovieApp.data.Movie;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
/**
 * Class FetchMoviesTask creates a separate thread on which to fetch movie data from the TMDB server
 */
public class MainModel extends AsyncTask<String, Void, List<Movie>> {
    private final int NUM_PAGES = 3;
    private Context context;
    public MainModel(Context _context) {
        context = _context;

    }
    /**
     * Required method for AsyncTask that defines what operations are to be done on the thread
     * @param params
     * @return A list of movies
     */
    @Override
    protected List<Movie> doInBackground(String... params) {
        //fetch NUM_PAGES pages of movie data
        List<Movie> Movies = new ArrayList<>();
        try {
            for (int i = 1; i < (NUM_PAGES + 1); i++) {
                ServerInterface serverInterface = new ServerInterface(context, "tmdb_api_key");
                String page = serverInterface.getData(i, params[0]);
                ParserInterface dataParser = new ParserInterface(page);
                List<Movie> movies = dataParser.getMovies();
                Movies.addAll(movies);
            }
            return Movies;
        } catch (JSONException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }



    /**
     * Method to fetch the movie data from the TMDB server
     * @param page Data from the server are separated into pages, this determines what page is fetched
     * @param sortBy The method of sorting movies
     * @return The String containing the movies data
     */

    /**
     * AsyncTask method which defines what is to be done after finishing the task - in our case,
     * it sends the movie data to the movie adapter
     * @param result
     */
    @Override
    protected void onPostExecute(List<Movie> result) {
        if (result != null) {
            MainActivity.getMovieAdapter().updateValues(result);
        }
    }


}