package com.github.arch.data.locale;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.github.arch.domain.Repo;
import com.github.arch.domain.RepoInformation;

@Database(version = ReposDatabase.DATABASE_VERSION,
        entities = {Repo.class, RepoInformation.class})
public abstract class ReposDatabase extends RoomDatabase {

    public static final String DATABASE_NAME    = "github_repos.db";
    public static final int    DATABASE_VERSION = 1;

    public abstract ReposDao reposDao();
    public abstract RepoInformationDAO repoInformationDAO();

}
