package com.oprahs_voice.android.movies.activities;
/**
 * This class is responsible for controlling the main activity of the application.
 * The activity also includes functions that are required for Android applications.
 *
 * @authors
 * @date
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.oprahs_voice.android.movies.R;
import com.oprahs_voice.android.movies.models.favorites.FavoritesModel;
import com.oprahs_voice.android.movies.models.main.MovieAdapter;
import com.oprahs_voice.android.movies.utilities.Movie;
import com.oprahs_voice.android.movies.models.main.FetchMovieData;
import com.oprahs_voice.android.movies.settings.Settings;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private static MovieAdapter movieAdapter;       // Reference to the movie adapter.
    private static FavoritesModel favoritesModel;   // Reference to the favorites model.
    private String sort;                            // Preference for sorting movie.
    private Settings settings;
    private FetchMovieData controller;
    private Menu activityMenu;
    /**
     * Method to return the favoritesModel member to be displayed.
     *
     * @return the FavoritesModel member
     */
    public static FavoritesModel getFavoritesModel() {
        return favoritesModel;
    }

    /**
     * Method to return the movieAdapter member to be acted upon by FetchMovieData and FavoritesModel.
     *
     * @return the movieAdapter member variable
     */
    public static MovieAdapter getMovieAdapter() {
        return movieAdapter;
    }

    /**
     * This method initializes an adapter to display clickable movie posters.
     *
     * @param _imageLayout the layout file which specifies image properties, i.e. size and scale type
     * @param _viewLayout  the layout file which specifies which type of layout will display the images
     */
    private void initializeMovieAdapter(int _imageLayout, int _viewLayout) {
        List<Movie> items = new ArrayList<>();
        movieAdapter = new MovieAdapter(getApplicationContext(), _imageLayout, items);

        Log.d("bindAdapterToView", "Adapter created");

        GridView gridView = (GridView) findViewById(_viewLayout);
        if (gridView != null) {
            gridView.setAdapter(movieAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Movie movie = (Movie) adapterView.getAdapter().getItem(i);
                    Intent detailActivityIntent = new Intent(getApplicationContext(), DetailActivity.class);
                    detailActivityIntent.putExtra(getString(R.string.intent_movie_string), movie);
                    startActivity(detailActivityIntent);
                }
            });
        }

        List<Movie> movies = (List<Movie>) getLastCustomNonConfigurationInstance();
        if (movies == null) {
            if (isNetworkAvailable()) {
                //if (controller.getSaveBoolean()) {
                    executeFetchMoviesTask();
                }
               // else controller.executeFetchMoviesTask("popular");
           // }
         else
            movieAdapter.updateValues(movies);
        }
    }

    /**
     * Method to determine whether the device being used has internet access.
     *
     * @return <tt>true</tt> if network is available
     */
    private boolean isNetworkAvailable() throws NullPointerException {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } else {
            Log.e("Main Activity", "isNetworkAvailable: Connectivity manager not found or null.");
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * onCreate is the Android system callback method that is called upon creation of the application.
     *
     * @param _savedInstanceState any saved information from a previous run, i.e. if the device is
     *                           rotated, a new instance of the app is created with saved information
     *                           from the previous run
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        settings = new Settings(getApplicationContext());
        this.sort = settings.getSort();
        setMainScreen(R.layout.activity_main);
        initializeMovieAdapter(R.layout.grid_view_pic, R.id.grid_view_layout);

        // Initialize the favorites model for storing favorite movies.
        favoritesModel = new FavoritesModel(this);
    }

    /**
     * Android callback method which creates a menu in the action bar in the upper-right corner of the screen.
     *
     * @param _menu a "menu" layout file
     * @return <tt>true</tt> by default
     */
    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, _menu);
        switchSortTitle(_menu);
        return true;
    }
    /**
     * When the user clicks the sort by option in the main menu, toggle the sort method between
     * popularity and User Rating and update the view.
     * @param _item the menu item clicked
     * @return <tt></tt>success recursive call
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {

        int id = _item.getItemId();
        // Change the sorting order of movies.
        if (id == R.id.action_sort) {
           /*if (this.sort.equals("popular")) {*/
            this.sort = "top_rated";
            executeFetchMoviesTask();
            //_item.setTitle(R.string.menu_sort_popularity);
        }
        else if(id == R.id.action_sort_rating){
            this.sort="popular";
            executeFetchMoviesTask();
            //_item.setTitle(R.string.menu_sort_user_rating);
        }
        // Take the user to the favorites view.
        else if (id == R.id.action_favorites) {
            startFavoritesActivity();
        }
        else{
            Intent intent = new Intent(this, SettingsView.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(_item);
    }
    /**
     * This method allows the menu to be edited while the application is running.
     * @param _menu a "menu" layout file
     * @return super.onPrepareOptionsMenu(menu)
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu _menu) {
        switchSortTitle(_menu);
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(_menu);
    }

    /**
     * Android system callback method which is called when the app is terminated.
     *
     * @param _savedInstanceState the information to be saved
     */

    @Override
    public void onSaveInstanceState(Bundle _savedInstanceState) {
        // Save the user's current sort state.
        _savedInstanceState.putString("USER_SORT", this.sort);
        super.onSaveInstanceState(_savedInstanceState);
    }
    public void executeFetchMoviesTask() {
        if (isNetworkAvailable()) {
            FetchMovieData fetchMovieData = new FetchMovieData(getApplicationContext(), settings.getPages());
            fetchMovieData.execute(this.sort);
        }
    }

    /**
     * This method specifies which layout file will be set for the main screen of the app.
     *
     * @param _layout the XML layout file which specifies how the activity looks on screen
     */
    private void setMainScreen(int _layout) {
        setContentView(_layout);
    }
    /**
     * This method initiates Favorites Activity
     */
    private void startFavoritesActivity() {
        Intent favoritesActivityIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
        startActivity(favoritesActivityIntent);
    }

    /**
     * This method takes in a menu item and based on the sort state will hide one of the menu options.
     * @param _menu a "menu" item
     */
    private void switchSortTitle(Menu _menu){
        if (this.sort.equals("top_rated")) {
            _menu.findItem(R.id.action_sort).setVisible(false);
            _menu.findItem(R.id.action_sort_rating).setVisible(true);
        } else {
            _menu.findItem(R.id.action_sort).setVisible(true);
            _menu.findItem(R.id.action_sort_rating).setVisible(false);
        }
    }
}




