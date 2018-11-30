package com.maca.andres.moviesproject.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.database.entity.MoviePage;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Insert(onConflict = REPLACE)
    void saveMovie(Movie movie);

    @Insert(onConflict = REPLACE)
    void savePage(MoviePage moviePage);
    @Query("SELECT * FROM moviepage WHERE page= :pageNumber AND category = :category")
    MoviePage loadMovieIdsByCategoryPage(String category, int pageNumber);

    @Query("SELECT * FROM movie WHERE id = :movieid")
    Movie loadMovie(Integer movieid);

    @Query("SELECT * FROM movie")
    List<Movie> movies();


    @Query("SELECT * FROM movie WHERE category = :category")
    List<Movie> getAllMoviesBy(String category);
    @Query("SELECT * FROM movie WHERE category = :category AND title = :title")
    List<Movie> getMovieNamedInCategory(String title, String category);
}
