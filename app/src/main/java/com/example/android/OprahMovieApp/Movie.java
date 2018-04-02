package com.example.android.OprahMovieApp;

import android.os.Parcelable;

import java.io.Serializable;


public class Movie implements Serializable {
    private String title;
    private double userRating;
    private String plotSynopsis;
    private String releaseDate;
    private String picUrl;

    public Movie(String title, double userRating, String releaseDate, String plotSynopsis, String picUrl) {
        this.title = title;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.plotSynopsis = plotSynopsis;
        this.picUrl = "http://image.tmdb.org/t/p/w185/" + picUrl;
    }

    public String getTitle() {
        return title;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String toString() {
        return "Movie Title: " + title + "\nUser Rating: " + userRating + "\nRelease Date: " + releaseDate +
                "\nPlot Synopsis: " + plotSynopsis + "Poster URL: " + picUrl;
    }
}

