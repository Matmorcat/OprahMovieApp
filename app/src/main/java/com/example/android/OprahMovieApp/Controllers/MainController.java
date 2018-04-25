package com.example.android.OprahMovieApp.Controllers;
/**
 * Last Date Modified:
 * MainController class
 * Contributing Authors:
 */
import android.content.Context;
import android.view.MenuItem;

import com.example.android.OprahMovieApp.MainModel.FetchMovieData;
import com.example.android.OprahMovieApp.R;
import com.example.android.OprahMovieApp.Settings.Settings;


public class MainController {
    private String sort = "popular";
    private Context context;
    private Settings settings;

    public MainController(Context _context) {
        this.settings = new Settings(_context);
        this.context = _context;
    }

    public void executeFetchMoviesTask(String _sort) {

        FetchMovieData fetchMovieData = new FetchMovieData(context, getNumberPages());
        fetchMovieData.execute(_sort);
    }


        /**
         * Retrieves the sort state from the settings class.
         *
         * @return
         */
        private String getSort(){
            return this.sort;
        }
    /**
     * Retrieves the default sort from settings.
     * @return
     */
        public String getDefaultSort(){
        return this.settings.getSort();
        }
        /**
         * Changes the sort state contained in the settings class.
         *
         * @param _sort
         */
        private void setSort (String _sort){
            this.sort = (_sort);
        }
    public void onClick(MenuItem _item) {
        if (getSort().equals("popular")) {
            setSort("top_rated");
            executeFetchMoviesTask("top_rated");
            _item.setTitle(R.string.menu_sort_popularity);
        }
        else {
            setSort("popular");
            executeFetchMoviesTask("popular");
            _item.setTitle(R.string.menu_sort_user_rating);
        }
    }
        private Integer getNumberPages(){
            return this.settings.getPages();
        }
    }


