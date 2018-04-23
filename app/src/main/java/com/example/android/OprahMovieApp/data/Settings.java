package com.example.android.OprahMovieApp.data;
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
        sharedPreferences = _context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }
    public void setSort(String _sort){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_SORT", _sort);
        editor.commit();
    }
    public String getSort(){
        if (sharedPreferences!= null) {
            return sharedPreferences.getString ("USER_SORT", "popular");
        }
        return "popular";
    }
}
