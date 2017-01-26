package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.utils.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    private TextView mMovieTitle;
    private ImageView mPoster;
    private TextView mReleaseDate;
    private TextView mRatings;
    private TextView mSynopsis;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mMovieTitle=(TextView)findViewById(R.id.movie_title);
        mPoster=(ImageView)findViewById(R.id.poster);
        mReleaseDate=(TextView)findViewById(R.id.release_date);
        mRatings=(TextView)findViewById(R.id.ratings);
        mSynopsis=(TextView)findViewById(R.id.synopsis);

        Intent intentThatStartedNewActivity=getIntent();
        if(intentThatStartedNewActivity!=null){
            movie=(Movie)intentThatStartedNewActivity.getSerializableExtra("MovieClass");
            mMovieTitle.setText(movie.title);
            mReleaseDate.setText("Release Date: "+movie.releaseDate);
            mRatings.setText("Average Rating: "+movie.userRating+"/10");
            mSynopsis.setText(movie.synopsis);
           // mSynopsis.setMovementMethod(new ScrollingMovementMethod());
            Picasso.with(MovieDetail.this).load(movie.posterPath).fit().into(mPoster);
        }
    }
}
