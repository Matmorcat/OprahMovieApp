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
        return this.movies;
    }

    public com.example.android.OprahMovieApp.Movie getItem(int position) {
        return this.movies.get(position);
    }

    public long getItemId(int _position) {
        return _position;
    }

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

    public void updateValues(List<com.example.android.OprahMovieApp.Movie> _movies) {
        this.movies = _movies;
        notifyDataSetChanged();
    }
}

