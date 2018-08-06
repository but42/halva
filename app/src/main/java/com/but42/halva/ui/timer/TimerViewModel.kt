package com.but42.halva.ui.timer

import android.util.Log
import com.but42.halva.repository.GetTimeUpEventSpec
import com.but42.halva.repository.Repository
import com.but42.halva.repository.StartTimerSpec
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
interface TimerViewModel : FragmentViewModel {
    fun onStart()
    fun onBackPressed()
}

class TimerViewModelImpl @Inject constructor(
        repository: Repository
) : ViewModelBase(repository), TimerViewModel {
    init {
        query(GetTimeUpEventSpec()) {
            Log.d("timer", "time is up")
        }
    }

    override fun onStart() = request(StartTimerSpec())
    override fun onBackPressed() = replaceFragment(FragmentEvent(FragmentType.START))
}