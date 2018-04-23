package com.example.android.OprahMovieApp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.OprahMovieApp.MainActivity;
import com.example.android.OprahMovieApp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends MovieAdapter {
    public FavoritesAdapter(Context _context, int _resource, List<Movie> _movies) {
        super(_context, _resource, _movies);
    }

    @Override
    public View getView(final int _position, View _convertView, ViewGroup _parent) {
        View view = _convertView;

        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(
                    R.layout.list_view_item, _parent, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.list_view_pic);
        String url = getItem(_position).getPicUrl();
        Picasso.with(this.context).load(url).into(imageView);
        TextView textView = (TextView)view.findViewById(R.id.list_view_text);
        textView.setText(getItem(_position).getTitle());
        Button deleteButton = (Button) view.findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Movie movie = getItem(_position);

                // Remove the movie from the favorites list.
                MainActivity.getFavoritesModel().removeMovie(movie);

                // TODO: Does not properly update data now without closing and re-opening window
                notifyDataSetChanged(); // Remove the item.
            }
        });

        return view;

    }



}
