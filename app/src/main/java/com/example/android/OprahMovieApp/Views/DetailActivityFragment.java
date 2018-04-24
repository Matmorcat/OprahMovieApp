package com.example.android.OprahMovieApp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.OprahMovieApp.R;
import com.example.android.OprahMovieApp.MainModel.Movie;
import com.squareup.picasso.Picasso;

/**
 * The class that functions to provide information about a specific movie from within the
 * DetailActivity.
 *
 * @see DetailActivity
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        View rootView = _inflater.inflate(R.layout.fragment_detail, _container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.intent_movie_string))) {
            Movie movie = (Movie) intent.getSerializableExtra(getString(R.string.intent_movie_string));

            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
            movieTitle.setText(movie.getTitle());

            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
            Picasso.with(getActivity()).load(movie.getPicUrl()).into(imageView);

            TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
            rating.setText(String.format("%s %s %s", getString(R.string.rating_scale_suffix_prefix), movie.getUserRating(), getString(R.string.rating_scale_suffix)));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            releaseDate.setText(String.format("%s %s", getString(R.string.release_date_prefix), movie.getReleaseDate()));

            TextView moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
            moviePlot.setText(movie.getPlotSynopsis());
        }

        return rootView;


    }
}
