package com.oprahs_voice.android.movies.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Settings class that is used to retrieve data from the sharedPreferences file.
 *
 * @author Luke Orr
 * @author Matthew Moretz
 * @date April 27th, 2018
 */
public class Settings extends Activity {
    private SharedPreferences sharedPreferences;


    public Settings(Context _context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }


    /**
     * Gets the sort state that is stored in SharedPreferences file.
     *
     * @return the sort preference
     */
    public String getSort() {
        if (this.sharedPreferences != null) {
            return this.sharedPreferences.getString("save_sort", "popular");
        }
        return "popular";
    }


    /**
     * Gets the pages that is stored in the SharedPreferences file.
     *
     * @return the number of pages to load
     */
    public int getPages() {
        String value = this.sharedPreferences.getString("number_of_pages", null);
        return value == null ? 6 : Integer.valueOf(value);
    }
}
