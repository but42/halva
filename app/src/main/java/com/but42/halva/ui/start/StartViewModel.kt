package com.but42.halva.ui.start

import com.but42.halva.repository.Repository
import com.but42.halva.ui.base.FragmentEvent
import com.but42.halva.ui.base.FragmentType
import com.but42.halva.ui.base.FragmentViewModel
import com.but42.halva.ui.base.ViewModelBase
import javax.inject.Inject

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface StartViewModel : FragmentViewModel {
    fun onButton(type: FragmentType)
}

class StartViewModelImpl @Inject constructor(
        repository: Repository
) : ViewModelBase(repository), StartViewModel {
    override fun onButton(type: FragmentType) = replaceFragment(FragmentEvent(type))
}