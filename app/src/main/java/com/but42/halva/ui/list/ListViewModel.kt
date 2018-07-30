package com.but42.halva.ui.list

import com.but42.halva.repository.GetListSpec
import com.but42.halva.repository.ListItem
import com.but42.halva.repository.Repository
import com.but42.halva.ui.base.ViewModelBase
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface ListViewModel {
    val list: Flowable<List<ListItem>>
    val compositeDisposable: CompositeDisposable
    val adapter: ListAdapter
}

class ListViewModelImpl @Inject constructor(
        repository: Repository
) : ViewModelBase(repository), ListViewModel {
    override val list: Flowable<List<ListItem>> = query(GetListSpec())
    override val adapter = ListAdapter()

    init {
        adapter.init(this)
    }
}