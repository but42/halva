package com.but42.halva.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.but42.halva.R
import com.but42.halva.ui.list.ListFragment
import com.but42.halva.ui.timer.TimerFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = TimerFragment.newInstance()
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }
}

@Subcomponent interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
