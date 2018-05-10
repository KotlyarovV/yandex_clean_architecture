package com.github.arch.domain;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface RemoteRepository {
    @NonNull
    Single<Response<List<Repo>>> getReposList();

    @NonNull
    Single<Response<RepoInformation>> loadById(long id);
}
