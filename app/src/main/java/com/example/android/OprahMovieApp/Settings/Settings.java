package com.example.android.OprahMovieApp.Settings;
/**
 * Last Date Modified:
 * Settings class that is used to save user.
 * Contributing Authors:
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class Settings extends Activity {
    private static Settings settings;
    public SharedPreferences sharedPreferences;


    public static Settings getInstance (Context _context){
        if(settings == null){
            settings = new Settings(_context);
        }
        return settings;
    }

    public Settings(Context _context){
        this.sharedPreferences = _context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    /**
     * Gets the sort state that is stored in settings.
     * @return
     */
    public String getSort(){
        if (this.sharedPreferences!= null) {
            return this.sharedPreferences.getString ("USER_SORT", "popular");
        }
        return "popular";
    }
    /**
     * Changes the sort state stored in settings.
     * @param _sort
     */
    public void setSort(String _sort){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString("USER_SORT", _sort);
        editor.apply();
    }
}
