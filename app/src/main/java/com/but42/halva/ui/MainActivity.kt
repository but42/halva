package com.but42.halva.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.but42.halva.R
import com.but42.halva.ui.base.FragmentType
import com.but42.halva.ui.base.FragmentUtil
import com.but42.halva.ui.list.ListFragment
import com.but42.halva.ui.start.StartFragment
import com.but42.halva.ui.timer.TimerFragment
import dagger.Subcomponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var fragmentUtil: FragmentUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = StartFragment.newInstance()
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
        fragmentUtil.subscribe(this) {
            when (it) {
                FragmentType.LIST -> setActionBar(R.string.list_title, true)
                FragmentType.TIMER -> setActionBar(R.string.timer_title, true)
                else -> setActionBar(R.string.app_name, false)
            }
        }
    }

    private fun setActionBar(title: Int, upIsEnabled: Boolean) {
        supportActionBar?.setTitle(title)
        supportActionBar?.setDisplayHomeAsUpEnabled(upIsEnabled)
        supportActionBar?.setDisplayShowHomeEnabled(upIsEnabled)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        when (fragment) {
            is ListFragment -> fragment.onBackPressed()
            is TimerFragment -> fragment.onBackPressed()
            else -> super.onBackPressed()
        }
    }
}

@Subcomponent interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
