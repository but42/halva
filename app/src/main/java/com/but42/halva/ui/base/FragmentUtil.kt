package com.but42.halva.ui.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import com.but42.halva.ui.list.ListFragment
import com.but42.halva.ui.start.StartFragment
import com.but42.halva.ui.timer.TimerFragment
import javax.inject.Inject
import javax.inject.Singleton
import com.but42.halva.R
import com.but42.halva.ui.TimeOutDialogFragment
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Mikhail Kuznetsov on 01.08.2018.
 *
 * @author Mikhail Kuznetsov
 */
@Singleton class FragmentUtil @Inject constructor() {
    private val subject: Subject<FragmentType> = PublishSubject.create()
    private val events = MutableLiveData<FragmentType>()

    fun subscribe(owner: LifecycleOwner, action: (FragmentType) -> Unit) {
        events.observe(owner, Observer { action.invoke(it ?: return@Observer) })
    }

    fun observe(owner: LifecycleOwner, viewModel: FragmentViewModel, manager: FragmentManager?) {
        viewModel.fragmentLiveData.observe(owner, Observer {
            if (it?.isHappen != false) return@Observer
            replaceFragment(it, manager ?: return@Observer)
        })
    }

    private fun replaceFragment(event: FragmentEvent, manager: FragmentManager) {
        val fragment = when (event.type) {
            FragmentType.LIST -> ListFragment.newInstance()
            FragmentType.TIMER -> TimerFragment.newInstance()
            FragmentType.START -> StartFragment.newInstance()
            FragmentType.TIME_OUT -> TimeOutDialogFragment.newInstance()
        }
        if (fragment is DialogFragment) {
            val oldFragment = manager.findFragmentByTag(event.type.name)
            if (oldFragment == null) fragment.show(manager, event.type.name)
        } else {
            manager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            events.postValue(event.type)
        }
        event.isHappen = true
    }
}

class FragmentEvent(
        val type: FragmentType,
        var isHappen: Boolean = false
)

enum class FragmentType {
    LIST, TIMER, START, TIME_OUT
}

interface FragmentViewModel {
    val fragmentLiveData: LiveData<FragmentEvent>
}

