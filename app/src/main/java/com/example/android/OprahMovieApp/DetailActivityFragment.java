package com.example.android.OprahMovieApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.OprahMovieApp.data.Movie;
import com.squareup.picasso.Picasso;


public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle savedInstanceState) {
        View rootView = _inflater.inflate(R.layout.fragment_detail, _container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.movie_string))) {
            Movie movie = (Movie) intent.getSerializableExtra(getString(R.string.movie_string));

            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
            movieTitle.setText(movie.getTitle());

            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
            Picasso.with(getActivity()).load(movie.getPicUrl()).into(imageView);

            TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
            rating.setText("" + movie.getUserRating() + "\n" + getString(R.string.rating_scale));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            releaseDate.setText(movie.getReleaseDate());

            TextView moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
            moviePlot.setText(movie.getPlotSynopsis());
        }

        return rootView;


    }
}
