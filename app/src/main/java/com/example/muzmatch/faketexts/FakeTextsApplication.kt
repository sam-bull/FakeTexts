package com.example.muzmatch.faketexts

import android.app.Application
import com.example.muzmatch.faketexts.di.DaggerFakeTextsComponent
import com.example.muzmatch.faketexts.di.FakeTextsComponent
import com.example.muzmatch.faketexts.di.FakeTextsModule

class FakeTextsApplication : Application() {

    lateinit var component: FakeTextsComponent
    private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerFakeTextsComponent.builder().fakeTextsModule(FakeTextsModule()).build()
    }
}