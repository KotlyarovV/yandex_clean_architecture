package com.github.arch.domain;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

public interface LocalRepository {
    @NonNull
    Flowable<List<Repo>> getReposList();

    Flowable<RepoInformation> loadById(long id);

    void insertOrUpdate(@NonNull List<Repo> list);

    void insertOrUpdate(@NonNull RepoInformation repoInformation);
}
