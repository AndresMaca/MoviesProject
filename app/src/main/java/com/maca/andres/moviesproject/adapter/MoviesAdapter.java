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
/*
I make some movies with bigger screen size because the app bussines logic i.e: You want your movie be 'starred movie' ok, you must pay some fee. In this case i just put starred movies
to those ones with a 8.5 in Vote Average, obviously you can set all the items with the same size just returning one value in the getItemViewType method
 */
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
                LoggerDebug.print(TAG, "Starred ViewHolder");
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
                starredMovieVH.voteAverage.setText(movie.getVoteAverage().toString().concat("/10"));
                starredMovieVH.title.setText(movie.getTitle());
                Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + data.get(position).getPosterPath()).into(starredMovieVH.picture);
                break;
            case NORMAL_MOVIE:
                final NormalMovieVH normalMovieVH = (NormalMovieVH) holder;
                normalMovieVH.voteAverage.setText(movie.getVoteAverage().toString().concat("/10"));
                normalMovieVH.title.setText(movie.getTitle());
                normalMovieVH.shortDescription.setText(movie.getOverview().substring(0,Math.min(movie.getOverview().length(), 100)).concat(" " +
                        "....."));
                Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + data.get(position).getPosterPath()).into(normalMovieVH.picture);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (data.get(position).getVoteAverage() > 8.5f) {
            return STARRED_MOVIE;
        } else return NORMAL_MOVIE;


    }

    class NormalMovieVH extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView voteAverage;
        private TextView shortDescription;
        private ImageView picture;

        public NormalMovieVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movietitle_textview);
            voteAverage = itemView.findViewById(R.id.movie_average);
            picture = itemView.findViewById(R.id.movie_imageview);
            shortDescription = itemView.findViewById(R.id.movie_overview);
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
