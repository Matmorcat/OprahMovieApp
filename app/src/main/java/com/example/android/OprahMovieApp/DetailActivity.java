package com.example.android.OprahMovieApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

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
}
