package com.anbetter.bus.observer

import android.util.Log
import androidx.lifecycle.*

/**
 *
 * <p>
 * Created by android_ls on 2021/1/9 12:30.
 *
 * @author android_ls
 * @version 1.0
 */
class BusLifecycleObserver<T>(
    private val owner: LifecycleOwner,
    private val observer: Observer<in T>,
    private val liveData: BusLiveData<T>
) : BusObserver<T>(observer, liveData), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        liveData.removeObserver(observer)
        owner.lifecycle.removeObserver(this)
    }

    override fun isAttachedTo(owner: LifecycleOwner): Boolean {
        return owner == this.owner
    }

}