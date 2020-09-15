package com.example.muzmatch.faketexts.di

import com.example.muzmatch.faketexts.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeTextsModule {

    @Provides
    @Singleton
    fun provideMainViewModel(): MainViewModel = MainViewModel()
}