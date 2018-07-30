package com.but42.halva.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.but42.halva.R
import dagger.Subcomponent
import dagger.android.AndroidInjector

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

@Subcomponent interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
