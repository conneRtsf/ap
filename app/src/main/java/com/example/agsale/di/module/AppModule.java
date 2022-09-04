package com.example.agsale.di.module;

import android.content.Context;

import com.example.agsale.MyApplication;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private Context context;

    public AppModule(MyApplication context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
