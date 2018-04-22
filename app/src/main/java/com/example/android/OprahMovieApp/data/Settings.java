package com.example.android.OprahMovieApp.data;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;


public class Settings extends Activity {
    public JSONObject settings = new JSONObject();

    public JSONObject makeJsonObject(String _sort, SQLiteDatabase _db)
            throws JSONException {
        try {
            settings.put("Sort", _sort);
            settings.put("Database", _db);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return settings;
    }


    public void writeDataBaseToJSON(SQLiteDatabase _db) {
        try {
            settings.put("DataBase", _db);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void writeSortToJSON(Context context, String _sort) {
        try {

            settings.put("Sort", _sort);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String retrieveSort() throws JSONException {
        JSONObject parent = this.parseJSONData();
        String string = parent.getString("Sort");
        return string;
    }
    public JSONObject parseJSONData() {
        String JSONString = null;
        JSONObject JSONObject = null;
        try {

            //open the inputStream to the file
            InputStream inputStream = getAssets().open("settings.json");

            int sizeOfJSONFile = inputStream.available();

            //array that will store all the data
            byte[] bytes = new byte[sizeOfJSONFile];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            JSONString = new String(bytes, "UTF-8");
            JSONObject = new JSONObject(JSONString);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (JSONException x) {
            x.printStackTrace();
            return null;
        }
        return JSONObject;
    }

}
