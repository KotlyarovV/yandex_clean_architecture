package com.github.arch.domain;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoadReposUseCase {

    private static final String TAG = "LoadReposUseCase";

    @NonNull
    private final RemoteRepository mRemoteRepository;
    @NonNull
    private final LocalRepository mLocalRepository;

    public LoadReposUseCase(@NonNull RemoteRepository remoteRepository,
                            @NonNull LocalRepository localRepository) {
        mRemoteRepository = remoteRepository;
        mLocalRepository = localRepository;
    }

    @NonNull
    public Flowable<List<Repo>> getRepos() {
        // We load data from local repository, give it to our caller and then load data from remote repository.
        // When data from remote repository is loaded, we store it in out local repository and our caller automatically
        // gets notified about changed data.
        Flowable<List<Repo>> data = mLocalRepository.getReposList();
        updateRepos();
        return data;
    }

    @NonNull
    public Flowable<RepoInformation> getRepositoryInformation(long id) {
        Flowable<RepoInformation> repoInformationFlowable = mLocalRepository.loadById(id);
        updateRepositoryInformation(id);
        return repoInformationFlowable;

    }

    @SuppressLint("CheckResult")
    public void updateRepos() {
        mRemoteRepository.getReposList().
                subscribeOn(Schedulers.io()).
                subscribe(
                        response -> {
                            if (response.isSuccessful()) {
                                List<Repo> list = response.body();
                                if (list != null) {
                                    mLocalRepository.insertOrUpdate(list);
                                }
                            }
                        },
                        throwable -> Log.e(TAG, "updateRepos()", throwable));
    }

    @SuppressLint("CheckResult")
    public void updateRepositoryInformation(long id) {
        mRemoteRepository.loadById(id).
                subscribeOn(Schedulers.io()).
                subscribe(
                        repoInformationResponse -> {
                            Response c = repoInformationResponse.raw();

                            String a = repoInformationResponse.message();

                            String b = repoInformationResponse.raw().toString();
                            repoInformationResponse.toString();
                            RepoInformation repoInformation = repoInformationResponse.body();
                            mLocalRepository.insertOrUpdate(repoInformation);
                            if (repoInformationResponse.isSuccessful()) {
                               // RepoInformation repoInformation = repoInformationResponse.body();
                                if (repoInformation != null) {
                                    mLocalRepository.insertOrUpdate(repoInformation);
                                }
                            }
                        },
                        throwable -> Log.e(TAG, "update", throwable)
                );
    }
}
