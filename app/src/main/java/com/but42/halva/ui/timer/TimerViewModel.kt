package com.but42.halva.ui.timer

import com.but42.halva.repository.GetTimeUpEventSpec
import com.but42.halva.repository.Repository
import com.but42.halva.repository.StartTimerSpec
import com.but42.halva.ui.base.FragmentEvent
import com.but42.halva.ui.base.FragmentType.START
import com.but42.halva.ui.base.FragmentType.TIME_OUT
import com.but42.halva.ui.base.FragmentViewModel
import com.but42.halva.ui.base.ViewModelBase
import javax.inject.Inject

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface TimerViewModel : FragmentViewModel {
    fun onStart()
    fun onBackPressed()
}

class TimerViewModelImpl @Inject constructor(
        repository: Repository
) : ViewModelBase(repository), TimerViewModel {
    init {
        query(GetTimeUpEventSpec()) {
            replaceFragment(FragmentEvent(TIME_OUT))
        }
    }

    override fun onStart() = request(StartTimerSpec())
    override fun onBackPressed() = replaceFragment(FragmentEvent(START))
}