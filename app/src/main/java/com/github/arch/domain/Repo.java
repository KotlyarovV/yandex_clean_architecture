package com.github.arch.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.github.arch.data.locale.ReposDao;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = ReposDao.TABLE_NAME,
        primaryKeys = {ReposDao.Columns.ID})
public final class Repo {

    @SerializedName("id")
    @ColumnInfo(name = ReposDao.Columns.ID)
    private final long mId;

    @SerializedName("name")
    @ColumnInfo(name = ReposDao.Columns.NAME)
    @NonNull
    private final String mName;

    @SerializedName("full_name")
    @ColumnInfo(name = ReposDao.Columns.FULL_NAME)
    @NonNull
    private final String mFullName;

    public Repo(long id,
                @NonNull String name,
                @NonNull String fullName) {
        this.mId = id;
        this.mName = name;
        this.mFullName = fullName;
    }

    public long getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getFullName() {
        return mFullName;
    }
}
