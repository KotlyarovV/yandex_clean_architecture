package com.github.arch.data.locale;

import android.support.annotation.NonNull;

import com.github.arch.domain.LocalRepository;
import com.github.arch.domain.Repo;
import com.github.arch.domain.RepoInformation;

import java.util.List;

import io.reactivex.Flowable;

public class LocalRepositoryImpl implements LocalRepository {

    @NonNull
    private final ReposDao mReposDao;
    @NonNull
    private final RepoInformationDAO mRepoInformationDAO;

    public LocalRepositoryImpl(
            @NonNull ReposDao reposDao,
            @NonNull RepoInformationDAO repoInformationDAO) {
        mReposDao = reposDao;
        mRepoInformationDAO = repoInformationDAO;
    }

    @NonNull
    @Override
    public Flowable<List<Repo>> getReposList() {
        return mReposDao.getReposList();
    }

    @Override
    public void insertOrUpdate(@NonNull List<Repo> list) {
        mReposDao.insertOrUpdate(list);
    }

    @Override
    public void insertOrUpdate(@NonNull RepoInformation repoInformation) {
        mRepoInformationDAO.insertOrUpdate(repoInformation);
    }

    @NonNull
    @Override
    public Flowable<RepoInformation> loadById(long id) {
        return mRepoInformationDAO.loadById(id);
    }
}
