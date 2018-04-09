package com.example.android.OprahMovieApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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


        }
        
        return super.onOptionsItemSelected(item);
    }
}
