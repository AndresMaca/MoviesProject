package com.maca.andres.moviesproject.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.maca.andres.moviesproject.database.converter.ListConverter;
import com.maca.andres.moviesproject.database.dao.MovieDao;
import com.maca.andres.moviesproject.database.entity.Movie;

@Database(entities = {Movie.class}, version = 1)
@TypeConverters(ListConverter.class)
public abstract class MovieLocalDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
