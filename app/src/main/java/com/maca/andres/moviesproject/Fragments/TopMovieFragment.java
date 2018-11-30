package com.maca.andres.moviesproject.Fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maca.andres.moviesproject.R;
import com.maca.andres.moviesproject.adapter.CustomScrollListener;
import com.maca.andres.moviesproject.adapter.MoviesAdapter;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;
import com.maca.andres.moviesproject.viewmodels.TopMoviesViewModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
/*
I choose to make individual fragments instead made one ParentFragment and pass some args
to it (shorter way) because maybe in the future the app requires some custom modification to each of the fragments so its more cleaner to
implements some changes with this.
 */

public class TopMovieFragment extends android.support.v4.app.Fragment {
    private static String TAG = TopMovieFragment.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewFactory;
    private TopMoviesViewModel moviesViewModel;
    private MoviesAdapter moviesAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Movie> data;
    private List<Integer> keys;
    private boolean isLoading;
    private boolean isLastPage;
    private int totalPages;

    public TopMovieFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.reyclerview_movie_list);
        data = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(data, getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new CustomScrollListener(linearLayoutManager) {

            @Override
            public boolean isLoading() {

                return isLoading;

            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                loadNextPage();

            }

            @Override
            public boolean isLastPage() { //TODO delete this
                return isLastPage;
            }

            @Override
            public int getTotalPageCount() {
                return totalPages;
            }
        });
        recyclerView.setAdapter(moviesAdapter);
        return view;
    }

    private void loadNextPage() {
        moviesViewModel.getNextChunckOfData();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoggerDebug.print(TAG, "Initializating fragment");
        configureDagger();
        configureViewModel();
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {
        moviesViewModel = ViewModelProviders.of(
                this, viewFactory).get(TopMoviesViewModel.class);
        moviesViewModel.getmovieListTopRated().observe(this, this::updateUI);
    }

    private void updateUI(List<Movie> movies) {
        if (movies.size() != 0) {
            LoggerDebug.print(TAG, "number of movies: " + movies.size());
            LoggerDebug.print(TAG, "First Movie: " + movies.get(0).getTitle());
            data.addAll(movies);
            Set<Movie> hs = new LinkedHashSet<>(data);
            data.clear();
            data.addAll(hs);
            moviesAdapter.notifyDataSetChanged();
            isLoading=false;
            //updateAdapter();
        }
    }
}
