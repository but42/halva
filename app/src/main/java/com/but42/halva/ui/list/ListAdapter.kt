package com.but42.halva.ui.list

import com.but42.halva.BR
import com.but42.halva.repository.ListItem
import com.but42.halva.ui.base.ViewModelAdapter
import javax.inject.Inject
import com.but42.halva.R

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
class ListAdapter @Inject constructor() : ViewModelAdapter<ListItem>() {
    private var isInit = false

    fun init(viewModel: ListViewModel) {
        if (isInit) return
        addGlobalObject(viewModel, BR.viewModel)
        registerCell(ListItem::class.java, R.layout.item_list, BR.item)
        observe(viewModel.list, viewModel.compositeDisposable)
        isInit = true
    }
}