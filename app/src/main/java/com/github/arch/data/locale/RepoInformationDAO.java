package com.github.arch.data.locale;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.github.arch.domain.RepoInformation;

import io.reactivex.Flowable;

@Dao
public abstract class RepoInformationDAO {
    public static final String TABLE_NAME = "repos_information";

    public interface Columns {
        String ID = "id";
        String GIT_URL = "git_url";
        String LICENSE_NAME = "license_name";
        String LICENSE_KEY = "license_key";
        String LICENSE_SPDX_ID = "license_spdx_id";
        String LICENSE_URL = "license_url";
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrUpdate(@NonNull RepoInformation repoInformation);

    @NonNull
    @Query("SELECT * FROM repos_information WHERE id = :id")
    public abstract Flowable<RepoInformation> loadById(long id);
}
