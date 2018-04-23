package com.example.android.OprahMovieApp.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.OprahMovieApp.MainActivity;
import com.example.android.OprahMovieApp.R;
import com.example.android.OprahMovieApp.exceptions.MovieFavoritesException;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This adapter is responsible for modifying the view to update information on favorite movies.
 */
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
        TextView textView = (TextView) view.findViewById(R.id.list_view_text);
        textView.setText(getItem(_position).getTitle());
        Button deleteButton = (Button) view.findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Movie movie = getItem(_position);

                // Try to remove the movie from the favorites list.
                try {

                    // Remove the movie from the favorites list.
                    MainActivity.getFavoritesModel().removeMovie(movie);

                    // Toast to display confirmation that the movie has been added to favorites.
                    Toast.makeText(context.getApplicationContext(), R.string.toast_favorites_removed, Toast.LENGTH_SHORT).show();

                } catch (MovieFavoritesException e) {

                    // If the movie is not in the favorites list, catch the exception.
                    Log.e("DetailActivity", "onOptionsItemSelected: ", e);

                    // Toast to display confirmation that the movie is already in favorites.
                    Toast.makeText(context.getApplicationContext(), R.string.toast_favorites_exists_false, Toast.LENGTH_SHORT).show();
                }

                // TODO: Does not properly update data now without closing and re-opening window.
                notifyDataSetChanged(); // Remove the item.
            }
        });

        return view;

    }


}
