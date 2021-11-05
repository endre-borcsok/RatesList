package com.example.app.ui

import com.example.app.databinding.ActivityMainBinding
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {
    @Provides
    internal fun provideBinding(view: MainActivity) : ActivityMainBinding {
        return ActivityMainBinding.inflate(view.layoutInflater)
    }
}