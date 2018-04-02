package com.example.android.OprahMovieApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<com.example.android.OprahMovieApp.Movie> movies;

    public MovieAdapter(Context context, int resource, List<com.example.android.OprahMovieApp.Movie> movies) {
        this.context = context;
        this.resource = resource;
        this.movies = movies;
    }

    public List<com.example.android.OprahMovieApp.Movie> getMovies() {
        return movies;
    }

    public com.example.android.OprahMovieApp.Movie getItem(int position) {
        return movies.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return movies.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;

        if (view == null) {
            view = (ImageView) LayoutInflater.from(context).inflate(
                    R.layout.grid_view_pic, parent, false);
        }

        String url = getItem(position).getPicUrl();
        Picasso.with(context).load(url).into(view);
        return view;

    }

    public void updateValues(List<com.example.android.OprahMovieApp.Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}

