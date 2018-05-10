package com.github.arch.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.arch.R;
import com.github.arch.domain.RepoInformation;

public class RepoInformationFragment extends Fragment {

    public static final String TAG = "RepoInformationFragment";
    public static final String ID = "ID";

    private long id;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView gitUrl;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView key;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView name;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView spdx_id;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView url;


    @SuppressWarnings("NullableProblems")
    @NonNull
    private RepoInformationViewModel mRepoInformationViewModel;


    @NonNull
    public static RepoInformationFragment getInstance(long id){
        RepoInformationFragment repoInformationFragment = new RepoInformationFragment();
        Bundle args = new Bundle();
        args.putLong(RepoInformationFragment.ID, id);
        repoInformationFragment.setArguments(args);
        return repoInformationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name);
        gitUrl = view.findViewById(R.id.git_url);
        url = view.findViewById(R.id.url);
        key = view.findViewById(R.id.key);
        spdx_id = view.findViewById(R.id.spdx_id);

        Bundle args = getArguments();
        if (args != null) {
            this.id = args.getLong(ID);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRepoInformationViewModel = ViewModelProviders.of(this).get(RepoInformationViewModel.class);
        mRepoInformationViewModel.setId(id);
        subscribeUi();
    }

    private void subscribeUi() {
        mRepoInformationViewModel.getRepo().observe(this, repo -> {
            Log.i(TAG, "Update UI");
            gitUrl.setText(repo.getGitUrl());
            key.setText(repo.getKey());
            name.setText(repo.getName());
            spdx_id.setText(repo.getSpdxId());
            url.setText(repo.getUrl());
        });

        mRepoInformationViewModel.getShowError().observe(this, isError -> {
            if (isError != null && isError) {
                Toast.makeText(getContext(), "Error while loading repo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onUpdateRepo(long id) {
        mRepoInformationViewModel.onUpdateRepo(id);
    }
}
