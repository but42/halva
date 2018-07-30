package com.but42.halva.ui.base

import android.arch.lifecycle.ViewModel
import com.but42.halva.repository.QuerySpecification
import com.but42.halva.repository.Repository
import com.but42.halva.repository.RequestSpecification
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
abstract class ViewModelBase(
        private val repository: Repository
) : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun request(spec: RequestSpecification, action: () -> Unit = {}) {
        compositeDisposable.add(repository.request(spec)
                .subscribeOn(Schedulers.io())
                .subscribe(action))
    }

    protected fun <T> query(spec: QuerySpecification<T>, action: (T) -> Unit) {
        compositeDisposable.add(repository.query(spec)
                .subscribeOn(Schedulers.io())
                .subscribe(action))
    }

    protected fun <T> query(spec: QuerySpecification<T>): Flowable<T>
            = repository.query(spec)
            .subscribeOn(Schedulers.io())
}