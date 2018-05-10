package com.github.arch.data.remote;

import android.support.annotation.NonNull;

import com.github.arch.domain.RemoteRepository;
import com.github.arch.domain.Repo;
import com.github.arch.domain.RepoInformation;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public class RemoteRepositoryImpl implements RemoteRepository {

    @NonNull
    private final GithubService mGithubService;

    public RemoteRepositoryImpl(@NonNull GithubService githubService) {
        mGithubService = githubService;
    }

    @NonNull
    @Override
    public Single<Response<List<Repo>>> getReposList() {
        return mGithubService.getReposList();
    }

    @NonNull
    @Override
    public Single<Response<RepoInformation>> loadById(long id) {
        return mGithubService.getRepo(id);
    }
}
