package com.oprahs_voice.android.movies.settings;
/**
 * settings class that is used to save and retrieve some user data.
 * @authors
 * @date
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Settings extends Activity {
    private SharedPreferences sharedPreferences;

    public Settings(Context _context){
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }
    /**
     * Gets the sort state that is stored in settings.
     * @return
     */
    public String getSort(){
        if (this.sharedPreferences!= null) {
            return this.sharedPreferences.getString ("save_sort", "popular");
        }
        return "popular";
    }
    public int getPages(){
            String value = sharedPreferences.getString("number_of_pages",null);
            return value == null ? 6 : Integer.getInteger(value);
    }
}
