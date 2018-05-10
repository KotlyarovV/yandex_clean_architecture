package com.github.arch.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.arch.R;

public class RepoListFragment extends Fragment {
    public static final String TAG = "RepoListFragment";

    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView mRepoRecycler;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private RepoRecyclerAdapter mRepoRecyclerAdapter;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private RepoListViewModel mRepoListViewModel;

    @NonNull
    public static RepoListFragment getInstance() {
        return new RepoListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRepoRecycler = view.findViewById(R.id.repo_recycler);
        mRepoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRepoRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        mRepoRecyclerAdapter = new RepoRecyclerAdapter();
        mRepoRecycler.setAdapter(mRepoRecyclerAdapter);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this::onUpdateRepos);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRepoListViewModel = ViewModelProviders.of(this).get(RepoListViewModel.class);

        subscribeUi();
    }

    private void subscribeUi() {
        mRepoListViewModel.getRepos().observe(this, repos -> {
            Log.i(TAG, "Update UI");
            mSwipeRefreshLayout.setRefreshing(false);
            mRepoRecyclerAdapter.setRepos(repos);
        });

        mRepoListViewModel.getShowError().observe(this, isError -> {
            if (isError != null && isError) {
                Toast.makeText(getContext(), R.string.repos_list_loading_error, Toast.LENGTH_SHORT).show();
            }
        });

        mRepoListViewModel.getShowLoading().observe(this, isShowLoading -> {
            if (isShowLoading != null && isShowLoading) {
                mSwipeRefreshLayout.setRefreshing(true);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void onUpdateRepos() {
        mRepoListViewModel.onUpdateRepos();
    }
}
