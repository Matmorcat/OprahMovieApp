package com.example.android.OprahMovieApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu (Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_detail, _menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    /**
     * Android callback method to handle action bar item clicks. The action bar will handle
     * clicks on the Home/Up button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_favorite) {
            //intent to retrieve movie information
            Intent intent = this.getIntent();
            if (intent != null && intent.hasExtra(getString(R.string.movie_string))) {
                Movie movie = (Movie) intent.getSerializableExtra(getString(R.string.movie_string));
            }
            //This won't be functional until we merge db branch
            FDBInterface dataface = new FDBInterface(getApplicationContext());
            dataface.getConfiguations().add(movie);
            //Toast to display confirmation that movie has been added to favorites
            Toast addFavoritesToast = new Toast(getApplicationContext());
            addFavoritesToast.makeText(getApplicationContext(), R.string.favorites_toast_text, Toast.LENGTH_SHORT).show();


            // TODO: Implement passing of movie information to database and favorites activity
        }

        return super.onOptionsItemSelected(item);
    }
}
