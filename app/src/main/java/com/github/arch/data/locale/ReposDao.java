package com.github.arch.data.locale;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.github.arch.domain.Repo;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class ReposDao {

    public static final String TABLE_NAME = "repos";

    public interface Columns {
        String ID          = "id";
        String NAME        = "name";
        String FULL_NAME   = "full_name";
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrUpdate(@NonNull List<Repo> repos);

    @NonNull
    @Query("SELECT * FROM " + TABLE_NAME + " ORDER BY " + Columns.NAME)
    public abstract Flowable<List<Repo>> getReposList();
}