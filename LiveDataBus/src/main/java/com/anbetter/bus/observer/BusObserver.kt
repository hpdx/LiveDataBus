package com.anbetter.bus.observer

import android.util.Log
import androidx.lifecycle.BusLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 *
 * <p>
 * Created by android_ls on 2021/1/9 12:14.
 *
 * @author android_ls
 * @version 1.0
 */
open class BusObserver<T>(
    private val observer: Observer<in T>,
    private val busLiveData: BusLiveData<T>
) : Observer<T> {

    private val mLastVersion = busLiveData.version

    override fun onChanged(t: T) {
        if (mLastVersion >= busLiveData.version) {
            return
        }

        try {
            observer.onChanged(t)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun isAttachedTo(owner: LifecycleOwner) = false

}