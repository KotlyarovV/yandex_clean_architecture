package com.github.arch.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.github.arch.data.locale.RepoInformationDAO;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = RepoInformationDAO.TABLE_NAME,
        primaryKeys = {RepoInformationDAO.Columns.ID})
public class RepoInformation {

    @SerializedName("id")
    @ColumnInfo(name = RepoInformationDAO.Columns.ID)
    private final long mId;

    @SerializedName("git_url")
    @ColumnInfo(name = RepoInformationDAO.Columns.GIT_URL)
    private final String mGitUrl;


    @SerializedName("license")
    @NonNull
    @Embedded
    private final License license;


    public RepoInformation(long id,
                           String mGitUrl,
                           String mName,
                           String mKey,
                           String mSpdxId,
                           String mUrl) {
        this.mGitUrl = mGitUrl;
        this.mId = id;
        license = new License(mName, mKey, mSpdxId, mUrl);
    }

    public RepoInformation(long id,
                           @NonNull String mGitUrl,
                           @NonNull License license) {
        this.mGitUrl = mGitUrl;
        this.mId = id;
        this.license = license;
    }

    public long getId() {
        return mId;
    }

    public String getGitUrl() {
        return mGitUrl;
    }

    public String getKey() {
        return license.getKey();
    }

    public String getName() {
        return license.getName();
    }

    public String getSpdxId() {
        return license.getSpdxId();
    }

    public String getUrl() {
        return license.getUrl();
    }

    @NonNull
    public License getLicense() {
        return license;
    }

}
