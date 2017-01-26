package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;


import com.example.android.popularmovies.utils.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by vivekpatel on 1/22/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> mMoviesList;
    private Context mContext;
    private movieAdapterOnClickHandler mClickHandler;



    public interface movieAdapterOnClickHandler{
        void onClick(Movie movie);
    }

    public MoviesAdapter(movieAdapterOnClickHandler mClickHandler){
        this.mClickHandler=mClickHandler;
    }

    public void setMoviesList(List<Movie> moviesList){
        mMoviesList=moviesList;
    }

    public void setContext(Context context){
        mContext=context;
    }


    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int layOutId=R.layout.movies_grid_items;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately=false;

        View view= layoutInflater.inflate(layOutId,parent,shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position){
        Movie movie=mMoviesList.get(position);
        Picasso.with(mContext).load(movie.posterPath).fit().into(holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        if(mMoviesList==null)
            return 0;
        return mMoviesList.size();
    }


    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        protected ImageView mPosterImageView;
        public MoviesAdapterViewHolder(View view){
            super(view);
            mPosterImageView=(ImageView)view.findViewById(R.id.movie_poster);
            view.setOnClickListener(this);
        }

        public void onClick(View view){
            int adapterPosition=getAdapterPosition();
            Movie movie=mMoviesList.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }
}
