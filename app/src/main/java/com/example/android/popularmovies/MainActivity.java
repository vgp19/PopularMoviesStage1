package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.content.Intent;

import com.example.android.popularmovies.utils.Movie;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.MoviesAdapter.movieAdapterOnClickHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements movieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,calculateNoOfColumns(getBaseContext()));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter=new MoviesAdapter(this);
        loadData(NetworkUtils.getPopularUrl());

    }




    public void loadData(String url){
        new fetchData().execute(url);
    }

    @Override
    public void onClick(Movie movie) {
        Context context=this;
        Class destinationClass=MovieDetail.class;
        Intent intentToStartNewActivity=new Intent(context,destinationClass);
        intentToStartNewActivity.putExtra("MovieClass",movie);
        startActivity(intentToStartNewActivity);
    }

    public class fetchData extends AsyncTask<String, Void, List<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if(strings.length==0)
                return null;

            try{
                URL url=new URL(strings[0]);
                String jsonString= NetworkUtils.getInternetConnection(url);
                return getMovieList(jsonString);

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mMoviesAdapter.setContext(MainActivity.this);
            mMoviesAdapter.setMoviesList(movies);
            mRecyclerView.setAdapter(mMoviesAdapter);
        }
    }

    private List<Movie> getMovieList(String jsonString) throws JSONException{
        JSONObject jsonObject=new JSONObject(jsonString);
        JSONArray jsonArray= jsonObject.getJSONArray("results");
        List<Movie>  movieList=new ArrayList<Movie>();
        for(int i=0; i<jsonArray.length();i++){
            JSONObject js=jsonArray.getJSONObject(i);
            String title=js.getString("title");
            String posterPath=js.getString("poster_path");
            String synopsis=js.getString("overview");
            String releaseDate=js.getString("release_date");
            String userRating=js.getString("vote_average");
            Movie m=new Movie(title,posterPath,synopsis,releaseDate,userRating);
            movieList.add(m);
        }
        return movieList;
    }

    //From StcakOverflow and Udacity Suggestions
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.movies,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.sort_by_popular)
            loadData(NetworkUtils.getPopularUrl());
        if(id==R.id.sort_by_ratings)
            loadData(NetworkUtils.getTopUrl());
        return super.onOptionsItemSelected(item);
    }
}
