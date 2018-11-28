package com.maca.andres.moviesproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maca.andres.moviesproject.R;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    public static final int NORMAL_MOVIE = 0; //Small view of a movie
    public static final int STARRED_MOVIE = 1; //Starred Movie, its view is bigger
    private List<Movie> data;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case NORMAL_MOVIE:
                View view = inflater.inflate(R.layout.normal_movie_item, parent, false);
                LoggerDebug.print(TAG, "NormalMovie ViewHolder");
                return new NormalMovieVH(view);
            case STARRED_MOVIE:
                View starredView = inflater.inflate(R.layout.starred_movie_itemv3, parent, false);
                LoggerDebug.print(TAG, "NormalMovie ViewHolder");
                return new StarredMovieVH(starredView);
            default:
                View defaultView = inflater.inflate(R.layout.normal_movie_item, parent, false);
                LoggerDebug.print(TAG, "NormalMovie ViewHolder");
                return new NormalMovieVH(defaultView);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = data.get(position);
        switch (holder.getItemViewType()) {
            case STARRED_MOVIE:

                final StarredMovieVH starredMovieVH = (StarredMovieVH) holder;
                starredMovieVH.description.setText(movie.getOverview());
                starredMovieVH.voteAverage.setText(movie.getVoteAverage().toString());
                starredMovieVH.title.setText(movie.getTitle());
                Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + data.get(position).getPosterPath()).into(starredMovieVH.picture);
                break;
            case NORMAL_MOVIE:
                final NormalMovieVH normalMovieVH = (NormalMovieVH) holder;
                normalMovieVH.voteAverage.setText(movie.getVoteAverage().toString());
                normalMovieVH.title.setText(movie.getTitle());
                Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + data.get(position).getPosterPath()).into(normalMovieVH.picture);

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getVoteAverage() > 8.0f) {
            return STARRED_MOVIE;
        } else return NORMAL_MOVIE;

    }

    class NormalMovieVH extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView voteAverage;
        private ImageView picture;

        public NormalMovieVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movietitle_textview);
            voteAverage = itemView.findViewById(R.id.movie_realease_date);
            picture = itemView.findViewById(R.id.movie_imageview);
        }
    }

    class StarredMovieVH extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView voteAverage;
        private TextView description;
        private ImageView picture;

        public StarredMovieVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.starred_title);
            voteAverage = itemView.findViewById(R.id.starred_popularity);
            description = itemView.findViewById(R.id.starred_description);
            picture = itemView.findViewById(R.id.starred_image);

        }
    }

    public MoviesAdapter(List<Movie> data, Context context) {
        this.data = data;
        this.context = context;
    }

}
