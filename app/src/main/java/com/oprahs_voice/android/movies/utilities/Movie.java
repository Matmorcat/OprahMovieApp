package com.oprahs_voice.android.movies.utilities;
/**
 * This is the class that is responsible for holding all information about a particular movie and
 * creating the Movie object. It has getters, setters, a constructor, and a toString() override.
 * @authors
 * @date
 */
import java.io.Serializable;


public class Movie implements Serializable {
    private int movieID;
    private String title;
    private double userRating;
    private String plotSynopsis;
    private String releaseDate;
    private String picUrl;

    public Movie(int _movieID, String _title, double _userRating, String _releaseDate, String _plotSynopsis, String _picUrl) {
        this.movieID = _movieID;
        this.title = _title;
        this.userRating = _userRating;
        this.releaseDate = _releaseDate;
        this.plotSynopsis = _plotSynopsis;
        this.picUrl = "http://image.tmdb.org/t/p/w185/" + _picUrl;
    }

    public int getMovieID() {
        return this.movieID;
    }

    public String getTitle() {
        return this.title;
    }

    public double getUserRating() {
        return this.userRating;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public String getPlotSynopsis() {
        return this.plotSynopsis;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public String toString() {
        return "Movie ID: " + this.movieID +
                "\nMovie Title: " + this.title +
                "\nUser Rating: " + this.userRating +
                "\nRelease Date: " + this.releaseDate +
                "\nPlot Synopsis: " + this.plotSynopsis +
                "\nPoster URL: " + this.picUrl;
    }
}

