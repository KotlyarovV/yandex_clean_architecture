package com.github.arch.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.arch.App;
import com.github.arch.domain.LoadReposUseCase;
import com.github.arch.domain.Repo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RepoListViewModel extends AndroidViewModel {

    private static final String TAG = "RepoListViewModel";

    @SuppressWarnings("NullableProblems")
    @Inject
    @NonNull
    LoadReposUseCase mLoadReposUseCase;

    @NonNull
    private final MutableLiveData<List<Repo>> mReposLiveData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> mShowError = new MutableLiveData<>();

    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public RepoListViewModel(Application application) {
        super(application);
        ((App) application).getAppComponent().inject(this);

        mReposLiveData.setValue(null);

        loadRepos();
    }

    private void loadRepos() {
        mShowLoading.postValue(true);
        Disposable disposable = mLoadReposUseCase.getRepos().
                subscribe(repos -> {
                            Log.d(TAG, "RepoListViewModel: successfully loaded repos");
                            mReposLiveData.postValue(repos);
                            mShowError.postValue(false);
                            mShowLoading.postValue(false);
                        },
                        throwable -> {
                            Log.e(TAG, "RepoListViewModel: failed to load repos", throwable);
                            mShowError.postValue(true);
                            mReposLiveData.postValue(null);
                            mShowLoading.postValue(false);
                        });
        mCompositeDisposable.add(disposable);
    }

    @NonNull
    public LiveData<List<Repo>> getRepos() {
        return mReposLiveData;
    }

    @NonNull
    public LiveData<Boolean> getShowLoading() {
        return mShowLoading;
    }

    @NonNull
    public LiveData<Boolean> getShowError() {
        return mShowError;
    }

    public void onUpdateRepos() {
        mLoadReposUseCase.updateRepos();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
    }
}
