package com.but42.halva.app

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.but42.halva.ui.list.ListViewModelImpl
import com.but42.halva.ui.start.StartViewModelImpl
import com.but42.halva.ui.timer.TimerViewModelImpl
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
@Singleton class ViewModelFactory @Inject constructor(
        private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel: ViewModel = viewModels[modelClass]?.get()
                ?: throw IllegalArgumentException("model class $modelClass not found")
        return viewModel as T
    }
}

@Target(AnnotationTarget.FUNCTION) @Retention(AnnotationRetention.RUNTIME) @MapKey
annotation class ViewModelKey(val value : KClass<out ViewModel>)

@Module interface ViewModelModule {
    @Binds @IntoMap @ViewModelKey(ListViewModelImpl::class)
    fun listViewModel(viewModel: ListViewModelImpl): ViewModel
    @Binds @IntoMap @ViewModelKey(TimerViewModelImpl::class)
    fun timerViewModel(viewModel: TimerViewModelImpl): ViewModel
    @Binds @IntoMap @ViewModelKey(StartViewModelImpl::class)
    fun startViewModel(viewModel: StartViewModelImpl): ViewModel
}