package com.maca.andres.moviesproject.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.maca.andres.moviesproject.R;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();
    public static final String KEY = "key";

    private TextView description, title, votesAv, realeaseDate, originalLanguage, voteCount, popularity;
    private ImageView poster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String result =getIntent().getStringExtra(KEY);
        Movie movie =
                new Gson().fromJson(result, Movie.class);
        title = findViewById(R.id.details_title);
        description = findViewById(R.id.details_description);
        votesAv = findViewById(R.id.details_vote_average);
        realeaseDate = findViewById(R.id.details_release_date);
        originalLanguage = findViewById(R.id.details_original_language);
        voteCount = findViewById(R.id.details_vote_count);
        popularity = findViewById(R.id.details_starred_popularity);
        poster = findViewById(R.id.details_image);
        LoggerDebug.print(TAG, "Title: "+movie.getTitle());
        setText(title,movie.getTitle());
        setText(description, movie.getOverview());
        setTextWithPreString(votesAv,"/10" ,String.valueOf(movie.getVoteAverage()));
        setText(realeaseDate, String.valueOf(movie.getReleaseDate()));
        setTextWithPreString(originalLanguage, movie.getReleaseDate(), getResources().getString(R.string.language));
        setTextWithPreString(voteCount, String.valueOf(movie.getVoteCount()), getResources().getString(R.string.vote_count));
        setTextWithPreString(popularity, String.valueOf(movie.getPopularity()),getResources().getString(R.string.popularity));
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath()).into(poster);




    }

    private void setText (TextView editText, String data){
        editText.setText(data);
    }
    private void setTextWithPreString(TextView editText, String data, String preData){
        setText(editText, " "+preData+" :"+data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
