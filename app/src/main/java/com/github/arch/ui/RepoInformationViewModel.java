package com.github.arch.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.arch.App;
import com.github.arch.domain.LoadReposUseCase;
import com.github.arch.domain.RepoInformation;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RepoInformationViewModel extends AndroidViewModel {
    private static final String TAG = "RepoInformationView";

    @SuppressWarnings("NullableProblems")
    @Inject
    @NonNull
    LoadReposUseCase mLoadReposUseCase;

    private long id;

    public void setId(long id) {
        this.id = id;
        loadRepoInformation(this.id);
    }

    @NonNull
    private final MutableLiveData<RepoInformation> mRepoLiveData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> mShowError = new MutableLiveData<>();


    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public RepoInformationViewModel(Application application) {
        super(application);
        ((App) application).getAppComponent().inject(this);

        //mRepoLiveData.setValue(null);

    }



    private void loadRepoInformation(long id) {
        Disposable disposable = mLoadReposUseCase.getRepositoryInformation(id).
                subscribe(repo -> {
                            Log.d(TAG, "RepoListViewModel: successfully loaded repos");
                            mRepoLiveData.postValue(repo);
                            mShowError.postValue(false);
                        },
                        throwable -> {
                            Log.e(TAG, "RepoListViewModel: failed to load repos", throwable);
                            mRepoLiveData.postValue(null);
                            mShowError.postValue(true);
                        });
        mCompositeDisposable.add(disposable);
    }

    @NonNull
    public LiveData<RepoInformation> getRepo() {
        return mRepoLiveData;
    }

    public void onUpdateRepo(long id) {
        mLoadReposUseCase.updateRepositoryInformation(id);
    }

    @NonNull
    public LiveData<Boolean> getShowError() {
        return mShowError;
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
    }
}
