package com.but42.halva.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import com.but42.halva.repository.RepositoryModule
import com.but42.halva.ui.MainActivity
import com.but42.halva.ui.MainComponent
import com.but42.halva.ui.list.ListComponent
import com.but42.halva.ui.list.ListFragment
import com.but42.halva.ui.start.StartComponent
import com.but42.halva.ui.start.StartFragment
import com.but42.halva.ui.timer.TimerComponent
import com.but42.halva.ui.timer.TimerFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.FragmentKey
import dagger.android.support.HasSupportFragmentInjector
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
class App : Application(), HasSupportFragmentInjector {

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent.builder().context(this).build()
        component.inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector
}

@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class,
    ViewModelModule::class, RepositoryModule::class])
@Singleton interface AppComponent {
    fun inject(app: App)

    @Component.Builder interface Builder {
        @BindsInstance fun context(context: Context): Builder
        fun build() : AppComponent
    }
}

@Module(subcomponents = [MainComponent::class, ListComponent::class, TimerComponent::class,
    StartComponent::class])
interface AppModule {
    @Binds @IntoMap @ActivityKey(MainActivity::class)
    fun bindsMainActivity(builder: MainComponent.Builder) : AndroidInjector.Factory<out Activity>

    @Binds @IntoMap @FragmentKey(ListFragment::class)
    fun bindsListFragment(builder: ListComponent.Builder) : AndroidInjector.Factory<out Fragment>
    @Binds @IntoMap @FragmentKey(TimerFragment::class)
    fun bindsTimerFragment(builder: TimerComponent.Builder) : AndroidInjector.Factory<out Fragment>
    @Binds @IntoMap @FragmentKey(StartFragment::class)
    fun bindsStartFragment(builder: StartComponent.Builder) : AndroidInjector.Factory<out Fragment>
}