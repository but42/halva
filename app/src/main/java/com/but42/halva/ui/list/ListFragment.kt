package com.but42.halva.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.but42.halva.app.ViewModelFactory
import com.but42.halva.databinding.FragmentListBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.but42.halva.R
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
class ListFragment : Fragment() {
    @Inject lateinit var factory: ViewModelFactory
    private lateinit var viewModel: ListViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[ListViewModelImpl::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}

@Subcomponent interface ListComponent : AndroidInjector<ListFragment> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<ListFragment>()
}