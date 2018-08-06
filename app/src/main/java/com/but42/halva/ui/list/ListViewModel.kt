package com.but42.halva.ui.list

import com.but42.halva.repository.GetListSpec
import com.but42.halva.repository.ListItem
import com.but42.halva.repository.Repository
import com.but42.halva.ui.base.FragmentEvent
import com.but42.halva.ui.base.FragmentType
import com.but42.halva.ui.base.FragmentViewModel
import com.but42.halva.ui.base.ViewModelBase
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface ListViewModel : FragmentViewModel {
    val list: Flowable<List<ListItem>>
    val compositeDisposable: CompositeDisposable
    val adapter: ListAdapter
    fun onBackPressed()
}

class ListViewModelImpl @Inject constructor(
        repository: Repository
) : ViewModelBase(repository), ListViewModel {
    override val list: Flowable<List<ListItem>> = query(GetListSpec())
    override val adapter = ListAdapter()

    init {
        adapter.init(this)
    }

    override fun onBackPressed() = replaceFragment(FragmentEvent(FragmentType.START))
}