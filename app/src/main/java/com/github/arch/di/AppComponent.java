package com.github.arch.di;

import android.support.annotation.NonNull;

import com.github.arch.ui.RepoInformationViewModel;
import com.github.arch.ui.RepoListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(@NonNull RepoListViewModel repoListViewModel);
    void inject(@NonNull RepoInformationViewModel repoInformationViewModel);
}
