package com.example.android.OprahMovieApp.Controllers;
/**
 * Last Date Modified:
 * MainController class
 * Contributing Authors:
 */
import android.content.Context;
import com.example.android.OprahMovieApp.Settings.Settings;


public class MainController {
    private Context context;
    private Settings settings;

    public MainController(Context _context) {
        this.settings = settings.getInstance(_context);
        this.context = _context;
    }

    /**
     * Retrieves the sort state from the settings class.
     *
     * @return
     */
    public String getSort() {
       return this.settings.getSort();
    }

    /**
     * Changes the sort state contained in the settings class.
     *
     * @param _string
     */
    public void setSort(String _string) {
        this.settings.setSort(_string);
    }
}
