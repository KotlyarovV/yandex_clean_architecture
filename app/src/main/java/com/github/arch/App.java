package com.github.arch;

import android.app.Application;
import android.support.annotation.NonNull;

import com.github.arch.di.AppComponent;
import com.github.arch.di.AppModule;
import com.github.arch.di.DaggerAppComponent;

public class App extends Application {

    @SuppressWarnings("NullableProblems")
    @NonNull
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.
                builder().
                appModule(new AppModule(this)).
                build();
    }

    @NonNull
    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
