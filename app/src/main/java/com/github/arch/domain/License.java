package com.github.arch.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.github.arch.data.locale.RepoInformationDAO;
import com.google.gson.annotations.SerializedName;

@Entity
public class License {

    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    @SerializedName("spdx_id")
    private String spdxId;

    @SerializedName("url")
    private String url;

    public License(String name,
                   String key,
                   String spdxId,
                   String url) {
        this.key = key;
        this.name = name;
        this.spdxId = spdxId;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSpdxId() {
        return spdxId;
    }

    public String getUrl() {
        return url;
    }


    public void setKey(String mKey) {
        this.key = mKey;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public void setSpdxId(String mSpdxId) {
        this.spdxId = mSpdxId;
    }

    public void setUrl(String mUrl) {
        this.url = mUrl;
    }
}

