package com.maca.andres.moviesproject.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.maca.andres.moviesproject.database.converter.ListConverter;
import com.maca.andres.moviesproject.database.dao.MovieDao;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.database.entity.MoviePage;

@Database(entities = {Movie.class, MoviePage.class}, version = 2)
@TypeConverters(ListConverter.class)
public abstract class MovieLocalDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
