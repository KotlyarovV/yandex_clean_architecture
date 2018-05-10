package com.github.arch.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.arch.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(RepoListFragment.TAG) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment_container, RepoListFragment.getInstance(), RepoListFragment.TAG);
            ft.commit();
        }
    }
}
