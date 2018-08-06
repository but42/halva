package com.but42.halva.ui.timer

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.but42.halva.app.ViewModelFactory
import com.but42.halva.databinding.FragmentTimerBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.but42.halva.R
import com.but42.halva.ui.base.FragmentUtil
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
class TimerFragment : Fragment() {
    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var fragmentUtil: FragmentUtil
    private lateinit var viewModel: TimerViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[TimerViewModelImpl::class.java]
        fragmentUtil.observe(this, viewModel, activity?.supportFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentTimerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        fun newInstance() = TimerFragment()
    }
}

@Subcomponent interface TimerComponent : AndroidInjector<TimerFragment> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<TimerFragment>()
}