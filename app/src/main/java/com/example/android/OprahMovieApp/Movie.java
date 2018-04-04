package com.example.android.OprahMovieApp;

import android.os.Parcelable;

import java.io.Serializable;


public class Movie implements Serializable {
    private String title;
    private double userRating;
    private String plotSynopsis;
    private String releaseDate;
    private String picUrl;

    public Movie(String _title, double _userRating, String _releaseDate, String _plotSynopsis, String _picUrl) {
        this.title = _title;
        this.userRating = _userRating;
        this.releaseDate = _releaseDate;
        this.plotSynopsis = _plotSynopsis;
        this.picUrl = "http://image.tmdb.org/t/p/w185/" + _picUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public double getUserRating() {
        return this.userRating;
    }

    public String getPlotSynopsis() {
        return this.plotSynopsis;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public String toString() {
        return "Movie Title: " + this.title + "\nUser Rating: " + this.userRating + "\nRelease Date: " + this.releaseDate +
                "\nPlot Synopsis: " + this.plotSynopsis + "Poster URL: " + this.picUrl;
    }
}

