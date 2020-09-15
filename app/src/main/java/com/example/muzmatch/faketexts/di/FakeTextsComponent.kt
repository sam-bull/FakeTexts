package com.example.muzmatch.faketexts.di

import com.example.muzmatch.faketexts.view.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FakeTextsModule::class])
interface FakeTextsComponent {

    fun inject(mainFragment: MainFragment)

}