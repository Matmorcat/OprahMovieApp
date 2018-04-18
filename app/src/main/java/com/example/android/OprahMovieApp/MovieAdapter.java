package com.example.android.OprahMovieApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.OprahMovieApp.favorites.FavoritesModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<Movie> movies;
    private static FavoritesModel favoritesModel;

    public MovieAdapter(Context _context, int _resource, List<Movie> _movies) {
        this.context = _context;
        this.resource = _resource;
        this.movies = _movies;

        // Initialize the favorites model for storing favorite movies
        favoritesModel = new FavoritesModel(_context);
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public Movie getItem(int position) {
        return this.movies.get(position);
    }

    /**
     * Checks to see if there is a movie by the given ID cached locally
     * @param movieID The id of the movie to search for in cache
     * @return True if the movie is locally saved, false if not
     */
    public boolean isCachedMovieByID(int movieID) {
        for (Movie movie : movies) {

            // The movie matches the id provided
            if (movie.getMovieID() == movieID){
                return true;
            }
        }
        // No movie matches the id provided
        return false;
    }

    /**
     * Get a movie and its information by providing the movie ID
     * @param movieID The movie ID of the movie to retrieve
     * @return The movie information
     */
    public Movie getMovieByID(int movieID) {
        for (Movie movie : movies) {

            // The movie matches the id provided
            if (movie.getMovieID() == movieID){
                return movie;
            }
        }

        // TODO: Perform a search query on the server to find a movie by this ID (This is a temporary return statement)
        return (new Movie(movieID, "Unknown", 0, null, null, null));
    }

    public long getItemId(int _position) {
        return _position;
    }

    /**
     * Get the number of movies saved in the local cache
     * @return The amount of movies saved locally
     */
    public int getCount() {
        return this.movies.size();
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        ImageView view = (ImageView) _convertView;

        if (view == null) {
            view = (ImageView) LayoutInflater.from(this.context).inflate(
                    R.layout.grid_view_pic, _parent, false);
        }

        String url = getItem(_position).getPicUrl();
        Picasso.with(this.context).load(url).into(view);
        return view;

    }

    public void updateValues(List<Movie> _movies) {
        this.movies = _movies;
        notifyDataSetChanged();
    }

    public static FavoritesModel getFavoritesModel(){
        return favoritesModel;
    }
}

