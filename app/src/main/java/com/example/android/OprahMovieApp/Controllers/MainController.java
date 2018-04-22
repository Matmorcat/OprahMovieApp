package com.example.android.OprahMovieApp.Controllers;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.android.OprahMovieApp.Models.MainModel;
import com.example.android.OprahMovieApp.R;
import com.example.android.OprahMovieApp.Views.FavoritesActivity;
import com.example.android.OprahMovieApp.Views.MainActivity;

public class MainController  {
    private Context context;
    private String sort;
    private MainActivity view;
    public MainController(Context _context) {
        context = _context;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Change sorting order of movies
        if (id == R.id.action_sort) {
            if (sort.equals("popular")) {
                sort = "top_rated";
                MainModel model = new MainModel(context);
                model.execute(sort);
                item.setTitle(R.string.sort_popularity);
            } else {
                sort = "popular";
                MainModel model = new MainModel(context);
                model.execute(sort);
                item.setTitle(R.string.sort_user_rating);
            }

        }

        // Take the user to the favorites view
        if (id == R.id.action_favorites) {
            Intent favoritesActivityIntent = new Intent(context, FavoritesActivity.class);
            view.startActivity(favoritesActivityIntent);
        }


        return onOptionsItemSelected(item);
    }
}

