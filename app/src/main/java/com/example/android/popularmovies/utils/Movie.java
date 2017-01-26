package com.example.android.popularmovies.utils;

import java.io.Serializable;

/**
 * Created by vivekpatel on 1/22/17.
 */

public class Movie implements Serializable {
    public String title;
    public String posterPath;
    public String synopsis;
    public String releaseDate;
    public String userRating;

    public Movie(String title,String posterPath,String synopsis,String releaseDate,String userRating){
        this.title=title;
        this.posterPath="http://image.tmdb.org/t/p/w185/"+posterPath;
        this.synopsis=synopsis;
        this.releaseDate=releaseDate;
        this.userRating=userRating;
    }
}
