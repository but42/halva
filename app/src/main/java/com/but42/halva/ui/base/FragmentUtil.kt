package com.but42.halva.ui.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentManager
import com.but42.halva.ui.list.ListFragment
import com.but42.halva.ui.start.StartFragment
import com.but42.halva.ui.timer.TimerFragment
import javax.inject.Inject
import javax.inject.Singleton
import com.but42.halva.R
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Mikhail Kuznetsov on 01.08.2018.
 *
 * @author Mikhail Kuznetsov
 */
@Singleton class FragmentUtil @Inject constructor() {
    private val subject: Subject<FragmentType> = PublishSubject.create()

    fun subscribe(action: (FragmentType) -> Unit) {
        subject.subscribe(action)
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
        }
        manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        event.isHappen = true
        subject.onNext(event.type)
    }
}

class FragmentEvent(
        val type: FragmentType,
        var isHappen: Boolean = false
)

enum class FragmentType {
    LIST, TIMER, START
}

interface FragmentViewModel {
    val fragmentLiveData: LiveData<FragmentEvent>
}

