package com.maca.andres.moviesproject.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.maca.andres.moviesproject.Fragments.SearchFragment;
import com.maca.andres.moviesproject.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/*
Im going to present the query results here and re use code.
There is a simply activity with fragment that display the user query!
Like all the app im going to put View Model connected with the repo.

 */
public class SearchActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        configureDagger();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.search_frame, new SearchFragment(), null)
                .commit();


    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

}
