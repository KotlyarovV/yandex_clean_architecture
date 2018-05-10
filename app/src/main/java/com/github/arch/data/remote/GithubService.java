package com.github.arch.data.remote;

import com.github.arch.domain.Repo;
import com.github.arch.domain.RepoInformation;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    String BASE_URL = "https://api.github.com/";

    @GET("/repositories?page=1")
    Single<Response<List<Repo>>> getReposList();

    @GET("repositories/{id}")
    Single<Response<RepoInformation>> getRepo(@Path("id") long id);
}