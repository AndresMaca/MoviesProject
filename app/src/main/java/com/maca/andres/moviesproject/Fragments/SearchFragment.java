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
import android.widget.Button;
import android.widget.EditText;

import com.maca.andres.moviesproject.R;
import com.maca.andres.moviesproject.adapter.MoviesAdapter;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;
import com.maca.andres.moviesproject.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SearchFragment extends android.support.v4.app.Fragment {
    private static final String TAG = SearchFragment.class.getSimpleName();
    @Inject
    ViewModelProvider.Factory viewFactory;
    private MoviesAdapter moviesAdapter;

    private SearchViewModel moviesViewModel;
    //private PopularMoviesViewModel moviesViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Movie> data;
    private EditText editText;
    private Button button;

    public SearchFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.reyclerview_movie_search);
        data = new ArrayList<>();
        editText = view.findViewById(R.id.movie_search_text_input);
        button= view.findViewById(R.id.search_movie_button);
        button.setOnClickListener(view1 -> {
            if(!editText.getText().toString().equals("")) moviesViewModel.searchMovie(editText.getText().toString());
        });

        moviesAdapter = new MoviesAdapter(data, getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(moviesAdapter);


        return view;
    }


    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {
      moviesViewModel = ViewModelProviders.of(
                this, viewFactory).get(SearchViewModel.class);
        moviesViewModel.getMovieSearchResults().observe(this, this::updateUI);

    }

    private void updateUI(List<Movie> movies) {
        if (movies.size() != 0) {
            LoggerDebug.print(TAG,"Movies: "+movies.size());
            data.clear();
            data.addAll(movies);
            moviesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureDagger();
        configureViewModel();
    }
}
