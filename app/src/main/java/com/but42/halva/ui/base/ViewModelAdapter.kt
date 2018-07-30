package com.but42.halva.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */

abstract class ViewModelAdapter<O : Any> : RecyclerView.Adapter<ViewHolder>() {

    protected val items = LinkedList<O>()

    private val cellMap = LinkedHashMap<Class<out O>, CellInfo>()
    private val sharedObjects = LinkedHashMap<Int, Any>()

    protected fun registerCell(clazz: Class<out O>, @LayoutRes layoutId: Int, bindingId: Int) {
        cellMap[clazz] = CellInfo(layoutId, bindingId)
    }

    protected fun addGlobalObject(obj: Any, bindingId: Int) {
        sharedObjects[bindingId] = obj
    }

    private fun getViewModel(position: Int) = items[position]

    private fun getCellInfo(viewModel: O): CellInfo {
        cellMap.entries.find { it.key == viewModel.javaClass }
                ?.apply { return value }

        cellMap.entries.find { it.key.isInstance(viewModel) }
                ?.apply {
                    cellMap[viewModel.javaClass] = value
                    return value
                }

        throw Exception("Cell info for class ${viewModel.javaClass.name} not found.")
    }

    private fun onBind(binding: ViewDataBinding, cellInfo: CellInfo, position: Int) {
        val viewModel = getViewModel(position)
        if (cellInfo.bindingId != 0)
            binding.setVariable(cellInfo.bindingId, viewModel)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return getCellInfo(getViewModel(position)).layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        val viewHolder = ViewHolder(view)

        sharedObjects.forEach{viewHolder.binding.setVariable(it.key, it.value)}
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellInfo = getCellInfo(getViewModel(position))
        onBind(holder.binding, cellInfo, position)
    }

    protected fun observe(flowable: Flowable<List<O>>, compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(flowable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::update))
    }

    protected open fun update(list: List<O>) {
        val callback = AdapterDiffCallback(items, list)
        update(callback, list)
    }

    protected fun update(callback: DiffUtil.Callback, list: List<O>) {
        val result = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(list)
        result.dispatchUpdatesTo(this)
    }
}

data class CellInfo(val layoutId: Int, val bindingId: Int)

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding: ViewDataBinding = DataBindingUtil.bind(view)!!
}

open class AdapterDiffCallback<O>(private val oldList: List<O>, private val newList: List<O>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition] == newList[newItemPosition]
}