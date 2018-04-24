package com.example.android.OprahMovieApp.MainModel;
/**
 * Last Date Modified:
 * This class provides and updates information in the main view to display an ordered list of movies
 * that the user can select from or learn more about.
 * Contributing Authors:
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.OprahMovieApp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<Movie> movies;


    public MovieAdapter(Context _context, int _resource, List<Movie> _movies) {
        this.context = _context;
        this.resource = _resource;
        this.movies = _movies;
    }


    /**
     * Get the number of movies saved in the local cache
     *
     * @return the amount of movies saved locally
     */
    public int getCount() {
        return this.movies.size();
    }


    public Movie getItem(int _position) {
        return this.movies.get(_position);
    }


    public long getItemId(int _position) {
        return _position;
    }


    /**
     * Get a movie and its information by providing the movie ID
     *
     * @param _movieID the movie ID of the movie to retrieve
     * @return the movie information
     */
    public Movie getMovieByID(int _movieID) {
        for (Movie movie : this.movies) {

            // The movie matches the id provided.
            if (movie.getMovieID() == _movieID) {
                return movie;
            }
        }

        // TODO: Perform a search query on the server to find a movie by this ID (This is a temporary return statement).
        return (new Movie(_movieID, "Unknown", 0, null, null, null));
    }


    public List<Movie> getMovies() {
        return this.movies;
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


    /**
     * Checks to see if there is a movie by the given ID cached locally.
     *
     * @param _movieID the id of the movie to search for in cache
     * @return <tt>true</tt> if the movie is locally saved
     */
    public boolean isCachedMovieByID(int _movieID) {
        for (Movie movie : this.movies) {

            // The movie matches the id provided.
            if (movie.getMovieID() == _movieID) {
                return true;
            }
        }
        // No movie matches the id provided.
        return false;
    }


    public void updateValues(List<Movie> _movies) {
        this.movies = _movies;
        notifyDataSetChanged();
    }

}

